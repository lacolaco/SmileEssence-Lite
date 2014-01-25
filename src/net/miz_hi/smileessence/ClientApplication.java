package net.miz_hi.smileessence;

import android.app.Application;

public class ClientApplication extends Application
{

    private Client client;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Client.initialize(this);
    }

    public void setClient(Client client)
    {
        this.client = client;
    }
}
