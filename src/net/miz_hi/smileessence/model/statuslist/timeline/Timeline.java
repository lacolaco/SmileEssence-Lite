package net.miz_hi.smileessence.model.statuslist.timeline;

import net.miz_hi.smileessence.model.statuslist.StatusList;

import java.util.concurrent.Future;

public abstract class Timeline extends StatusList
{

    public abstract Future loadNewer();

    public abstract Future loadOlder();

}
