package net.miz_hi.smileessence.listener;

import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import net.miz_hi.smileessence.core.MyExecutor;
import net.miz_hi.smileessence.model.statuslist.timeline.Timeline;
import net.miz_hi.smileessence.notification.Notificator;
import net.miz_hi.smileessence.util.UiHandler;

public class TimelineRefreshListener implements PullToRefreshBase.OnRefreshListener2<ListView>
{

    Timeline timeline;

    public TimelineRefreshListener(Timeline timeline)
    {
        this.timeline = timeline;
    }

    @Override
    public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView)
    {
        final int old = timeline.getCount();
        MyExecutor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    timeline.loadNewer(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            final int current = timeline.getCount();
                            new UiHandler()
                            {
                                @Override
                                public void run()
                                {
                                    refreshView.onRefreshComplete();
                                    refreshView.getRefreshableView().setSelection(old == 0 ? 0 : current - old + 1);
                                    Notificator.info((current - old) + "件読み込みました");
                                }
                            }.post();
                        }
                    }).get();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Notificator.alert("更新に失敗しました");
                    new UiHandler()
                    {
                        @Override
                        public void run()
                        {
                            refreshView.onRefreshComplete();
                        }
                    }.post();
                }
            }
        });
    }

    @Override
    public void onPullUpToRefresh(final PullToRefreshBase<ListView> refreshView)
    {
        final int old = timeline.getCount();
        MyExecutor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    timeline.loadOlder(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            final int current = timeline.getCount();
                            new UiHandler()
                            {
                                @Override
                                public void run()
                                {
                                    refreshView.onRefreshComplete();
                                    refreshView.getRefreshableView().smoothScrollToPositionFromTop(old, 0, 800);
                                    Notificator.info((current - old) + "件読み込みました");
                                }
                            }.post();
                        }
                    }).get();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Notificator.alert("更新に失敗しました");
                    new UiHandler()
                    {
                        @Override
                        public void run()
                        {
                            refreshView.onRefreshComplete();
                        }
                    }.post();
                }
            }
        });
    }
}
