package net.miz_hi.smileessence.data.search;

public class SearchManager
{

    public static void addSearch(Search search)
    {
        SearchModel.instance().save(search);
    }

    public static void deleteSearch(int id)
    {
        SearchModel.instance().delete(id);
    }

    public static java.util.List<Search> getSearches()
    {
        return SearchModel.instance().findAll();
    }
}
