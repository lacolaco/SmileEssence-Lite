package net.miz_hi.smileessence.theme.impl;

import net.miz_hi.smileessence.R;
import net.miz_hi.smileessence.theme.IColorTheme;

public class DarkColorTheme implements IColorTheme
{

    @Override
    public int getBackground1()
    {
        return R.color.Gray;
    }

    @Override
    public int getBackground2()
    {
        return R.color.Gray4;
    }

    @Override
    public int getNormalTextColor()
    {
        return R.color.LightGray;
    }

    @Override
    public int getHeaderTextColor()
    {
        return R.color.Green;
    }

    @Override
    public int getHintTextColor()
    {
        return R.color.Gray3;
    }

    @Override
    public int getSpecialTextColor()
    {
        return R.color.DarkBlue;
    }

    @Override
    public int getMentionsBackgroundColor()
    {
        return R.color.DarkRed;
    }

    @Override
    public int getRetweetBackgroundColor()
    {
        return R.color.DarkerBlue;
    }

    @Override
    public int getDialogBorderColor()
    {
        return R.color.MetroBlue;
    }

    @Override
    public int getTitleTextColor()
    {
        return R.color.MetroBlue;
    }

    @Override
    public int getHyperlinkTextColor()
    {
        return R.color.MetroBlue;
    }

    @Override
    public int getMenuItemLayout()
    {
        return R.layout.menuitem_dark;
    }

    @Override
    public int getMenuParentLayout()
    {
        return R.layout.menuparent_dark;
    }

    @Override
    public int getMenuParentCloseIcon()
    {
        return R.drawable.expand_close_white;
    }

    @Override
    public int getMenuParentOpenIcon()
    {
        return R.drawable.expand_open_white;
    }

    @Override
    public int getMessageIcon()
    {
        return R.drawable.icon_message_white;
    }

    @Override
    public int getRetweetIcon()
    {
        return R.drawable.icon_retweet_white;
    }

    @Override
    public int getFavoriteOffIcon()
    {
        return R.drawable.icon_favorite_off_white;
    }

    @Override
    public int getFavoriteOnIcon()
    {
        return R.drawable.icon_favorite_on;
    }

    @Override
    public int getGarbageIcon()
    {
        return R.drawable.icon_garbage_white;
    }

    @Override
    public int getMenuIcon()
    {
        return R.drawable.icon_menu_white;
    }

    @Override
    public int getPictureIcon()
    {
        return R.drawable.icon_picture_white;
    }

    @Override
    public int getConfigIcon()
    {
        return R.drawable.icon_config_white;
    }

    @Override
    public int getDeleteIcon()
    {
        return R.drawable.icon_delete_white;
    }
}
