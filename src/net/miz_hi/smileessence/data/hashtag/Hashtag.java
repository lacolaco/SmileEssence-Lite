package net.miz_hi.smileessence.data.hashtag;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "hashtags")
public class Hashtag
{

    @DatabaseField(generatedId = true)
    private long Id;
    @DatabaseField
    private String text;
    @DatabaseField
    private int usedCount;

    public Hashtag()
    {
    }

    public Hashtag(String text, int usedCount)
    {
        this.text = text;
        this.usedCount = usedCount;
    }

    public long getId()
    {
        return Id;
    }

    public void setId(long id)
    {
        this.Id = id;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public int getUsedCount()
    {
        return usedCount;
    }

    public void setUsedCount(int usedCount)
    {
        this.usedCount = usedCount;
    }
}
