package net.miz_hi.smileessence.status;

import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.cache.TweetCache;
import net.miz_hi.smileessence.model.status.tweet.TweetModel;
import net.miz_hi.smileessence.task.impl.ShowTweetTask;
import net.miz_hi.smileessence.twitter.ResponseConverter;
import twitter4j.Status;
import twitter4j.UserMentionEntity;

import java.util.concurrent.Future;

public class TweetUtils
{

    public static boolean isReply(Status st)
    {
        if (st == null)
        {
            return false;
        }
        for (UserMentionEntity ume : st.getUserMentionEntities())
        {
            if (ume.getScreenName().equals(Client.getMainAccount().getScreenName()))
            {
                return true;
            }
        }
        return false;
    }

    public static TweetModel getOrCreateStatusModel(long id)
    {
        TweetModel statusModel = TweetCache.get(id);
        if (statusModel == null)
        {
            Future<Status> f = new ShowTweetTask(id).callAsync();
            Status status;
            try
            {
                status = f.get();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                status = null;
            }
            if (status == null)
            {
                return null;
            }
            statusModel = ResponseConverter.convert(status);
        }
        return statusModel;
    }

}
