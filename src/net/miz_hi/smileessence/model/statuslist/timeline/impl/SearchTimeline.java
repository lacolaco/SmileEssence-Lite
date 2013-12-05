package net.miz_hi.smileessence.model.statuslist.timeline.impl;

import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.model.status.IStatusModel;
import net.miz_hi.smileessence.model.status.tweet.TweetModel;
import net.miz_hi.smileessence.model.statuslist.timeline.Timeline;
import net.miz_hi.smileessence.notification.Notificator;
import net.miz_hi.smileessence.preference.EnumPreferenceKey;
import net.miz_hi.smileessence.task.impl.SearchTask;
import net.miz_hi.smileessence.twitter.ResponseConverter;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;


public class SearchTimeline extends Timeline
{

    static final String LANG = "ja";
    static final int COUNT = 20;

    String queryString;
    Query query;
    long maxId;
    long minId;

    public SearchTimeline(String queryString)
    {
        this.queryString = queryString;
    }

    private void initQuery()
    {
        query = new Query();
        query.setQuery(queryString);
        query.setLang(LANG);
        query.setCount(COUNT);
        query.setResultType(Query.RECENT);
    }

    @Override
    public Future loadNewer(Runnable callback)
    {
        initQuery();
        if (getStatusList().length > 0)
        {
            maxId = ((TweetModel) getStatus(0)).statusId;
            query.setSinceId(maxId);
            return new SearchTask(Client.getMainAccount(), query)
            {
                @Override
                public void onPostExecute(QueryResult result)
                {
                    int count = 0;
                    maxId = result.getMaxId();
                    List<Status> statuses = result.getTweets();
                    Collections.reverse(statuses);
                    for (Status status : statuses)
                    {
                        if (!Client.<Boolean>getPreferenceValue(EnumPreferenceKey.SEARCH_INCLUDE_RT) && status.isRetweet())
                        {
                            continue;
                        }
                        addToTop(ResponseConverter.convert(status));
                        count++;
                    }
                    applyForce();
                    Notificator.info(count + "件読み込みました");
                }
            }.setCallBack(callback).callAsync();
        }
        else
        {
            return new SearchTask(Client.getMainAccount(), query)
            {
                @Override
                public void onPostExecute(QueryResult result)
                {
                    int count = 0;
                    List<Status> statuses = result.getTweets();
                    Collections.reverse(statuses);
                    for (Status status : statuses)
                    {
                        if (!Client.<Boolean>getPreferenceValue(EnumPreferenceKey.SEARCH_INCLUDE_RT) && status.isRetweet())
                        {
                            continue;
                        }
                        addToTop(ResponseConverter.convert(status));
                        count++;
                    }
                    applyForce();
                    Notificator.info(count + "件読み込みました");
                }
            }.setCallBack(callback).callAsync();
        }
    }

    @Override
    public Future loadOlder(Runnable callback)
    {
        initQuery();
        if (getStatusList().length > 0)
        {
            minId = ((TweetModel) getStatus(getStatusList().length - 1)).statusId;
            query.setMaxId(minId);
            return new SearchTask(Client.getMainAccount(), query)
            {
                @Override
                public void onPostExecute(QueryResult result)
                {
                    int count = 0;
                    minId = result.getSinceId();
                    List<Status> statuses = result.getTweets();
                    for (Status status : statuses)
                    {
                        if (!Client.<Boolean>getPreferenceValue(EnumPreferenceKey.SEARCH_INCLUDE_RT) && status.isRetweet())
                        {
                            continue;
                        }
                        addToBottom(ResponseConverter.convert(status));
                        count++;
                    }
                    applyForce();
                    Notificator.info(count + "件読み込みました");
                }
            }.setCallBack(callback).callAsync();
        }
        else
        {
            return new SearchTask(Client.getMainAccount(), query)
            {
                @Override
                public void onPostExecute(QueryResult result)
                {
                    int count = 0;
                    List<Status> statuses = result.getTweets();
                    for (Status status : statuses)
                    {
                        if (!Client.<Boolean>getPreferenceValue(EnumPreferenceKey.SEARCH_INCLUDE_RT) && status.isRetweet())
                        {
                            continue;
                        }
                        addToBottom(ResponseConverter.convert(status));
                        count++;
                    }
                    applyForce();
                    Notificator.info(count + "件読み込みました");
                }
            }.setCallBack(callback).callAsync();
        }
    }

    @Override
    public boolean checkStatus(IStatusModel status)
    {
        return status instanceof TweetModel;
    }

}
