package net.miz_hi.smileessence.model.statuslist.timeline.impl;

import net.miz_hi.smileessence.model.status.IStatusModel;
import net.miz_hi.smileessence.model.status.tweet.TweetModel;
import net.miz_hi.smileessence.model.statuslist.timeline.Timeline;


public class SearchTimeline extends Timeline
{

    private String query;

    public SearchTimeline(String query)
    {
        this.query = query;
    }

    @Override
    public void loadNewer()
    {
        //TODO REST API
    }

    @Override
    public void loadOlder()
    {
        //TODO REST API
    }

    @Override
    public boolean checkStatus(IStatusModel status)
    {
        return status instanceof TweetModel;
    }

}
