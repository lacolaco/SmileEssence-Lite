package net.miz_hi.smileessence.command.main;

import android.support.v4.app.Fragment;
import net.miz_hi.smileessence.command.MenuCommand;
import net.miz_hi.smileessence.system.PageController;
import net.miz_hi.smileessence.view.fragment.IRemovable;


public class CommandClosePage extends MenuCommand
{

    @Override
    public String getName()
    {
        return "タブを閉じる";
    }

    @Override
    public void workOnUiThread()
    {
        PageController.getInstance().removePage();
    }

    @Override
    public boolean getDefaultVisibility()
    {
        Fragment fragment = PageController.getInstance().getPage(PageController.getInstance().getCurrentPage());
        return fragment != null && fragment instanceof IRemovable;
    }

}
