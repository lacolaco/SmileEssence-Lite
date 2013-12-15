package net.miz_hi.smileessence.command;

import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.dialog.ConfirmDialog;
import net.miz_hi.smileessence.dialog.MenuDialog;
import net.miz_hi.smileessence.preference.EnumPreferenceKey;
import net.miz_hi.smileessence.util.UiHandler;
import net.miz_hi.smileessence.view.activity.MainActivity;

public abstract class MenuCommand implements ICommand
{

    @Override
    public boolean getDefaultVisibility()
    {
        return true;
    }

    @Override
    public final void run()
    {
        new UiHandler()
        {

            @Override
            public void run()
            {
                if (MenuCommand.this instanceof IConfirmable && Client.<Boolean>getPreferenceValue(EnumPreferenceKey.CONFIRM_DIALOG))
                {
                    ConfirmDialog.show(MainActivity.getInstance(), "実行しますか？", new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            MenuDialog.dispose();
                            workOnUiThread();
                        }
                    });
                }
                else
                {
                    MenuDialog.dispose();
                    workOnUiThread();
                }
            }
        }.post();
    }

    public abstract void workOnUiThread();

}
