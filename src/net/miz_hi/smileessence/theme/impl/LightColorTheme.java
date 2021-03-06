package net.miz_hi.smileessence.theme.impl;

import net.miz_hi.smileessence.R;
import net.miz_hi.smileessence.theme.IColorTheme;

public class LightColorTheme implements IColorTheme
{

    @Override
    public int getBackground1()
    {
        return R.color.White;
    }

    @Override
    public int getBackground2()
    {
        return R.color.LightGray;
    }

    @Override
    public int getNormalTextColor()
    {
        return R.color.Gray;
    }

    @Override
    public int getHeaderTextColor()
    {
        return R.color.ThickGreen;
    }

    @Override
    public int getHintTextColor()
    {
        return R.color.Gray2;
    }

    @Override
    public int getSpecialTextColor()
    {
        return R.color.DarkBlue;
    }

    @Override
    public int getMentionsBackgroundColor()
    {
        return R.color.LightRed;
    }

    @Override
    public int getRetweetBackgroundColor()
    {
        return R.color.LightBlue;
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
        return R.layout.menuitem_white;
    }

    @Override
    public int getMenuParentLayout()
    {
        return R.layout.menuparent_white;
    }

    @Override
    public int getMenuParentCloseIcon()
    {
        return R.drawable.expand_close;
    }

    @Override
    public int getMenuParentOpenIcon()
    {
        return R.drawable.expand_open;
    }

    @Override
    public int getMessageIcon()
    {
        return R.drawable.icon_message;
    }

    @Override
    public int getRetweetIcon()
    {
        return R.drawable.icon_retweet_off;
    }

    @Override
    public int getFavoriteOffIcon()
    {
        return R.drawable.icon_favorite_off;
    }

    @Override
    public int getFavoriteOnIcon()
    {
        return R.drawable.icon_favorite_on;
    }

    @Override
    public int getGarbageIcon()
    {
        return R.drawable.icon_garbage;
    }

    @Override
    public int getMenuIcon()
    {
        return R.drawable.icon_menu;
    }

    @Override
    public int getPictureIcon()
    {
        return R.drawable.icon_pict;
    }

    @Override
    public int getConfigIcon()
    {
        return R.drawable.icon_config;
    }

    @Override
    public int getDeleteIcon()
    {
        return R.drawable.icon_delete;
    }


}
