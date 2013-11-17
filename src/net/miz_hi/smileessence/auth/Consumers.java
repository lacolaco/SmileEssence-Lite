package net.miz_hi.smileessence.auth;

import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.R;

public class Consumers
{

    public static Consumer smileEssenceLite = new Consumer(Client.getString(R.string.consumer_key), Client.getString(R.string.consumer_secret));


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
