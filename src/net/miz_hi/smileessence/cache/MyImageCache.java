package net.miz_hi.smileessence.cache;

import android.graphics.Bitmap;
import android.util.LruCache;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.util.UiHandler;

public class MyImageCache implements ImageLoader.ImageCache
{

    private static MyImageCache instance;
    private LruCache<String, Bitmap> lruCache;
    private ImageLoader imageLoader;

    static
    {
        instance = new MyImageCache();
    }

    private MyImageCache()
    {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;       // 最大メモリに依存
        lruCache = new LruCache<String, Bitmap>(cacheSize);
        imageLoader = new ImageLoader(Client.getRequestQueue(), this);
    }

    public static MyImageCache getInstance()
    {
        return instance;
    }

    public ImageLoader getImageLoader()
    {
        return imageLoader;
    }

    public static void setImageToView(String imageUrl, NetworkImageView view)
    {
        view.setImageUrl(imageUrl, instance.imageLoader);
    }

    public static void preCache(final String imageUrl)
    {
        new UiHandler()
        {
            @Override
            public void run()
            {
                instance.imageLoader.get(imageUrl, new ImageLoader.ImageListener()
                {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate)
                    {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }
                });
            }
        }.post();
    }

    public static void clearCache()
    {
        instance.lruCache.evictAll();
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
