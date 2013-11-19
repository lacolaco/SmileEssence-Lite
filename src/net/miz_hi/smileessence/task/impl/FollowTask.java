package net.miz_hi.smileessence.task.impl;

import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.notification.Notificator;
import net.miz_hi.smileessence.task.Task;
import net.miz_hi.smileessence.twitter.API;
import net.miz_hi.smileessence.twitter.ResponseConverter;
import twitter4j.TwitterException;
import twitter4j.User;

public class FollowTask extends Task<User>
{

    private String userName;

    public FollowTask(String userName)
    {
        this.userName = userName;
    }

    @Override
    public void onPreExecute()
    {
    }

    @Override
    public void onPostExecute(User result)
    {
        if (result != null)
        {
            ResponseConverter.convert(result);
            Notificator.info("フォローしました");
        }
        else
        {
            Notificator.alert("フォロー失敗しました");
        }
    }

    @Override
    public User call() throws Exception
    {
        try
        {
            return API.follow(Client.getMainAccount(), userName);
        }
        catch (TwitterException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
