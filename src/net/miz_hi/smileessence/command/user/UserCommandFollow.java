package net.miz_hi.smileessence.command.user;

import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.command.IConfirmable;
import net.miz_hi.smileessence.task.impl.FollowTask;

public class UserCommandFollow extends UserCommand implements IConfirmable
{

    public UserCommandFollow(String userName)
    {
        super(userName);
    }

    @Override
    public String getName()
    {
        return "フォローする";
    }

    @Override
    public void workOnUiThread()
    {
        new FollowTask(userName).callAsync();
    }

    @Override
    public boolean getDefaultVisibility()
    {
        return !Client.getMainAccount().getScreenName().equals(userName);
    }

}