package net.miz_hi.smileessence.command.user;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import net.miz_hi.smileessence.twitter.TwitterUtil;

public class UserCommandOpenFavstar extends UserCommand
{

    private Activity activity;

    public UserCommandOpenFavstar(String userName, Activity activity)
    {
        super(userName);
        this.activity = activity;
    }

    @Override
    public String getName()
    {
        return "Favstarを開く";
    }

    @Override
    public void workOnUiThread()
    {
        String url = TwitterUtil.getFavstarURL(userName);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(intent);
    }
}
