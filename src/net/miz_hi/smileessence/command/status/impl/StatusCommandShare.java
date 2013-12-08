package net.miz_hi.smileessence.command.status.impl;

import android.app.Activity;
import android.content.Intent;
import net.miz_hi.smileessence.command.IHideable;
import net.miz_hi.smileessence.command.status.StatusCommand;
import net.miz_hi.smileessence.model.status.tweet.TweetModel;
import net.miz_hi.smileessence.twitter.TwitterUtil;

public class StatusCommandShare extends StatusCommand implements IHideable
{

    private Activity activity;

    public StatusCommandShare(TweetModel status, Activity activity)
    {
        super(status);
        this.activity = activity;
    }

    @Override
    public void workOnUiThread()
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, TwitterUtil.getSTOTString(status));
        activity.startActivity(intent);
    }

    @Override
    public String getName()
    {
        return "共有";
    }
}
