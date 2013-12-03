package net.miz_hi.smileessence.view.fragment.impl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import net.miz_hi.smileessence.R;
import net.miz_hi.smileessence.core.MyExecutor;
import net.miz_hi.smileessence.listener.TimelineScrollListener;
import net.miz_hi.smileessence.statuslist.StatusListManager;
import net.miz_hi.smileessence.util.CustomListAdapter;
import net.miz_hi.smileessence.util.UiHandler;
import net.miz_hi.smileessence.view.fragment.NamedFragment;

public class HomeFragment extends NamedFragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        LinearLayout page = (LinearLayout) inflater.inflate(R.layout.listpage_refresh_layout, container, false);
        PullToRefreshListView listView = (PullToRefreshListView) page.findViewById(R.id.listpage_listview);
        ProgressBar progress = new ProgressBar(getActivity());
        progress.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        progress.setVisibility(View.GONE);
        ((ViewGroup) listView.getParent()).addView(progress);
        listView.setEmptyView(progress);
        CustomListAdapter<?> adapter = StatusListManager.getAdapter(StatusListManager.getHomeTimeline());
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new TimelineScrollListener(adapter, StatusListManager.getHomeTimeline()));
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>()
        {
            @Override
            public void onRefresh(final PullToRefreshBase<ListView> refreshView)
            {
                MyExecutor.execute(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            StatusListManager.getHomeTimeline().loadNewer().get();
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
        });
        return page;
    }

    @Override
    public String getTitle()
    {
        return "Home";
    }

}
