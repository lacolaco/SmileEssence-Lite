package net.miz_hi.smileessence.cache;

import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.model.status.user.UserModel;
import net.miz_hi.smileessence.task.impl.GetUserTask;
import twitter4j.User;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class UserCache
{

    private static ConcurrentHashMap<Long, UserModel> usersMap = new ConcurrentHashMap<Long, UserModel>();

    public static UserModel put(User user)
    {
        UserModel model;
        if (usersMap.containsKey(user.getId()))
        {
            model = usersMap.get(user.getId());
            model.updateData(user);
        }
        else
        {
            model = new UserModel(user);
            usersMap.put(user.getId(), model);
        }
        return model;
    }

    public static UserModel get(long id)
    {
        return usersMap.get(id);
    }

    public static UserModel getByScreenName(String screenName)
    {
        for (UserModel user : usersMap.values())
        {
            if (user.screenName.equals(screenName))
            {
                return user;
            }
        }
        return null;
    }

    public static UserModel getMyself()
    {
        UserModel model = get(Client.getMainAccount().getUserId());
        if (model != null)
        {
            return model;
        }
        else
        {
            Future f = new GetUserTask(Client.getMainAccount().getUserId()).callAsync();
            try
            {
                return (UserModel) f.get();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static UserModel remove(long id)
    {
        return usersMap.remove(id);
    }

    public static void clearCache()
    {
        usersMap.clear();
    }

}
