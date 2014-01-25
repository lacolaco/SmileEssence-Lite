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
}
