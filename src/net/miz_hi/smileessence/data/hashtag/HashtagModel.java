package net.miz_hi.smileessence.data.hashtag;

import android.content.Context;
import android.util.Log;
import com.j256.ormlite.dao.Dao;
import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.data.DBHelper;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HashtagModel
{

    private Context context;
    private static HashtagModel instance = new HashtagModel(Client.getMainActivity());

    private HashtagModel(Context context)
    {
        this.context = context;
    }

    public static HashtagModel instance()
    {
        return instance;
    }

    public void save(Hashtag hashtag)
    {
        DBHelper helper = new DBHelper(context);
        try
        {
            Dao<Hashtag, Integer> dao = helper.getDao(Hashtag.class);
            hashtag = dao.createIfNotExists(hashtag);
            hashtag.setLatestDate(new Date());
            dao.update(hashtag);
        }
        catch(Exception e)
        {
            Log.e(HashtagModel.class.getSimpleName(), "error on save");
        }
        finally
        {
            helper.close();
        }
    }

    public void delete(int id)
    {
        DBHelper helper = new DBHelper(context);
        try
        {
            Dao<Hashtag, Integer> dao = helper.getDao(Hashtag.class);
            dao.deleteById(id);
        }
        catch(Exception e)
        {
            Log.e(HashtagModel.class.getSimpleName(), "error on delete");
        }
        finally
        {
            helper.close();
        }
    }

    public void deleteAll()
    {
        DBHelper helper = new DBHelper(context);
        try
        {
            for(Hashtag hashtag : findAll())
            {
                Dao<Hashtag, Integer> dao = helper.getDao(Hashtag.class);
                dao.delete(hashtag);
            }
        }
        catch(Exception e)
        {
            Log.e(HashtagModel.class.getSimpleName(), "error on delete");
        }
        finally
        {
            helper.close();
        }
    }

    public List<Hashtag> findAll()
    {
        DBHelper helper = new DBHelper(context);
        try
        {
            Dao<Hashtag, Integer> dao = helper.getDao(Hashtag.class);
            return dao.queryBuilder().orderBy("latestDate", false).query();
        }
        catch(Exception e)
        {
            Log.e(HashtagModel.class.getSimpleName(), "error on findAll");
            return Collections.emptyList();
        }
        finally
        {
            helper.close();
        }
    }

    public List<Hashtag> find(int count)
    {
        DBHelper helper = new DBHelper(context);
        try
        {
            Dao<Hashtag, Integer> dao = helper.getDao(Hashtag.class);
            return dao.queryBuilder().orderBy("latestDate", false).limit(count).query();
        }
        catch(Exception e)
        {
            Log.e(HashtagModel.class.getSimpleName(), "error on find");
            return Collections.emptyList();
        }
        finally
        {
            helper.close();
        }
    }
}
