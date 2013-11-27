package net.miz_hi.smileessence.menu;

import android.app.Activity;
import net.miz_hi.smileessence.command.page.CommandClosePage;
import net.miz_hi.smileessence.command.page.CommandToAddPage;
import net.miz_hi.smileessence.command.page.CommandToPageMove;
import net.miz_hi.smileessence.dialog.ExpandMenuDialog;

import java.util.ArrayList;
import java.util.List;

public class TabMenu extends ExpandMenuDialog
{

    public TabMenu(Activity activity)
    {
        super(activity);
    }

    @Override
    public List<MenuElement> getElements()
    {
        List<MenuElement> list = new ArrayList<MenuElement>();
        list.add(new MenuElement(new CommandToPageMove()));
        list.add(new MenuElement(new CommandClosePage()));
        list.add(new MenuElement(new CommandToAddPage()));
        return list;
    }
}
