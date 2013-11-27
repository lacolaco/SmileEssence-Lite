package net.miz_hi.smileessence.task.impl;

import net.miz_hi.smileessence.auth.Account;
import net.miz_hi.smileessence.task.Task;
import net.miz_hi.smileessence.twitter.API;
import twitter4j.Query;
import twitter4j.QueryResult;

public class SearchTask extends Task<QueryResult>
{

    private final Account account;
    private final Query query;

    public SearchTask(Account account, Query query)
    {
        this.account = account;
        this.query = query;
    }

    @Override
    public void onPreExecute()
    {
    }

    @Override
    public void onPostExecute(QueryResult result)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public QueryResult call() throws Exception
    {
        return API.search(account, query);
    }
}
