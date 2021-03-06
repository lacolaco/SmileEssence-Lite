package net.miz_hi.smileessence.cache;

import net.miz_hi.smileessence.model.status.tweet.TweetModel;
import net.miz_hi.smileessence.task.impl.ShowTweetTask;
import net.miz_hi.smileessence.twitter.ResponseConverter;
import twitter4j.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class TweetCache
{

    private static TweetCache instance = new TweetCache();

    private ConcurrentHashMap<Long, TweetModel> statusesMap = new ConcurrentHashMap<Long, TweetModel>();
    private CopyOnWriteArrayList<Long> readRetweetList = new CopyOnWriteArrayList<Long>();

    public static TweetModel put(Status status)
    {
        TweetModel model;
        if (instance.statusesMap.containsKey(status.getId()))
        {
            model = instance.statusesMap.get(status.getId());
            model.updateData(status);
        }
        else
        {
            model = new TweetModel(status);
            instance.statusesMap.put(status.getId(), model);
        }
        if (status.isRetweet())
        {
            instance.readRetweetList.add(status.getId());
            new ShowTweetTask(status.getRetweetedStatus().getId())
            {
                @Override
                public void onPostExecute(Status result)
                {
                    if (result != null)
                    {
                        ResponseConverter.convert(result);
                    }
                }
            }.callAsync();
        }
        return model;
    }

    public static List<TweetModel> getList()
    {
        return new ArrayList<TweetModel>(instance.statusesMap.values());
    }

    public static TweetModel get(long id)
    {
        return instance.statusesMap.get(id);
    }

    public static TweetModel remove(long id)
    {
        return instance.statusesMap.remove(id);
    }

    public static boolean isNotRead(long id)
    {
        int count = 0;
        for (Long l : instance.readRetweetList)
        {
            if (id == l)
            {
                count++;
            }
        }
        return count <= 1;
    }

    public static void clearCache()
    {
        instance.statusesMap.clear();
        instance.readRetweetList.clear();
    }
}
