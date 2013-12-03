package net.miz_hi.smileessence.view.fragment.impl;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.R;
import net.miz_hi.smileessence.cache.ImageCache;
import net.miz_hi.smileessence.command.CommandOpenUrl;
import net.miz_hi.smileessence.menu.UserMenu;
import net.miz_hi.smileessence.model.status.user.UserModel;
import net.miz_hi.smileessence.task.impl.FollowTask;
import net.miz_hi.smileessence.task.impl.GetRelationshipTask;
import net.miz_hi.smileessence.task.impl.GetUserTask;
import net.miz_hi.smileessence.task.impl.UnfollowTask;
import net.miz_hi.smileessence.twitter.TwitterUtil;
import net.miz_hi.smileessence.view.fragment.IRemovable;
import net.miz_hi.smileessence.view.fragment.ISingleton;
import net.miz_hi.smileessence.view.fragment.NamedFragment;
import twitter4j.Relationship;
import twitter4j.User;

@SuppressLint("ValidFragment")
public class UserInfoFragment extends NamedFragment implements OnClickListener, IRemovable, ISingleton
{

    UserModel user;
    TextView screenNameView;
    TextView nameView;
    TextView homepageView;
    TextView locateView;
    TextView isFollowedView;
    TextView isProtectedView;
    TextView descriptionView;
    TextView tweetCountView;
    TextView followingView;
    TextView followedView;
    TextView favoriteView;
    NetworkImageView iconView;
    NetworkImageView headerView;
    Button followButton;

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
        View reload = page.findViewById(R.id.user_reload);
        reload.setOnClickListener(this);
        View menu = page.findViewById(R.id.user_menu);
        menu.setOnClickListener(this);

        screenNameView = (TextView) page.findViewById(R.id.user_screenname);
        screenNameView.setOnClickListener(this);
        nameView = (TextView) page.findViewById(R.id.user_name);
        homepageView = (TextView) page.findViewById(R.id.user_homepage);
        locateView = (TextView) page.findViewById(R.id.user_locate);
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
        followButton = (Button) page.findViewById(R.id.user_follow);
        followButton.setOnClickListener(this);
        setData();
        return page;
    }

    public void reload()
    {
        final ProgressDialog pd = ProgressDialog.show(getActivity(), null, "情報を更新中...", true);
        new GetUserTask(user.userId)
        {
            @Override
            public void onPostExecute(User result)
            {
                user.updateData(result);
                setData();
                pd.dismiss();
            }
        }.callAsync();
    }

    private void setData()
    {
        nameView.setText(user.name);
        screenNameView.setText("@" + user.screenName);
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

        if (user.isMe())
        {
            followButton.setVisibility(View.GONE);
            isFollowedView.setText("あなたです");
        }
        else
        {
            followButton.setText("読み込み中");
            isFollowedView.setText("読み込み中");
            new GetRelationshipTask(user.userId)
            {
                @Override
                public void onPostExecute(Relationship result)
                {
                    if (result != null)
                    {
                        boolean isFollowing = result.isSourceFollowingTarget();
                        followButton.setText(isFollowing ? "リムーブする" : "フォローする");
                        followButton.setBackgroundDrawable(isFollowing ? Client.getResource().getDrawable(R.drawable.round_red) : Client.getResource().getDrawable(R.drawable.round_blue));
                        followButton.setTag(isFollowing);
                        isFollowedView.setText(result.isSourceFollowedByTarget() ? "フォローされています" : "フォローされていません");
                    }
                }
            }.callAsync();
        }
        isProtectedView.setVisibility(user.isProtected ? View.VISIBLE : View.GONE);
        String htmlDescription = getHtmlDescription(user.description);
        descriptionView.setText(Html.fromHtml(htmlDescription));
        descriptionView.setMovementMethod(LinkMovementMethod.getInstance());
        tweetCountView.setText(Integer.toString(user.statusCount));
        followingView.setText(Integer.toString(user.friendCount));
        followedView.setText(Integer.toString(user.followerCount));
        favoriteView.setText(Integer.toString(user.favoriteCount));
        ImageCache.setImageToView(user.biggerIconUrl, iconView);
        ImageCache.setImageToView(user.headerImageUrl, headerView);
    }

    private String getHtmlDescription(String description)
    {
        String html = description;
        html = html.replaceAll("https?://[\\w/:%#\\$&\\?\\(\\)~\\.=\\+\\-]+", "<a href=\"$0\">$0</a>");
        html = html.replaceAll("@([a-zA-Z0-9_]+)", "<a href=\"" + TwitterUtil.getUserHomeURL("$1") + "\">$0</a>");
        html = html.replaceAll("\r\n", "<br />");
        return html;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.user_reload:
            {
                reload();
                break;
            }
            case R.id.user_menu:
            {
                new UserMenu(getActivity(), user).create().show();
                break;
            }
            case R.id.user_icon:
            {
                openUrl(user.biggerIconUrl);
                break;
            }
            case R.id.user_screenname:
            {
                openUrl(TwitterUtil.getUserHomeURL(user.screenName));
                break;
            }
            case R.id.user_count_tweet:
            {
                openUrl(TwitterUtil.getUserHomeURL(user.screenName));
                break;
            }
            case R.id.user_count_following:
            {
                openUrl(TwitterUtil.getUserHomeURL(user.screenName) + "/following");
                break;
            }
            case R.id.user_count_followed:
            {
                openUrl(TwitterUtil.getUserHomeURL(user.screenName) + "/followers");
                break;
            }
            case R.id.user_count_favorite:
            {
                openUrl(TwitterUtil.getUserHomeURL(user.screenName) + "/favorites");
                break;
            }
            case R.id.user_follow:
            {
                followButton.setText("読み込み中");
                followButton.setBackgroundColor(Client.getColor(R.color.Gray3));
                Boolean isFollowing = v.getTag() != null ? (Boolean) v.getTag() : false;
                if (isFollowing)
                {
                    new UnfollowTask(user.screenName)
                    {
                        @Override
                        public void onPostExecute(User result)
                        {
                            super.onPostExecute(result);
                            setData();
                        }
                    }.callAsync();
                }
                else
                {
                    new FollowTask(user.screenName)
                    {
                        @Override
                        public void onPostExecute(User result)
                        {
                            super.onPostExecute(result);
                            setData();
                        }
                    }.callAsync();
                }
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
