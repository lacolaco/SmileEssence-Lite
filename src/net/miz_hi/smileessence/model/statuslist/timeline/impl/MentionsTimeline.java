package net.miz_hi.smileessence.model.statuslist.timeline.impl;

import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.model.status.IStatusModel;
import net.miz_hi.smileessence.model.status.tweet.TweetModel;
import net.miz_hi.smileessence.model.statuslist.timeline.Timeline;
import net.miz_hi.smileessence.notification.Notificator;
import net.miz_hi.smileessence.task.impl.GetMentionsTask;
import twitter4j.Paging;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;


public class MentionsTimeline extends Timeline
{

    @Override
    public Future loadNewer(Runnable callback)
    {
        if (getStatusList().length > 0)
        {
            long maxId = ((TweetModel) getStatus(0)).statusId;
            return new GetMentionsTask(Client.getMainAccount(), new Paging(1, 20, maxId))
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
                    Notificator.info(result.size() + "件読み込みました");
                }
            }.setCallBack(callback).callAsync();
        }
        else
        {
            return new GetMentionsTask(Client.getMainAccount(), new Paging(1, 20))
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
                    Notificator.info(result.size() + "件読み込みました");
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
            Paging page = new Paging(1, 20);
            page.setMaxId(minId);
            return new GetMentionsTask(Client.getMainAccount(), page)
            {
                @Override
                public void onPostExecute(List<TweetModel> result)
                {
                    for (TweetModel status : result)
                    {
                        addToBottom(status);
                    }
                    applyForce();
                    Notificator.info(result.size() + "件読み込みました");
                }
            }.setCallBack(callback).callAsync();
        }
        else
        {
            return new GetMentionsTask(Client.getMainAccount(), new Paging(1, 20))
            {
                @Override
                public void onPostExecute(List<TweetModel> result)
                {
                    for (TweetModel status : result)
                    {
                        addToBottom(status);
                    }
                    applyForce();
                    Notificator.info(result.size() + "件読み込みました");
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
