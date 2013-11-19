package net.miz_hi.smileessence.model.status.tweet;

import android.text.Html;
import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.cache.TweetCache;
import net.miz_hi.smileessence.model.status.IStatusModel;
import net.miz_hi.smileessence.model.status.user.UserModel;
import net.miz_hi.smileessence.preference.EnumPreferenceKey;
import net.miz_hi.smileessence.status.EnumNameStyle;
import net.miz_hi.smileessence.status.TweetUtils;
import net.miz_hi.smileessence.task.impl.DestroyTask;
import net.miz_hi.smileessence.task.impl.FavoriteTask;
import net.miz_hi.smileessence.task.impl.RetweetTask;
import net.miz_hi.smileessence.task.impl.UnFavoriteTask;
import net.miz_hi.smileessence.twitter.ResponseConverter;
import net.miz_hi.smileessence.util.StringUtils;
import twitter4j.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * data model for view and menu
 */
public class TweetModel implements Comparable<TweetModel>, IStatusModel
{

    public Date createdAt;
    public long statusId;
    public UserModel user;
    private long inReplyToStatusId;
    private String text;
    private URLEntity[] urls;
    private MediaEntity[] medias;
    private HashtagEntity[] hashtags;
    private UserMentionEntity[] userMentions;
    private String source;
    public EnumTweetType type = EnumTweetType.NORMAL;
    /**
     * 子ツイート(RTでない場合は自分自身)
     */
    private TweetModel original;
    private List<TweetModel> parents = new ArrayList<TweetModel>();
    private boolean isFavorited;
    private boolean isRetweeted;
    private int favoriteCount;
    private int retweetCount;
    private long myRetweetId;

    public TweetModel(Status status)
    {
        statusId = status.getId();
        updateData(status);
    }

    public void updateData(Status status)
    {
        createdAt = status.getCreatedAt();
        text = status.getText();
        source = Html.fromHtml(status.getSource()).toString();
        inReplyToStatusId = status.getInReplyToStatusId();
        user = ResponseConverter.convert(status.getUser());
        if (status.isRetweet())
        {
            original = ResponseConverter.convert(status.getRetweetedStatus());
            original.addParent(this);
        }
        else
        {
            original = this;
        }
        retweetCount = status.getRetweetCount();
        favoriteCount = status.getFavoriteCount();
        hashtags = status.getHashtagEntities();
        urls = status.getURLEntities();
        medias = status.getMediaEntities();
        userMentions = status.getUserMentionEntities();
        isFavorited = status.isFavorited();
        isRetweeted = status.isRetweeted();

        myRetweetId = status.isRetweetedByMe() ? status.getCurrentUserRetweetId() : -1L;

        if (hashtags != null)
        {
            for (HashtagEntity hashtag : hashtags)
            {
                TweetCache.putHashtag(hashtag.getText());
            }
        }

        type = status.isRetweet() ? EnumTweetType.RETWEET : (TweetUtils.isReply(status) ? EnumTweetType.REPLY : EnumTweetType.NORMAL);
    }

    @Override
    public int compareTo(TweetModel another)
    {
        return another.createdAt.compareTo(this.createdAt);
    }

    private TweetModel()
    {
    }

    public static TweetModel getSampleModel()
    {
        TweetModel status = new TweetModel();
        status.original = status;
        status.createdAt = new Date();
        status.statusId = 0;
        status.text = "";
        status.source = "";
        status.inReplyToStatusId = 0;
        status.user = UserModel.getNullUserModel();
        status.favoriteCount = 0;
        status.retweetCount = 0;
        status.hashtags = new HashtagEntity[0];
        status.urls = new URLEntity[0];
        status.medias = new MediaEntity[0];
        status.userMentions = new UserMentionEntity[0];
        status.isFavorited = false;
        status.isRetweeted = false;
        status.myRetweetId = -1L;
        status.type = EnumTweetType.NORMAL;
        return status;
    }

    public TweetModel getOriginal()
    {
        return original;
    }

    public List<TweetModel> getParents()
    {
        return parents;
    }

    public void addParent(TweetModel parent)
    {
        parents.add(parent);
    }

    public void deleteParent(TweetModel parent)
    {
        parents.remove(parent);
    }

    /*
        getter methods
     */

    public long getInReplyToStatusId()
    {
        return original.inReplyToStatusId;
    }

    public String getText()
    {
        return original.text;
    }

    public String getSource()
    {
        return original.source;
    }

    public URLEntity[] getUrls()
    {
        return original.urls;
    }

    public MediaEntity[] getMedias()
    {
        return original.medias;
    }

    public HashtagEntity[] getHashtags()
    {
        return original.hashtags;
    }

    public UserMentionEntity[] getUserMentions()
    {
        return original.userMentions;
    }

    public boolean isFavorited()
    {
        return original.isFavorited;
    }

    public boolean isRetweeted()
    {
        return original.isRetweeted;
    }

    public int getFavoriteCount()
    {
        return original.favoriteCount;
    }

    public int getRetweetCount()
    {
        return original.retweetCount;
    }

    public boolean isRetweetByMe()
    {
        return myRetweetId > -1L;
    }

    public long getMyRetweetId()
    {
        return myRetweetId;
    }

    /*
        action methods
     */

    /**
     * do destroy async
     */
    public void destroy()
    {
        if (user.isMe())
        {
            new DestroyTask(statusId).callAsync();
        }
        else
        {
            new DestroyTask(original.statusId).callAsync();
        }
    }

    /**
     * do favorite async
     */
    public void favorite()
    {
        new FavoriteTask(original.statusId).callAsync();
    }

    /**
     * do unfavorite async
     */
    public void unfavorite()
    {
        new UnFavoriteTask(original.statusId).callAsync();
    }

    /**
     * do retweet async
     */
    public void retweet()
    {
        new RetweetTask(original.statusId).callAsync();
    }

    public void setFavorited(boolean favorited)
    {
        original.isFavorited = favorited;
    }

    /**
     * returns list of screenName in own text
     */
    public List<String> getScreenNames()
    {
        List<String> names = new ArrayList<String>();
        names.add(original.user.screenName);
        if (original.userMentions != null)
        {
            for (UserMentionEntity entity : original.userMentions)
            {
                if (entity.getScreenName().equals(Client.getMainAccount().getScreenName()))
                {
                    continue;
                }
                if (names.contains(entity.getScreenName()))
                {
                    continue;
                }
                names.add(entity.getScreenName());
            }
        }
        return names;
    }

    @Override
    public UserModel getUser()
    {
        return original.user;
    }

    @Override
    public String getTextTop()
    {
        UserModel shownUser = original.user;
        StringBuilder builder = new StringBuilder();
        String style = Client.getPreferenceValue(EnumPreferenceKey.NAME_STYLE);
        if (style.equals(EnumNameStyle.S_N.get()) || style.equals(EnumNameStyle.S.get()))
        {
            builder.append(shownUser.screenName);
        }
        else if (style.equals(EnumNameStyle.N_S.get()) || style.equals(EnumNameStyle.N.get()))
        {
            builder.append(shownUser.name);
        }
        if (style.equals(EnumNameStyle.S_N.get()))
        {
            builder.append(" / ");
            builder.append(shownUser.name);
        }
        else if (style.equals(EnumNameStyle.N_S.get()))
        {
            builder.append(" / ");
            builder.append(shownUser.screenName);
        }
        return builder.toString();
    }

    @Override
    public String getTextContent()
    {
        return getText();
    }

    @Override
    public String getTextBottom()
    {
        StringBuilder builder = new StringBuilder();
        if (type == EnumTweetType.RETWEET)
        {
            builder.append("(RT: ");
            builder.append(user.screenName);
            builder.append(") ");
            builder.append(StringUtils.dateToString(original.createdAt));
            builder.append(" via ");
            builder.append(original.source);
        }
        else
        {
            builder.append(StringUtils.dateToString(createdAt));
            builder.append(" via ");
            builder.append(source);
        }
        return builder.toString();
    }
}
