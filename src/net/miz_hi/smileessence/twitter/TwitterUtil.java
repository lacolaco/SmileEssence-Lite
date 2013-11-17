package net.miz_hi.smileessence.twitter;

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
}
