package net.miz_hi.smileessence.model.statuslist.timeline.impl;

import net.miz_hi.smileessence.model.status.IStatusModel;
import net.miz_hi.smileessence.model.status.tweet.TweetModel;
import net.miz_hi.smileessence.model.statuslist.timeline.Timeline;

import java.util.concurrent.Future;


public class MentionsTimeline extends Timeline
{

    @Override
    public Future loadNewer()
    {
        //TODO REST API
        return null;
    }

    @Override
    public Future loadOlder()
    {
        //TODO REST API
        return null;
    }

    @Override
    public boolean checkStatus(IStatusModel status)
    {
        return status instanceof TweetModel;
    }

}
