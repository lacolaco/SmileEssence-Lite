package net.miz_hi.smileessence.command.post;

import net.miz_hi.smileessence.command.MenuCommand;
import net.miz_hi.smileessence.system.PostSystem;

public class CommandMakeAnonymous extends MenuCommand
{

    @Override
    public String getName()
    {
        return "匿名にする";
    }

    @Override
    public void workOnUiThread()
    {
        String text = PostSystem.getText();
        int start = PostSystem.getSelectionStart();
        int end = PostSystem.getSelectionEnd();
        PostSystem.setText(edit(text, start, end));
        PostSystem.openPostPage();
    }

    private String edit(String text, int start, int end)
    {
        if (start == end)
        {
            return "？？？「" + text + "」";
        }
        else
        {
            StringBuilder master = new StringBuilder(text);
            String selected = text.substring(start, end);
            selected = "？？？「" + selected + "」";
            return master.replace(start, end, selected).toString();
        }
    }

}
