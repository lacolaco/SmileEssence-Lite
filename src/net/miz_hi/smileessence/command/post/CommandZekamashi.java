package net.miz_hi.smileessence.command.post;

import net.miz_hi.smileessence.command.MenuCommand;
import net.miz_hi.smileessence.system.PostSystem;

public class CommandZekamashi extends MenuCommand
{

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
        StringBuilder master = new StringBuilder(text);
        if (start == end)
        {
            return master.reverse().toString();
        }
        else
        {
            String selected = text.substring(start, end);
            StringBuilder target = new StringBuilder(selected);
            target.reverse();
            selected = target.toString();
            String temp = "########TEMP########";
            for (String bracket : brackets)
            {
                String first = bracket.split(",")[0];
                String second = bracket.split(",")[1];
                selected = selected.replace(first, temp).replace(second, first).replace(temp, second);
            }
            return master.replace(start, end, selected).toString();
        }
    }

    private String[] brackets = new String[]{"（,）", "(,)", "「,」", "[,]", "{,}", "<,>", "/,\\"};


    @Override
    public String getName()
    {
        return "ぜかまし";
    }
}
