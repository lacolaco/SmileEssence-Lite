package net.miz_hi.smileessence.command.post;

import net.miz_hi.smileessence.command.MenuCommand;
import net.miz_hi.smileessence.system.PostSystem;
import net.miz_hi.smileessence.util.Morse;

public class CommandParseMorse extends MenuCommand
{

    public CommandParseMorse()
    {
    }

    @Override
    public String getName()
    {
        return "モールスに変換";
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
            return Morse.jaToMc(text);
        }
        else
        {
            StringBuilder master = new StringBuilder(text);
            String selected = text.substring(start, end);
            selected = " " + Morse.jaToMc(selected) + " ";
            return master.replace(start, end, selected).toString();
        }
    }

    /**
     * Test
     *
     * @param args
     */
    public static void main(String[] args)
    {
        CommandParseMorse command = new CommandParseMorse();
        String test = "test test test";
        int start = 5;
        int end = 9;
        System.out.println(test);
        test = command.edit(test, start, end);
        System.out.println(test);
        test = command.edit(test, 0, 0);
        System.out.println(test);
        System.out.println(Morse.mcToJa(test));
    }

}
