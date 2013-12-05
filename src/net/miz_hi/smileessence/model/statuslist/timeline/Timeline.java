package net.miz_hi.smileessence.model.statuslist.timeline;

import net.miz_hi.smileessence.model.statuslist.StatusList;

import java.util.concurrent.Future;

public abstract class Timeline extends StatusList
{

    public Future loadNewer()
    {
        return loadNewer(null);
    }

    public Future loadOlder()
    {
        return loadOlder(null);
    }

    public abstract Future loadNewer(Runnable callback);

    public abstract Future loadOlder(Runnable callback);

}
