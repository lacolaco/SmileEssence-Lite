package net.miz_hi.smileessence.listener;

import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import net.miz_hi.smileessence.core.MyExecutor;
import net.miz_hi.smileessence.model.statuslist.timeline.Timeline;
import net.miz_hi.smileessence.util.UiHandler;

public class TimelineRefreshListener implements PullToRefreshBase.OnRefreshListener2<ListView>
{

    Timeline timeline;

    public TimelineRefreshListener(Timeline timeline)
    {
        this.timeline = timeline;
    }

    @Override
    public void onPullDownToRefresh(final PullToRefreshBase refreshView)
    {
        MyExecutor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    timeline.loadNewer().get();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                new UiHandler()
                {
                    @Override
                    public void run()
                    {
                        refreshView.onRefreshComplete();
                    }
                }.post();
            }
        });
    }

    @Override
    public void onPullUpToRefresh(final PullToRefreshBase refreshView)
    {
        MyExecutor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    timeline.loadOlder().get();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                new UiHandler()
                {
                    @Override
                    public void run()
                    {
                        refreshView.onRefreshComplete();
                    }
                }.post();
            }
        });
    }
}
