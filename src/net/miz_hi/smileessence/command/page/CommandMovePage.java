package net.miz_hi.smileessence.command.page;

import net.miz_hi.smileessence.command.MenuCommand;
import net.miz_hi.smileessence.system.PageController;

public class CommandMovePage extends MenuCommand
{

    String pageTitle;
    int position;

    public CommandMovePage(String title, int position)
    {
        this.pageTitle = title;
        this.position = position;
    }

    @Override
    public String getName()
    {
        return pageTitle;
    }

    @Override
    public void workOnUiThread()
    {
        PageController.getInstance().move(position);
    }

}
