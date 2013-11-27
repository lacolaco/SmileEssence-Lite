package net.miz_hi.smileessence.model.statuslist.timeline.impl;

import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.model.status.IStatusModel;
import net.miz_hi.smileessence.model.status.tweet.TweetModel;
import net.miz_hi.smileessence.model.statuslist.timeline.Timeline;
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

    private static final String LANG = "ja";
    private static final int COUNT = 20;

    private String queryString;
    private Query query;

    public SearchTimeline(String queryString)
    {
        this.queryString = queryString;
        initQuery();
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
    public Future loadNewer()
    {
        if (getStatusList().length > 0)
        {
            long maxId = ((TweetModel) getStatus(0)).statusId;
            query.setSinceId(maxId);
            return new SearchTask(Client.getMainAccount(), query)
            {
                @Override
                public void onPostExecute(QueryResult result)
                {
                    List<Status> statuses = result.getTweets();
                    Collections.reverse(statuses);
                    for (Status status : statuses)
                    {
                        addToTop(ResponseConverter.convert(status));
                    }
                    applyForce();
                }
            }.callAsync();
        }
        else
        {
            return new SearchTask(Client.getMainAccount(), query)
            {
                @Override
                public void onPostExecute(QueryResult result)
                {
                    List<Status> statuses = result.getTweets();
                    Collections.reverse(statuses);
                    for (Status status : statuses)
                    {
                        addToTop(ResponseConverter.convert(status));
                    }
                    applyForce();
                }
            }.callAsync();
        }
    }

    @Override
    public Future loadOlder()
    {
        if (getStatusList().length > 0)
        {
            long minId = ((TweetModel) getStatus(getStatusList().length - 1)).statusId;
            query.setMaxId(minId);
            return new SearchTask(Client.getMainAccount(), query)
            {
                @Override
                public void onPostExecute(QueryResult result)
                {
                    List<Status> statuses = result.getTweets();
                    for (Status status : statuses)
                    {
                        addToBottom(ResponseConverter.convert(status));
                    }
                    applyForce();
                }
            }.callAsync();
        }
        else
        {
            return new SearchTask(Client.getMainAccount(), query)
            {
                @Override
                public void onPostExecute(QueryResult result)
                {
                    List<Status> statuses = result.getTweets();
                    for (Status status : statuses)
                    {
                        addToBottom(ResponseConverter.convert(status));
                    }
                    applyForce();
                }
            }.callAsync();
        }
    }

    @Override
    public boolean checkStatus(IStatusModel status)
    {
        return status instanceof TweetModel;
    }

}
