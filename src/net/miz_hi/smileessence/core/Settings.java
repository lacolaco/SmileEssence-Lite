package net.miz_hi.smileessence.core;

import net.miz_hi.smileessence.theme.IColorTheme;

public class Settings
{

    private IColorTheme theme;

    private int textSize;

    public int getTextSize()
    {
        return textSize;
    }

    public void setTextSize(int textSize)
    {
        this.textSize = textSize;
    }

    public IColorTheme getTheme()
    {
        return theme;
    }

    public void setTheme(IColorTheme theme)
    {
        this.theme = theme;
    }
}
