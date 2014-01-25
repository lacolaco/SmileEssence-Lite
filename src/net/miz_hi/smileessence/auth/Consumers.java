package net.miz_hi.smileessence.auth;

import android.app.Application;
import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.R;

public class Consumers
{

    public static Consumer smileEssenceLite;

    static
    {
        Application app = Client.getApplication();
        smileEssenceLite = new Consumer(app.getString(R.string.consumer_key), app.getString(R.string.consumer_secret));
    }

    public static Consumer getDefault()
    {
        return smileEssenceLite;
    }

    public static class Consumer
    {

        public String key;
        public String secret;

        public Consumer(String key, String secret)
        {
            this.key = key;
            this.secret = secret;
        }
    }
}
