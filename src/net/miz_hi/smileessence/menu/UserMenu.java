package net.miz_hi.smileessence.menu;

import android.app.Activity;
import net.miz_hi.smileessence.command.MenuCommand;
import net.miz_hi.smileessence.command.user.*;
import net.miz_hi.smileessence.dialog.SimpleMenuDialog;
import net.miz_hi.smileessence.model.status.user.UserModel;
import net.miz_hi.smileessence.status.StatusViewFactory;

import java.util.ArrayList;
import java.util.List;

public class UserMenu extends SimpleMenuDialog
{

    private String userName;

    public UserMenu(Activity activity, UserModel model)
    {
        super(activity);
        this.userName = model.screenName;
        setTitle(StatusViewFactory.newInstance(inflater, null).getStatusView(model));
    }

    @Override
    public List<MenuCommand> getMenuList()
    {
        List<MenuCommand> items = new ArrayList<MenuCommand>();
        items.add(new UserCommandReply(userName));
        items.add(new UserCommandAddReply(userName));
        items.add(new UserCommandOpenInfo(userName, activity));
        items.add(new UserCommandOpenTimeline(userName, activity));
        items.add(new UserCommandOpenFavstar(userName, activity));
        items.add(new UserCommandOpenAclog(userName, activity));
        items.add(new UserCommandOpenTwilog(userName, activity));
        items.add(new UserCommandBlock(userName));
        items.add(new UserCommandSpam(userName));
        return items;
    }
}
