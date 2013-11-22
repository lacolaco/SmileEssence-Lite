package net.miz_hi.smileessence.system;

import android.text.TextUtils;
import net.miz_hi.smileessence.notification.Notificator;
import net.miz_hi.smileessence.task.impl.TweetTask;
import net.miz_hi.smileessence.util.StringUtils;
import net.miz_hi.smileessence.util.UiHandler;
import net.miz_hi.smileessence.view.fragment.impl.PostFragment;
import twitter4j.StatusUpdate;

import java.io.File;

public class PostSystem
{

    private PostFragment fragment;
    private static PostSystem instance;
    public static final long NONE_ID = -1;

    private PostSystem()
    {
    }

    public static void init(PostFragment fragment)
    {
        instance = new PostSystem();
        instance.fragment = fragment;
    }

    private static PostPageState getState()
    {
        return instance.fragment.getState();
    }

    public static void clear(boolean keepPicture)
    {
        PostPageState state = getState();
        state.setText("");
        state.setCursor(0);
        state.setInReplyToStatusId(NONE_ID);
        if (!keepPicture)
        {
            state.setPicturePath(null);
        }
    }

    public static void setText(String str)
    {
        PostPageState state = getState();
        state.setText(str);
    }

    public static void appendText(String str)
    {
        PostPageState state = getState();
        state.setText(state.getText() + str);
    }

    public static void insertText(String str)
    {
        PostPageState state = getState();
        int cursor = state.cursor;
        StringBuilder sb = new StringBuilder(state.getText());
        sb.insert(cursor, str);
        cursor = cursor + sb.length();
        if (cursor > sb.length())
        {
            cursor = sb.length();
        }
        state.setText(sb.toString());
        state.setCursor(cursor);
    }

    public static void setReply(String userName, long statusId)
    {
        PostPageState state = getState();
        state.setText("@" + userName + " ");
        state.setCursor(state.getText().length());
        state.setInReplyToStatusId(statusId);
    }

    public static void addReply(String userName)
    {
        PostPageState state = getState();
        StringBuilder sb = new StringBuilder(state.getText());

        if ((sb.indexOf("@" + userName) != -1))
        {
            return;
        }
        else
        {
            sb.append(String.format("@%s ", userName));
        }

        if (sb.charAt(0) != '.')
        {
            sb.insert(0, ".");
        }
        state.setText(sb.toString());
        state.setCursor(state.getText().length());
        state.setInReplyToStatusId(NONE_ID);
    }

    public static boolean submit(String text)
    {
        PostPageState state = getState();
        if (TextUtils.isEmpty(text) && state.getPicturePath() == null)
        {
            Notificator.alert("何か入力してください");
            return false;
        }
        else if (StringUtils.countTweetCharacters(text) > 140)
        {
            Notificator.alert("文字数が多すぎます");
            return false;
        }
        else
        {
            final StatusUpdate update = new StatusUpdate(text);
            if (state.getInReplyToStatusId() >= 0)
            {
                update.setInReplyToStatusId(state.getInReplyToStatusId());
            }
            if (state.getPicturePath() != null)
            {
                update.setMedia(new File(state.getPicturePath()));
            }
            new UiHandler()
            {

                @Override
                public void run()
                {
                    new TweetTask(update).callAsync();
                    clear(false);
                }
            }.postAtFrontOfQueue();
        }
        return true;
    }

    public static void openPostPage()
    {
        PageController.getInstance().move(PageController.PAGE_POST);
        instance.fragment.loadState();
    }

    public static void setPicturePath(String path)
    {
        PostPageState state = getState();
        state.setPicturePath(path);
    }

    public static void setCursor(int i)
    {
        PostPageState state = getState();
        state.setCursor(i);
    }

    public static String getText()
    {
        PostPageState state = getState();
        return state.getText();
    }


    public static class PostPageState
    {

        private String text = "";
        private int cursor = 0;
        private long inReplyTo = NONE_ID;
        private String pictPath;

        public String getText()
        {
            return text;
        }

        public void setText(String text)
        {
            this.text = text;
        }

        public void setCursor(int index)
        {
            this.cursor = index;
        }

        public int getCursor()
        {
            return cursor;
        }

        public long getInReplyToStatusId()
        {
            return inReplyTo;
        }

        public void setInReplyToStatusId(long statusId)
        {
            inReplyTo = statusId;
        }

        public String getPicturePath()
        {
            return pictPath;
        }

        public void setPicturePath(String path)
        {
            pictPath = path;
        }
    }
}