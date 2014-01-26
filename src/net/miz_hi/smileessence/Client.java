package net.miz_hi.smileessence;

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
import net.miz_hi.smileessence.theme.DarkColorTheme;
import net.miz_hi.smileessence.theme.impl.LightColorTheme;
import net.miz_hi.smileessence.util.LogHelper;
import net.miz_hi.smileessence.view.activity.MainActivity;

public class Client
{

    private static Client instance;

    private MainActivity app;
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

    public static MainActivity getMainActivity()
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
        boolean isDark = Client.<Boolean>getPreferenceValue(EnumPreferenceKey.THEME_IS_DARK);
        if(isDark)
        {
            s.setTheme(new DarkColorTheme());
            instance.app.setTheme(R.style.DarkTheme);
        }
        else
        {
            s.setTheme(new LightColorTheme());
            instance.app.setTheme(R.style.LightTheme);
        }
        LogHelper.d(isDark);
        return s;
    }

    public static void initialize(MainActivity activity)
    {
        instance = new Client();
        instance.prefHelper = new PreferenceHelper(PreferenceManager.getDefaultSharedPreferences(activity));
        instance.app = activity;
        instance.mainAccount = null;
        instance.requestQueue = VolleyUtil.createRequestQueue(activity, null, 32 * 1024 * 1024); //32MBのディスクキャッシュ
        instance.settings = loadSettings();
        new DBHelper(activity).initialize();
        MyExecutor.init();
    }

}
