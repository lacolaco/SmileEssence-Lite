package net.miz_hi.smileessence.command.user;

import net.miz_hi.smileessence.notification.Notificator;
import net.miz_hi.smileessence.system.PostSystem;

public class UserCommandAddReply extends UserCommand
{

    public UserCommandAddReply(String userName)
    {
        super(userName);
    }

    @Override
    public String getName()
    {
        return "返信先に追加";
    }

    @Override
    public void workOnUiThread()
    {
        PostSystem.addReply(userName);
        Notificator.info(userName + "を返信先に追加しました");
    }
}
