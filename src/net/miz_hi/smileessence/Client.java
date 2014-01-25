package net.miz_hi.smileessence;

import android.app.Application;
import android.preference.PreferenceManager;
import com.android.volley.RequestQueue;
import net.miz_hi.smileessence.auth.Account;
import net.miz_hi.smileessence.auth.AuthenticationDB;
import net.miz_hi.smileessence.cache.VolleyUtil;
import net.miz_hi.smileessence.core.MyExecutor;
import net.miz_hi.smileessence.core.Settings;
import net.miz_hi.smileessence.data.DBHelper;
import net.miz_hi.smileessence.permission.IPermission;
import net.miz_hi.smileessence.permission.PermissonChecker;
import net.miz_hi.smileessence.preference.EnumPreferenceKey;
import net.miz_hi.smileessence.preference.PreferenceHelper;
import net.miz_hi.smileessence.theme.impl.LightColorTheme;

public class Client
{

    private static Client instance;

    private Application app;
    private Account mainAccount;
    private IPermission permission;
    private PreferenceHelper prefHelper;
    private RequestQueue requestQueue;
    private Settings settings;

    private Client()
    {
    }

    public static PreferenceHelper getPreferenceHelper()
    {
        return instance.prefHelper;
    }

    public static void putPreferenceValue(EnumPreferenceKey key, Object value)
    {
        instance.prefHelper.putPreferenceValue(key, value);
    }

    public static <T> T getPreferenceValue(EnumPreferenceKey key)
    {
        return instance.prefHelper.getPreferenceValue(key);
    }

    public static boolean hasAuthorizedAccount()
    {
        return AuthenticationDB.instance().findAll() != null && !AuthenticationDB.instance().findAll().isEmpty();
    }

    public static Application getApplication()
    {
        return instance.app;
    }

    public static Account getMainAccount()
    {
        return instance.mainAccount;
    }

    public static void setMainAccount(Account account)
    {
        if(account != null)
        {
            putPreferenceValue(EnumPreferenceKey.LAST_USED_USER_ID, account.getUserId());
        }
        else
        {
            putPreferenceValue(EnumPreferenceKey.LAST_USED_USER_ID, -1L);
        }
        instance.mainAccount = account;

        setPermission(PermissonChecker.checkPermission(account));
    }

    public static IPermission getPermission()
    {
        return instance.permission;
    }

    public static void setPermission(IPermission permission)
    {
        instance.permission = permission;
    }

    public static RequestQueue getRequestQueue()
    {
        return instance.requestQueue;
    }

    public static Settings getSettings()
    {
        return instance.settings;
    }

    public static Settings loadSettings()
    {
        Settings s = new Settings();
        int tSize = getPreferenceValue(EnumPreferenceKey.TEXT_SIZE);
        if(tSize < 0)
        {
            putPreferenceValue(EnumPreferenceKey.TEXT_SIZE, 10);
        }
        s.setTextSize(Client.<Integer>getPreferenceValue(EnumPreferenceKey.TEXT_SIZE));
        boolean isDark = Client.<Boolean>getPreferenceValue(EnumPreferenceKey.THEME_IS_DEFAULT);
        if(isDark)
        {
            s.setTheme(new LightColorTheme());
        }
        else
        {
            s.setTheme(new LightColorTheme());
        }
        return s;
    }

    public static void initialize(ClientApplication app)
    {
        instance = new Client();
        instance.prefHelper = new PreferenceHelper(PreferenceManager.getDefaultSharedPreferences(app));
        instance.app = app;
        instance.mainAccount = null;
        instance.requestQueue = VolleyUtil.createRequestQueue(app, null, 32 * 1024 * 1024); //32MBのディスクキャッシュ
        instance.settings = loadSettings();
        new DBHelper(app).initialize();
        MyExecutor.init();
        app.setClient(instance);
    }

}
