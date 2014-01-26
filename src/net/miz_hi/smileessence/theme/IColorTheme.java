package net.miz_hi.smileessence.theme;

public interface IColorTheme
{

    /**
     * Color for list bg and etc.
     *
     * @return
     */
    public int getBackground1();

    /**
     * Color for alternative list bg.
     *
     * @return
     */
    public int getBackground2();

    /**
     * Color for normal text.
     *
     * @return
     */
    public int getNormalTextColor();

    /**
     * Color for header text.
     *
     * @return
     */
    public int getHeaderTextColor();

    /**
     * Color for hint text.
     *
     * @return
     */
    public int getHintTextColor();

    /**
     * Color for special text. example: header of my tweet.
     *
     * @return
     */
    public int getSpecialTextColor();

    /**
     * Color for mentions tweet background.
     *
     * @return
     */
    public int getMentionsBackgroundColor();

    /**
     * Color for retweet background.
     *
     * @return
     */
    public int getRetweetBackgroundColor();

    /**
     * Color for dialog border.
     *
     * @return
     */
    public int getDialogBorderColor();

    /**
     * Color for menu title.
     *
     * @return
     */
    public int getTitleTextColor();

    /**
     * Color for hyperlink text.
     *
     * @return
     */
    public int getHyperlinkTextColor();

    public int getMenuItemLayout();

    public int getMenuParentLayout();

    public int getMenuParentCloseIcon();

    public int getMenuParentOpenIcon();

    public int getMessageIcon();

    public int getRetweetIcon();

    public int getFavoriteOffIcon();

    public int getFavoriteOnIcon();

    public int getGarbageIcon();

    public int getMenuIcon();

    public int getPictureIcon();

    public int getConfigIcon();

    public int getDeleteIcon();
}
