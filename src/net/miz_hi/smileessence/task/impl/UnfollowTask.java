package net.miz_hi.smileessence.task.impl;

import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.notification.Notificator;
import net.miz_hi.smileessence.task.Task;
import net.miz_hi.smileessence.twitter.API;
import net.miz_hi.smileessence.twitter.ResponseConverter;
import twitter4j.TwitterException;
import twitter4j.User;

public class UnfollowTask extends Task<User>
{

    private String userName;

    public UnfollowTask(String userName)
    {
        this.userName = userName;
    }

    @Override
    public User call()
    {
        try
        {
            return API.unfollow(Client.getMainAccount(), userName);
        }
        catch (TwitterException e)
        {
            e.printStackTrace();
            return null;
        }
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
            Notificator.info("リムーヴしました");
        }
        else
        {
            Notificator.alert("リムーヴ失敗しました");
        }
    }
}
