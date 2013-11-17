package net.miz_hi.smileessence.util;

import android.net.Uri;
import twitter4j.URLEntity;
import twitter4j.util.CharacterUtil;

import java.util.Calendar;
import java.util.Date;

public class StringUtils
{

    public static String parseUrlToFileName(String string)
    {
        return Uri.parse(string).getLastPathSegment();
    }

    public static String dateToString(Date date)
    {
        Calendar cal = Calendar.getInstance();
        Calendar calToday = Calendar.getInstance();

        cal.setTime(date);

        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DATE);
        int h = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        int s = cal.get(Calendar.SECOND);

        StringBuilder builder = new StringBuilder();

        if (cal.get(Calendar.YEAR) != calToday.get(Calendar.YEAR))
        {
            builder.append(y).append("/");
        }
        if (cal.get(Calendar.DAY_OF_YEAR) != calToday.get(Calendar.DAY_OF_YEAR))
        {
            builder.append(String.format("%02d", m + 1)).append("/").append(String.format("%02d", d)).append(" ");
        }
        builder.append(String.format("%02d", h)).append(":").append(String.format("%02d", min)).append(":").append(String.format("%02d", s));

        return builder.toString();
    }

    public static int countTweetCharacters(String text)
    {
        return CharacterUtil.count(text);
    }

    public static String replaceUrlEntity(String text, URLEntity[] entities)
    {
        StringBuilder builder = new StringBuilder(text);
        if (entities.length == 0)
        {
            return builder.toString();
        }
        for (int i = entities.length - 1; i >= 0; i--)
        {
            URLEntity entity = entities[i];
            builder.replace(entity.getStart(), entity.getEnd(), entity.getExpandedURL());
        }
        return builder.toString();
    }

}
