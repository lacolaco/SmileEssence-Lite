package net.miz_hi.smileessence.command.user;

import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.command.IConfirmable;
import net.miz_hi.smileessence.task.impl.UnfollowTask;

public class UserCommandUnfollow extends UserCommand implements IConfirmable
{

    public UserCommandUnfollow(String userName)
    {
        super(userName);
    }

    @Override
    public String getName()
    {
        return "リムーヴする";
    }

    @Override
    public void workOnUiThread()
    {
        new UnfollowTask(userName).callAsync();
    }

    @Override
    public boolean getDefaultVisibility()
    {
        return !Client.getMainAccount().getScreenName().equals(userName);
    }

}