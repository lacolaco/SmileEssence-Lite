package net.miz_hi.smileessence.data.search;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "searches")
public class Search
{

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String query;

    public Search()
    {
    }

    public Search(String query)
    {
        this.query = query;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getQuery()
    {
        return query;
    }

    public void setQuery(String query)
    {
        this.query = query;
    }
}
