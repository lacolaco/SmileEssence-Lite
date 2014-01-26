package net.miz_hi.smileessence.command.main;

import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.R;
import net.miz_hi.smileessence.command.MenuCommand;
import net.miz_hi.smileessence.system.PostSystem;

public class CommandCommercial extends MenuCommand
{

    @Override
    public String getName()
    {
        return "宣伝する";
    }

    @Override
    public void workOnUiThread()
    {
        String str = "Android用Twitterクライアント「SmileEssence Lite」をチェック！\r\n " + Client.getMainActivity().getString(R.string.app_url);
        PostSystem.setText(str);
        PostSystem.openPostPage();
    }

}
