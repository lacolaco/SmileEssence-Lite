package net.miz_hi.smileessence.view.fragment.impl;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.R;
import net.miz_hi.smileessence.core.MyExecutor;
import net.miz_hi.smileessence.listener.TimelineScrollListener;
import net.miz_hi.smileessence.model.statuslist.timeline.Timeline;
import net.miz_hi.smileessence.preference.EnumPreferenceKey;
import net.miz_hi.smileessence.statuslist.StatusListAdapter;
import net.miz_hi.smileessence.statuslist.StatusListManager;
import net.miz_hi.smileessence.util.UiHandler;
import net.miz_hi.smileessence.view.fragment.IRemovable;
import net.miz_hi.smileessence.view.fragment.NamedFragment;

import java.util.concurrent.ExecutionException;

public class ListFragment extends NamedFragment implements IRemovable
{

    String name;
    long id;
    boolean inited;

    public static ListFragment newInstance(long id, String fullName)
    {
        ListFragment fragment = new ListFragment();
        fragment.name = fullName;
        fragment.id = id;
        fragment.inited = false;
        return fragment;
    }


    @Override
    public String getTitle()
    {
        return name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View page = inflater.inflate(R.layout.listpage_refresh_layout, container, false);
        PullToRefreshListView listView = (PullToRefreshListView) page.findViewById(R.id.listpage_listview);
        StatusListAdapter adapter = StatusListManager.getAdapter(StatusListManager.getListTimeline(id));
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new TimelineScrollListener(adapter));
        listView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener<ListView>()
        {
            @Override
            public void onRefresh(final PullToRefreshBase refreshView)
            {
                MyExecutor.execute(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        refresh();
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
        listView.onRefreshComplete();
        return page;
    }

    @Override
    public void onSelected()
    {
        if (Client.<Boolean>getPreferenceValue(EnumPreferenceKey.LIST_LOAD) && isNotInited())
        {
            final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "Now loading...");
            MyExecutor.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    refresh();
                    new UiHandler()
                    {
                        @Override
                        public void run()
                        {
                            pd.dismiss();
                        }
                    }.post();
                }
            });
        }
    }

    public void refresh()
    {
        inited = true;
        Timeline timeline = StatusListManager.getListTimeline(id);
        try
        {
            timeline.loadNewer().get();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onRemoved()
    {
        StatusListManager.removeListTimeline(id);
    }

    public boolean isNotInited()
    {
        return !inited;
    }

}
