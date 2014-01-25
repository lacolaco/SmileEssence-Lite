package net.miz_hi.smileessence.theme;

import net.miz_hi.smileessence.R;

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
}
