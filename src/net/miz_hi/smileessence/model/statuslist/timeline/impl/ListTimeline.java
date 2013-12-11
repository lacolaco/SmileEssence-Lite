package net.miz_hi.smileessence.model.statuslist.timeline.impl;

import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.model.status.IStatusModel;
import net.miz_hi.smileessence.model.status.tweet.TweetModel;
import net.miz_hi.smileessence.model.statuslist.timeline.Timeline;
import net.miz_hi.smileessence.preference.EnumPreferenceKey;
import net.miz_hi.smileessence.task.impl.GetListTimelineTask;
import twitter4j.Paging;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;


public class ListTimeline extends Timeline
{

    private long id;

    public ListTimeline(long id)
    {
        this.id = id;
    }

    @Override
    public Future loadNewer(Runnable callback)
    {
        if (getStatusList().length > 0)
        {
            long maxId = ((TweetModel) getStatus(0)).statusId;
            return new GetListTimelineTask(Client.getMainAccount(), id, new Paging(1, Client.<Integer>getPreferenceValue(EnumPreferenceKey.REQUEST_COUNT), maxId))
            {
                @Override
                public void onPostExecute(List<TweetModel> result)
                {
                    Collections.reverse(result);
                    for (TweetModel status : result)
                    {
                        addToTop(status);
                    }
                    applyForce();
                }
            }.setCallBack(callback).callAsync();
        }
        else
        {
            return new GetListTimelineTask(Client.getMainAccount(), id, new Paging(1, Client.<Integer>getPreferenceValue(EnumPreferenceKey.REQUEST_COUNT)))
            {
                @Override
                public void onPostExecute(List<TweetModel> result)
                {
                    Collections.reverse(result);
                    for (TweetModel status : result)
                    {
                        addToTop(status);
                    }
                    applyForce();
                }
            }.setCallBack(callback).callAsync();
        }
    }

    @Override
    public Future loadOlder(Runnable callback)
    {
        if (getStatusList().length > 0)
        {
            long minId = ((TweetModel) getStatus(getStatusList().length - 1)).statusId;
            Paging page = new Paging(1, Client.<Integer>getPreferenceValue(EnumPreferenceKey.REQUEST_COUNT));
            page.setMaxId(minId);
            return new GetListTimelineTask(Client.getMainAccount(), id, page)
            {
                @Override
                public void onPostExecute(List<TweetModel> result)
                {
                    for (TweetModel status : result)
                    {
                        addToBottom(status);
                    }
                    applyForce();
                }
            }.setCallBack(callback).callAsync();
        }
        else
        {
            return new GetListTimelineTask(Client.getMainAccount(), id, new Paging(1, Client.<Integer>getPreferenceValue(EnumPreferenceKey.REQUEST_COUNT)))
            {
                @Override
                public void onPostExecute(List<TweetModel> result)
                {
                    for (TweetModel status : result)
                    {
                        addToBottom(status);
                    }
                    applyForce();
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
