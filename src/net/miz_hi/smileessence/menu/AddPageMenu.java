package net.miz_hi.smileessence.menu;

import android.app.Activity;
import net.miz_hi.smileessence.command.ICommand;
import net.miz_hi.smileessence.command.page.CommandCreateNewListPage;
import net.miz_hi.smileessence.command.page.CommandCreateNewSearchPage;
import net.miz_hi.smileessence.dialog.SimpleMenuDialog;

import java.util.ArrayList;
import java.util.List;

public class AddPageMenu extends SimpleMenuDialog
{

    public AddPageMenu(Activity activity)
    {
        super(activity);
        setTitle("追加するタブを選択");
    }

    @Override
    public List<ICommand> getMenuList()
    {
        List<ICommand> commands = new ArrayList<ICommand>();

        commands.add(new CommandCreateNewListPage(activity));
        commands.add(new CommandCreateNewSearchPage(activity));

        return commands;
    }


}
