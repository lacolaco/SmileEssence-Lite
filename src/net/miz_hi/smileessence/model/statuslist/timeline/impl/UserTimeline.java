package net.miz_hi.smileessence.model.statuslist.timeline.impl;

import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.model.status.IStatusModel;
import net.miz_hi.smileessence.model.status.tweet.TweetModel;
import net.miz_hi.smileessence.model.statuslist.timeline.Timeline;
import net.miz_hi.smileessence.statuslist.StatusListAdapter;
import net.miz_hi.smileessence.statuslist.StatusListManager;
import net.miz_hi.smileessence.task.impl.GetUserTimelineTask;
import twitter4j.Paging;

import java.util.Collections;
import java.util.List;


public class UserTimeline extends Timeline
{

    private long userId;

    public UserTimeline(long userId)
    {
        this.userId = userId;
    }

    @Override
    public void loadNewer()
    {
        final StatusListAdapter adapter = StatusListManager.getAdapter(this);
        if (getStatusList().length > 0)
        {
            long maxId = ((TweetModel) getStatus(0)).statusId;
            new GetUserTimelineTask(Client.getMainAccount(), userId, new Paging(1, 20, maxId))
            {
                @Override
                public void onPostExecute(List<TweetModel> result)
                {
                    Collections.reverse(result);
                    for (TweetModel status : result)
                    {
                        addToTop(status);
                    }
                    adapter.forceNotifyAdapter();
                }
            }.callAsync();
        }
        else
        {
            new GetUserTimelineTask(Client.getMainAccount(), userId, new Paging(1, 20))
            {
                @Override
                public void onPostExecute(List<TweetModel> result)
                {
                    Collections.reverse(result);
                    for (TweetModel status : result)
                    {
                        addToTop(status);
                    }
                    adapter.forceNotifyAdapter();
                }
            }.callAsync();
        }
    }

    @Override
    public void loadOlder()
    {
        //        StatusListAdapter adapter = StatusListManager.getAdapter(this);
        //        List<TweetModel> list;
        //        if (getStatusList().length > 0)
        //        {
        //            long minId = ((TweetModel) getStatus(getStatusList().length)).statusId;
        //            list = new GetUserTimelineTask(Client.getMainAccount(), userId, new Paging(1, 20, 0, minId)).call();
        //        }
        //        else
        //        {
        //            list = new GetUserTimelineTask(Client.getMainAccount(), userId, new Paging(1, 20)).call();
        //        }
        //
        //        for (TweetModel status : list)
        //        {
        //            addToBottom(status);
        //        }
        //        adapter.forceNotifyAdapter();
    }

    @Override
    public boolean checkStatus(IStatusModel status)
    {
        return status instanceof TweetModel;
    }

}
