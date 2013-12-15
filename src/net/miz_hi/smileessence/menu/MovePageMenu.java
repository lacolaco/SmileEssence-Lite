package net.miz_hi.smileessence.menu;

import android.app.Activity;
import net.miz_hi.smileessence.command.MenuCommand;
import net.miz_hi.smileessence.command.page.CommandMovePage;
import net.miz_hi.smileessence.dialog.SimpleMenuDialog;
import net.miz_hi.smileessence.system.PageController;
import net.miz_hi.smileessence.view.fragment.NamedFragment;

import java.util.ArrayList;
import java.util.List;

public class MovePageMenu extends SimpleMenuDialog
{

    public MovePageMenu(Activity activity)
    {
        super(activity);
        setTitle("移動先のタブを選択");
    }

    @Override
    public List<MenuCommand> getMenuList()
    {
        List<MenuCommand> commands = new ArrayList<MenuCommand>();
        for (int i = 0; i < PageController.getInstance().getCount(); i++)
        {
            NamedFragment fragment = PageController.getInstance().getPage(i);
            commands.add(new CommandMovePage(fragment.getTitle(), i));
        }
        return commands;
    }

}
