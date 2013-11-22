package net.miz_hi.smileessence.command.post;

import net.miz_hi.smileessence.command.MenuCommand;
import net.miz_hi.smileessence.system.PostSystem;

public class CommandZekamashi extends MenuCommand
{

    @Override
    public void workOnUiThread()
    {
        String text = PostSystem.getText();
        StringBuilder builder = new StringBuilder(text);
        PostSystem.setText(builder.reverse().toString());
        PostSystem.openPostPage();
    }

    @Override
    public String getName()
    {
        return "ぜかまし";
    }
}
