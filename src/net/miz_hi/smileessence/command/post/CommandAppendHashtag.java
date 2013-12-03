package net.miz_hi.smileessence.command.post;

import net.miz_hi.smileessence.command.MenuCommand;
import net.miz_hi.smileessence.data.hashtag.Hashtag;
import net.miz_hi.smileessence.system.PostSystem;

public class CommandAppendHashtag extends MenuCommand
{

    private Hashtag hashtag;

    public CommandAppendHashtag(Hashtag hashtag)
    {
        this.hashtag = hashtag;
    }

    @Override
    public String getName()
    {
        return "#" + hashtag.getText();
    }

    @Override
    public void workOnUiThread()
    {
        PostSystem.appendText(" #" + hashtag);
        PostSystem.openPostPage();
    }
}
