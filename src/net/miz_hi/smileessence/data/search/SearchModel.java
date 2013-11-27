package net.miz_hi.smileessence.data.search;

import android.content.Context;
import android.util.Log;
import com.j256.ormlite.dao.Dao;
import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.data.DBHelper;

import java.util.Collections;
import java.util.List;

public class SearchModel
{

    private Context context;
    private static SearchModel instance = new SearchModel(Client.getApplication());

    private SearchModel(Context context)
    {
        this.context = context;
    }

    public static SearchModel instance()
    {
        return instance;
    }

    public void save(Search search)
    {
        DBHelper helper = new DBHelper(context);
        try
        {
            Dao<Search, Integer> dao = helper.getDao(Search.class);
            dao.createOrUpdate(search);
        }
        catch (Exception e)
        {
            Log.e(SearchModel.class.getSimpleName(), "error on save");
        }
        finally
        {
            helper.close();
        }
    }

    public void delete(int searchId)
    {
        DBHelper helper = new DBHelper(context);
        try
        {
            Dao<Search, Integer> dao = helper.getDao(Search.class);
            dao.deleteById(searchId);
        }
        catch (Exception e)
        {
            Log.e(SearchModel.class.getSimpleName(), "error on delete");
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
            for (Search search : findAll())
            {
                Dao<Search, Integer> dao = helper.getDao(Search.class);
                dao.delete(search);
            }
        }
        catch (Exception e)
        {
            Log.e(SearchModel.class.getSimpleName(), "error on delete");
        }
        finally
        {
            helper.close();
        }
    }

    public List<Search> findAll()
    {
        DBHelper helper = new DBHelper(context);
        try
        {
            Dao<Search, Integer> dao = helper.getDao(Search.class);
            return dao.queryForAll();
        }
        catch (Exception e)
        {
            Log.e(SearchModel.class.getSimpleName(), "error on findAll");
            return Collections.emptyList();
        }
        finally
        {
            helper.close();
        }
    }
}
