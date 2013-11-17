package net.miz_hi.smileessence.command.user;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import net.miz_hi.smileessence.twitter.TwitterUtil;

public class UserCommandOpenPage extends UserCommand
{

    private Activity activity;

    public UserCommandOpenPage(String userName, Activity activity)
    {
        super(userName);
        this.activity = activity;
    }

    @Override
    public String getName()
    {
        return "ユーザーページを開く";
    }

    @Override
    public void workOnUiThread()
    {
        String userPage = TwitterUtil.getUserHomeURL(userName);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(userPage));
        activity.startActivity(intent);
    }
}
