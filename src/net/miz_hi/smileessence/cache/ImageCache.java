package net.miz_hi.smileessence.cache;

import android.graphics.Bitmap;
import android.util.LruCache;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.util.UiHandler;

public class ImageCache implements ImageLoader.ImageCache
{

    private static ImageCache instance;
    private LruCache<String, Bitmap> lruCache;
    private ImageLoader imageLoader;

    static
    {
        instance = new ImageCache();
    }

    private ImageCache()
    {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 4;       // 最大メモリに依存
        lruCache = new LruCache<String, Bitmap>(cacheSize);
        imageLoader = new ImageLoader(Client.getRequestQueue(), this);
    }

    public static ImageCache getInstance()
    {
        if(instance == null)
        {
            instance = new ImageCache();
        }
        return instance;
    }

    public static void setImageToView(String imageUrl, NetworkImageView view)
    {
        view.setImageUrl(imageUrl, getInstance().imageLoader);
    }

    public static void preCache(final String imageUrl)
    {
        new UiHandler()
        {
            @Override
            public void run()
            {
                getInstance().imageLoader.get(imageUrl, new ImageLoader.ImageListener()
                {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate)
                    {
                    }

                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                    }
                });
            }
        }.post();
    }

    public static void clearCache()
    {
        getInstance().lruCache.evictAll();
    }

    @Override
    public Bitmap getBitmap(String url)
    {
        return lruCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap)
    {
        lruCache.put(url, bitmap);
    }
}
