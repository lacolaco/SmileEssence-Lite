package net.miz_hi.smileessence.data.hashtag;

public class HashtagManager
{

    public static void addOrUpdate(Hashtag hashtag)
    {
        HashtagModel.instance().save(hashtag);
    }

    public static void delete(int id)
    {
        HashtagModel.instance().delete(id);
    }

    public static java.util.List<Hashtag> get(int count)
    {
        return HashtagModel.instance().find(count);
    }
}
