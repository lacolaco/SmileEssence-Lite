package net.miz_hi.smileessence.view.fragment.impl;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import net.miz_hi.smileessence.R;
import net.miz_hi.smileessence.cache.MyImageCache;
import net.miz_hi.smileessence.command.CommandOpenUrl;
import net.miz_hi.smileessence.command.user.UserCommandOpenPage;
import net.miz_hi.smileessence.core.MyExecutor;
import net.miz_hi.smileessence.menu.UserMenu;
import net.miz_hi.smileessence.model.status.user.UserModel;
import net.miz_hi.smileessence.task.impl.GetUserTask;
import net.miz_hi.smileessence.util.UiHandler;
import net.miz_hi.smileessence.view.IRemovable;
import net.miz_hi.smileessence.view.fragment.NamedFragment;

@SuppressLint("ValidFragment")
public class UserInfoFragment extends NamedFragment implements OnClickListener, IRemovable
{

    UserModel user;
    TextView screenNameView;
    TextView nameView;
    TextView homepageView;
    TextView locateView;
    TextView isFollowingView;
    TextView isFollowedView;
    TextView isProtectedView;
    TextView descriptionView;
    TextView tweetCountView;
    TextView followingView;
    TextView followedView;
    TextView favoriteView;
    NetworkImageView iconView;
    NetworkImageView headerView;

    private UserInfoFragment()
    {
    }

    public static UserInfoFragment newInstance(UserModel user)
    {
        UserInfoFragment fragment = new UserInfoFragment();
        fragment.user = user;
        return fragment;
    }

    @Override
    public String getTitle()
    {
        return user.screenName + "'s Profile";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View page = inflater.inflate(R.layout.userinfo_layout, container, false);
        Button reload = (Button) page.findViewById(R.id.user_reload);
        reload.setOnClickListener(this);
        Button menu = (Button) page.findViewById(R.id.user_menu);
        menu.setOnClickListener(this);

        screenNameView = (TextView) page.findViewById(R.id.user_screenname);
        screenNameView.setOnClickListener(this);
        nameView = (TextView) page.findViewById(R.id.user_name);
        homepageView = (TextView) page.findViewById(R.id.user_homepage);
        locateView = (TextView) page.findViewById(R.id.user_locate);
        isFollowingView = (TextView) page.findViewById(R.id.user_isfollowing);
        isFollowedView = (TextView) page.findViewById(R.id.user_isfollowed);
        isProtectedView = (TextView) page.findViewById(R.id.user_isprotected);
        descriptionView = (TextView) page.findViewById(R.id.user_bio);
        tweetCountView = (TextView) page.findViewById(R.id.user_count_tweet);
        tweetCountView.setOnClickListener(this);
        followingView = (TextView) page.findViewById(R.id.user_count_following);
        followingView.setOnClickListener(this);
        followedView = (TextView) page.findViewById(R.id.user_count_followed);
        followedView.setOnClickListener(this);
        favoriteView = (TextView) page.findViewById(R.id.user_count_favorite);
        favoriteView.setOnClickListener(this);
        iconView = (NetworkImageView) page.findViewById(R.id.user_icon);
        iconView.setOnClickListener(this);
        headerView = (NetworkImageView) page.findViewById(R.id.user_header);
        reload(false);
        return page;
    }

    private void reload(final boolean force)
    {
        new UiHandler()
        {

            @Override
            public void run()
            {
                SpannableString screenName = new SpannableString("@" + user.screenName);
                screenName.setSpan(new UnderlineSpan(), 0, screenName.length(), 0);
                screenNameView.setText(screenName);
                nameView.setText(user.name);
                if (TextUtils.isEmpty(user.homePageUrl))
                {
                    homepageView.setVisibility(View.GONE);
                }
                else
                {
                    homepageView.setText(user.homePageUrl);
                }
                if (TextUtils.isEmpty(user.location))
                {
                    locateView.setVisibility(View.GONE);
                }
                else
                {
                    locateView.setText(user.location);
                }
                isFollowingView.setText(user.isFriend(force) ? "フォローしています" : user.isMe() ? "あなたです" : "フォローしていません");
                isFollowedView.setText(user.isFollower(force) ? "フォローされています" : user.isMe() ? "あなたです" : "フォローされていません");
                isProtectedView.setVisibility(user.isProtected ? View.VISIBLE : View.GONE);
                descriptionView.setText(user.description);
                tweetCountView.setText(Integer.toString(user.statusCount));
                followingView.setText(Integer.toString(user.friendCount));
                followedView.setText(Integer.toString(user.followerCount));
                favoriteView.setText(Integer.toString(user.favoriteCount));
                MyImageCache.setImageToView(user.iconUrl, iconView);
                MyImageCache.setImageToView(user.headerImageUrl, headerView);
            }
        }.post();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.user_reload:
            {
                final ProgressDialog pd = ProgressDialog.show(getActivity(), null, "情報を更新中...", true);
                MyExecutor.execute(new Runnable()
                {
                    public void run()
                    {
                        user.updateData(new GetUserTask(user.userId).call());
                        reload(true);
                        pd.dismiss();
                    }
                });
                break;
            }
            case R.id.user_menu:
            {
                new UserMenu(getActivity(), user).create().show();
                break;
            }
            case R.id.user_screenname:
            {
                new UserCommandOpenPage(getActivity(), user.screenName).run();
                break;
            }
            case R.id.user_icon:
            {
                openUrl(user.iconUrl);
                break;
            }
            case R.id.user_count_tweet:
            {
                break;
            }
            case R.id.user_count_following:
            {
                break;
            }
            case R.id.user_count_followed:
            {
                break;
            }
            case R.id.user_count_favorite:
            {
                break;
            }
        }
    }

    private void openUrl(String url)
    {
        new CommandOpenUrl(getActivity(), url).run();
    }

    @Override
    public void onRemoved()
    {
    }

}
