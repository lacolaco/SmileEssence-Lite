package net.miz_hi.smileessence.view.fragment.impl;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import net.miz_hi.smileessence.R;
import net.miz_hi.smileessence.listener.TimelineRefreshListener;
import net.miz_hi.smileessence.listener.TimelineScrollListener;
import net.miz_hi.smileessence.model.status.user.UserModel;
import net.miz_hi.smileessence.statuslist.StatusListAdapter;
import net.miz_hi.smileessence.statuslist.StatusListManager;
import net.miz_hi.smileessence.view.fragment.IRemovable;
import net.miz_hi.smileessence.view.fragment.ISingleton;
import net.miz_hi.smileessence.view.fragment.NamedFragment;

@SuppressLint("ValidFragment")
public class UserTimelineFragment extends NamedFragment implements IRemovable, ISingleton
{

    UserModel user;

    private UserTimelineFragment()
    {
    }

    public static UserTimelineFragment newInstance(UserModel user)
    {
        UserTimelineFragment fragment = new UserTimelineFragment();
        fragment.user = user;
        return fragment;
    }

    @Override
    public String getTitle()
    {
        return user.screenName + "'s Timeline";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View page = inflater.inflate(R.layout.listpage_refresh_layout, container, false);
        PullToRefreshListView listView = (PullToRefreshListView) page.findViewById(R.id.listpage_listview);
        StatusListAdapter adapter = StatusListManager.getAdapter(StatusListManager.getUserTimeline(user.userId));
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new TimelineScrollListener(adapter));
        listView.setOnRefreshListener(new TimelineRefreshListener(StatusListManager.getUserTimeline(user.userId)));
        listView.onRefreshComplete();
        return page;
    }

    @Override
    public void onRemoved()
    {
        StatusListManager.removeUserTimeline(user.userId);
    }

}
