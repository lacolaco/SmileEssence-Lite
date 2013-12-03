package net.miz_hi.smileessence.view.fragment.impl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import net.miz_hi.smileessence.R;
import net.miz_hi.smileessence.listener.TimelineRefreshListener;
import net.miz_hi.smileessence.listener.TimelineScrollListener;
import net.miz_hi.smileessence.statuslist.StatusListAdapter;
import net.miz_hi.smileessence.statuslist.StatusListManager;
import net.miz_hi.smileessence.view.fragment.IRemovable;
import net.miz_hi.smileessence.view.fragment.NamedFragment;

public class ListFragment extends NamedFragment implements IRemovable
{

    String name;
    long id;

    public static ListFragment newInstance(long id, String fullName)
    {
        ListFragment fragment = new ListFragment();
        fragment.name = fullName;
        fragment.id = id;
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
        listView.setOnRefreshListener(new TimelineRefreshListener(StatusListManager.getListTimeline(id)));
        return page;
    }


    @Override
    public void onRemoved()
    {
        StatusListManager.removeListTimeline(id);
    }
}
