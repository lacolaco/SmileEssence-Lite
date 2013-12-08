package net.miz_hi.smileessence.twitter;

import net.miz_hi.smileessence.model.status.tweet.TweetModel;

public class TwitterUtil
{

    public static String getUserHomeURL(String screenName)
    {
        return "http://twitter.com/" + screenName;
    }

    public static String getAclogURL(String screenName)
    {
        return "http://aclog.koba789.com/" + screenName + "/timeline";
    }

    public static String getFavstarURL(String screenName)
    {
        return "http://favstar.fm/users/" + screenName + "/recent";
    }

    public static String getTwilogURL(String screenName)
    {
        return "http://twilog.org/" + screenName;
    }

    public static String getTweetURL(TweetModel tweet)
    {
        return String.format("https://twitter.com/%s/status/%s", tweet.getOriginal().user.screenName, tweet.getOriginal().statusId);
    }

    public static String getSTOTString(TweetModel tweet)
    {
        return String.format("%s: %s [%s]", tweet.getOriginal().user.screenName, tweet.getText(), getTweetURL(tweet));
    }
}
