package net.miz_hi.smileessence.listener;

import android.app.ProgressDialog;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import net.miz_hi.smileessence.core.MyExecutor;
import net.miz_hi.smileessence.model.statuslist.StatusList;
import net.miz_hi.smileessence.model.statuslist.timeline.Timeline;
import net.miz_hi.smileessence.notification.Notificator;
import net.miz_hi.smileessence.util.CustomListAdapter;
import net.miz_hi.smileessence.util.UiHandler;

public class TimelineScrollListener implements OnScrollListener
{

    private CustomListAdapter<?> adapter;
    private StatusList statusList;

    public TimelineScrollListener(CustomListAdapter<?> adapter, StatusList statusList)
    {
        this.adapter = adapter;
        this.statusList = statusList;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
        adapter.setCanNotifyOnChange(false);

        if (view.getFirstVisiblePosition() == 0 && view.getChildAt(0) != null && view.getChildAt(0).getTop() == 0)
        {
            if (scrollState == SCROLL_STATE_IDLE)
            {
                adapter.setCanNotifyOnChange(true);
                int before = adapter.getCount();
                adapter.notifyDataSetChanged();
                int after = adapter.getCount();
                int addCount = after - before;
                ((ListView) view).setSelectionFromTop(addCount, 0);
                if (addCount > 0)
                {
                    Notificator.info(addCount + "件の新着があります");
                }
            }
        }
        else if (view.getLastVisiblePosition() == view.getCount() - 1 && view.getChildAt(view.getChildCount() - 1) != null && view.getBottom() == view.getChildAt(view.getChildCount() - 1).getBottom())
        {
            if (scrollState == SCROLL_STATE_IDLE)
            {
                if (statusList instanceof Timeline)
                {
                    final ProgressDialog pd = ProgressDialog.show(view.getContext(), "", "Now loading...");
                    MyExecutor.execute(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                ((Timeline) statusList).loadOlder().get();
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
                                    statusList.applyForce();
                                    pd.dismiss();
                                }
                            }.post();
                        }
                    });
                }
            }
        }
    }

}
