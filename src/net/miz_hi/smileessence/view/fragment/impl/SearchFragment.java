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

public class SearchFragment extends NamedFragment implements IRemovable
{

    int searchId;
    String query;

    @Override
    public String getTitle()
    {
        return query;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View page = inflater.inflate(R.layout.listpage_refresh_layout, container, false);
        PullToRefreshListView listView = (PullToRefreshListView) page.findViewById(R.id.listpage_listview);
        StatusListAdapter adapter = StatusListManager.getAdapter(StatusListManager.getSearchTimeline(searchId));
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new TimelineScrollListener(adapter));
        listView.setOnRefreshListener(new TimelineRefreshListener(StatusListManager.getSearchTimeline(searchId)));
        return page;
    }

    @Override
    public void onRemoved()
    {
        StatusListManager.removeSearchTimeline(searchId);
    }

    public static SearchFragment getInstance(int id, String query)
    {
        SearchFragment fragment = new SearchFragment();
        fragment.searchId = id;
        fragment.query = query;
        return fragment;
    }
}
