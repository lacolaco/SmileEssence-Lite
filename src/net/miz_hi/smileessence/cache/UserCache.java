package net.miz_hi.smileessence.cache;

import net.miz_hi.smileessence.model.status.user.UserModel;
import twitter4j.User;

import java.util.concurrent.ConcurrentHashMap;

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

    public static UserModel remove(long id)
    {
        return usersMap.remove(id);
    }

    public static void clearCache()
    {
        usersMap.clear();
    }

}
