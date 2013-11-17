package net.miz_hi.smileessence.command.user;

import android.app.Activity;
import net.miz_hi.smileessence.cache.UserCache;
import net.miz_hi.smileessence.menu.UserMenu;
import net.miz_hi.smileessence.model.status.ResponseConverter;
import net.miz_hi.smileessence.model.status.user.UserModel;
import net.miz_hi.smileessence.task.impl.GetUserTask;
import twitter4j.User;

public class UserCommandUserMenu extends UserCommand
{

    private Activity activity;

    public UserCommandUserMenu(String userName, Activity activity)
    {
        super(userName);
        this.activity = activity;
    }

    @Override
    public void workOnUiThread()
    {
        UserModel user = UserCache.getByScreenName(userName);
        if (user != null)
        {
            new UserMenu(activity, user).create().show();
        }
        else
        {
            new GetUserTask(userName)
            {
                @Override
                public void onPostExecute(User result)
                {
                    UserModel model = ResponseConverter.convert(result);
                    new UserMenu(activity, model).create().show();
                }
            }.callAsync();
        }
    }

    @Override
    public String getName()
    {
        return "ユーザーメニューを開く";
    }
}
