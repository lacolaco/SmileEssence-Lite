package net.miz_hi.smileessence.view.fragment.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.android.volley.toolbox.NetworkImageView;
import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.R;
import net.miz_hi.smileessence.cache.ImageCache;
import net.miz_hi.smileessence.cache.UserCache;
import net.miz_hi.smileessence.core.MyExecutor;
import net.miz_hi.smileessence.dialog.ConfirmDialog;
import net.miz_hi.smileessence.dialog.SelectPictureDialog;
import net.miz_hi.smileessence.listener.PostEditTextListener;
import net.miz_hi.smileessence.menu.MainMenu;
import net.miz_hi.smileessence.menu.PostingMenu;
import net.miz_hi.smileessence.model.status.tweet.TweetModel;
import net.miz_hi.smileessence.model.status.user.UserModel;
import net.miz_hi.smileessence.notification.Notificator;
import net.miz_hi.smileessence.preference.EnumPreferenceKey;
import net.miz_hi.smileessence.status.StatusViewFactory;
import net.miz_hi.smileessence.status.TweetUtils;
import net.miz_hi.smileessence.system.PostSystem;
import net.miz_hi.smileessence.system.PostSystem.PostPageState;
import net.miz_hi.smileessence.task.impl.GetUserTask;
import net.miz_hi.smileessence.twitter.ResponseConverter;
import net.miz_hi.smileessence.util.UiHandler;
import net.miz_hi.smileessence.view.activity.MainActivity;
import net.miz_hi.smileessence.view.fragment.NamedFragment;
import twitter4j.User;

@SuppressLint("ValidFragment")
public class PostFragment extends NamedFragment implements OnClickListener
{

    TextView textCount;
    EditText editText;
    FrameLayout frameInReplyTo;
    ImageView imagePict;
    NetworkImageView iconView;
    TextView screenNameView;
    private PostPageState state;
    boolean inited;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (!inited)
        {
            state = new PostPageState();
            PostSystem.init(this);
            inited = true;
        }
    }

    @Override
    public String getTitle()
    {
        return "Post";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View page = inflater.inflate(R.layout.post_layout, container, false);
        editText = (EditText) page.findViewById(R.id.editText_tweet);
        frameInReplyTo = (FrameLayout) page.findViewById(R.id.frame_inreplyto);
        imagePict = (ImageView) page.findViewById(R.id.image_pict);
        textCount = (TextView) page.findViewById(R.id.textView_count);
        Button imageButtonSubmit = (Button) page.findViewById(R.id.imBtn_tweet);
        ImageButton imageButtonDelete = (ImageButton) page.findViewById(R.id.imBtn_delete);
        ImageButton imageButtonMenu = (ImageButton) page.findViewById(R.id.imBtn_tweetmenu);
        ImageButton imageButtonPict = (ImageButton) page.findViewById(R.id.imBtn_pickpict);
        ImageButton config = (ImageButton) page.findViewById(R.id.post_config);
        config.setOnClickListener(this);
        iconView = (NetworkImageView) page.findViewById(R.id.post_myIcon);
        screenNameView = (TextView) page.findViewById(R.id.post_myName);

        PostEditTextListener listener = new PostEditTextListener(textCount);
        editText.setTextSize(Client.getTextSize() + 3);
        editText.addTextChangedListener(listener);
        editText.setOnFocusChangeListener(listener);
        imageButtonSubmit.setOnClickListener(this);
        imageButtonDelete.setOnClickListener(this);
        imageButtonMenu.setOnClickListener(this);
        imageButtonPict.setOnClickListener(this);
        imagePict.setOnClickListener(this);
        return page;
    }

    @Override
    public void onPause()
    {
        super.onPause();
        saveState();
    }

    @Override
    public void onSelected()
    {
        loadState();
        openIme();
    }

    @Override
    public void onDeselect()
    {
        saveState();
        hideIme();
    }

    public PostPageState getState()
    {
        return state;
    }

    public void loadState()
    {
        String text = state.getText();
        setText(text);
        int cursor = state.getCursor();
        setCursor(cursor);
        long inReplyTo = state.getInReplyToStatusId();
        setInReplyTo(inReplyTo);
        String picturePath = state.getPicturePath();
        setPicture(picturePath);
        UserModel me = UserCache.get(Client.getMainAccount().getUserId());
        if (me != null)
        {
            ImageCache.setImageToView(me.iconUrl, iconView);
            screenNameView.setText(me.screenName);
        }
        else
        {
            new GetUserTask(Client.getMainAccount().getUserId())
            {
                @Override
                public void onPostExecute(User result)
                {
                    if (result != null)
                    {
                        UserModel model = ResponseConverter.convert(result);
                        ImageCache.setImageToView(model.iconUrl, iconView);
                        screenNameView.setText(model.screenName);
                    }
                }
            }.callAsync();
        }
    }

    /**
     * save to state: text, cursor
     */
    public void saveState()
    {
        if (editText != null)
        {
            String text = editText.getText().toString();
            state.setText(text);
            int cursor = editText.getSelectionEnd();
            state.setCursor(cursor);
        }
    }

    public void setText(String s)
    {
        if (editText != null)
        {
            editText.setText(s);
        }
    }

    public void setCursor(final int i)
    {
        if (editText != null)
        {
            if (i < 0)
            {
                editText.setSelection(0);
            }
            else if (i > editText.getText().length())
            {
                editText.setSelection(editText.getText().length());
            }
            else
            {
                editText.setSelection(i);
            }
        }
    }

    public void setInReplyTo(final long l)
    {
        if (frameInReplyTo != null)
        {
            if (l == PostSystem.NONE_ID)
            {
                frameInReplyTo.setVisibility(View.GONE);
            }
            else
            {
                MyExecutor.execute(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        try
                        {
                            TweetModel status = TweetUtils.getOrCreateStatusModel(l);
                            final View v = StatusViewFactory.newInstance(MainActivity.getInstance().getLayoutInflater(), null).getStatusView(status);
                            new UiHandler()
                            {

                                @Override
                                public void run()
                                {
                                    frameInReplyTo.removeAllViews();
                                    frameInReplyTo.addView(v);
                                    frameInReplyTo.setVisibility(View.VISIBLE);
                                }
                            }.post();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }


    public void setPicture(final String path)
    {
        if (imagePict != null)
        {
            if (path == null)
            {
                imagePict.setVisibility(View.GONE);
                return;
            }
            MyExecutor.execute(new Runnable()
            {
                public void run()
                {
                    new UiHandler()
                    {

                        @Override
                        public void run()
                        {
                            Options opt = new Options();
                            opt.inPurgeable = true; // GC可能にする
                            opt.inSampleSize = 2;
                            Bitmap bm = BitmapFactory.decodeFile(path, opt);
                            imagePict.setImageBitmap(bm);
                            imagePict.setVisibility(View.VISIBLE);
                        }
                    }.post();
                }
            });
        }
    }

    public void clear()
    {
        editText.setText("");
        setInReplyTo(PostSystem.NONE_ID);
        PostSystem.clear(true);
        removePicture();
    }

    public void clearBySubmit()
    {
        editText.setText("");
        setInReplyTo(PostSystem.NONE_ID);
        PostSystem.clear(false);
        imagePict.setVisibility(View.GONE);
    }

    public void removePicture()
    {
        if (imagePict.isShown())
        {
            ConfirmDialog.show(MainActivity.getInstance(), "画像の投稿を取り消しますか？", new Runnable()
            {

                @Override
                public void run()
                {
                    state.setPicturePath(null);
                    imagePict.setVisibility(View.GONE);
                    Notificator.info("取り消しました");
                }
            });
        }
    }

    public void openIme()
    {
        if (editText == null)
        {
            return;
        }

        new UiHandler()
        {

            @Override
            public void run()
            {
                if (Client.<Boolean>getPreferenceValue(EnumPreferenceKey.OPEN_IME))
                {
                    InputMethodManager imm = (InputMethodManager) Client.getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(editText, 0);
                }
            }
        }.post();
    }

    public void hideIme()
    {
        if (editText == null)
        {
            return;
        }

        new UiHandler()
        {

            @Override
            public void run()
            {
                InputMethodManager imm = (InputMethodManager) Client.getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        }.post();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.imBtn_tweet:
            {
                if (PostSystem.submit(editText.getText().toString()))
                {
                    if (Client.<Boolean>getPreferenceValue(EnumPreferenceKey.AFTER_SUBMIT))
                    {
                        MainActivity.getInstance().getViewPager().setCurrentItem(1);
                    }
                    clearBySubmit();
                }
                break;
            }
            case R.id.imBtn_tweetmenu:
            {
                saveState();
                new PostingMenu(getActivity()).create().show();
                break;
            }
            case R.id.imBtn_pickpict:
            {
                saveState();
                new SelectPictureDialog(getActivity()).create().show();
                break;
            }
            case R.id.imBtn_delete:
            {
                ConfirmDialog.show(getActivity(), "全消去しますか？", new Runnable()
                {
                    @Override
                    public void run()
                    {
                        clear();
                    }
                });
                break;
            }
            case R.id.image_pict:
            {
                removePicture();
                break;
            }
            case R.id.post_config:
            {
                saveState();
                new MainMenu(getActivity()).create().show();
                break;
            }
            default:
                break;
        }
    }
}