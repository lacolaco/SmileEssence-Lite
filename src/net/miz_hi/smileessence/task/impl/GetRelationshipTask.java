package net.miz_hi.smileessence.task.impl;

import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.task.Task;
import net.miz_hi.smileessence.twitter.API;
import twitter4j.Relationship;

import java.util.HashMap;


public class GetRelationshipTask extends Task<Relationship>
{

    static HashMap<Long, GetRelationshipTask> idsInTask = new HashMap<Long, GetRelationshipTask>();
    long userId;

    public GetRelationshipTask(long userId)
    {
        this.userId = userId;
    }

    @Override
    public Relationship call()
    {
        Relationship rel = null;
        try
        {
            if (idsInTask.containsKey(userId))
            {
                return idsInTask.get(userId).call();
            }
            else
            {
                idsInTask.put(userId, this);
                rel = API.getRelationship(Client.getMainAccount(), userId);
                idsInTask.remove(userId);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return rel;
    }

    @Override
    public void onPreExecute()
    {
    }

    @Override
    public void onPostExecute(Relationship result)
    {
    }
}
