package net.miz_hi.smileessence.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.*;

import java.io.File;


public class VolleyUtil
{

    /**
     * Default on-disk cache directory.
     */
    private static final String DEFAULT_CACHE_DIR = "volley";

    public static RequestQueue createRequestQueue(Context context, HttpStack stack, int maxCacheSizeInBytes)
    {
        File cacheDir = new File(context.getCacheDir(), DEFAULT_CACHE_DIR);

        String userAgent = "volley/0";
        try
        {
            String packageName = context.getPackageName();

            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        }
        catch (PackageManager.NameNotFoundException e)
        {
        }

        if (stack == null)
        {
            if (Build.VERSION.SDK_INT >= 9)
            {
                stack = new HurlStack();
            }
            else
            {
                // Prior to Gingerbread, HttpUrlConnection was unreliable.
                // See: http://android-developers.blogspot.com/2011/09/androids-http-clients.html
                stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
            }
        }

        Network network = new BasicNetwork(stack);

        RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheDir, maxCacheSizeInBytes), network);
        queue.start();

        return queue;
    }
}
