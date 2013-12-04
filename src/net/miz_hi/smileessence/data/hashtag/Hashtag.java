package net.miz_hi.smileessence.data.hashtag;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;


@DatabaseTable(tableName = "hashtags")
public class Hashtag
{

    @DatabaseField(generatedId = true)
    private int Id;
    @DatabaseField(unique = true)
    private String text;
    @DatabaseField
    private Date latestDate;

    public Hashtag()
    {
    }

    public Hashtag(String text)
    {
        this.text = text;
    }

    public int getId()
    {
        return Id;
    }

    public void setId(int id)
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

    public Date getLatestDate()
    {
        return latestDate;
    }

    public void setLatestDate(Date latestDate)
    {
        this.latestDate = latestDate;
    }
}
