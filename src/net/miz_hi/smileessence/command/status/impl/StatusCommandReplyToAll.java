package net.miz_hi.smileessence.command.status.impl;

import net.miz_hi.smileessence.command.status.StatusCommand;
import net.miz_hi.smileessence.model.status.tweet.TweetModel;
import net.miz_hi.smileessence.system.PostSystem;

public class StatusCommandReplyToAll extends StatusCommand
{

    public StatusCommandReplyToAll(TweetModel status)
    {
        super(status);
    }

    @Override
    public String getName()
    {
        return "全員に返信";
    }

    @Override
    public void workOnUiThread()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(".");
        for (String name : status.getScreenNames())
        {
            builder.append("@").append(name).append(" ");
        }
        PostSystem.setText(builder.toString());
        PostSystem.getState().setCursor(builder.length());
        PostSystem.openPostPage();
    }

    @Override
    public boolean getDefaultVisibility()
    {
        return status.getScreenNames().size() > 1;
    }

}
