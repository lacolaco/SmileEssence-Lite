package net.miz_hi.smileessence.view.fragment.impl;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import net.miz_hi.smileessence.R;
import net.miz_hi.smileessence.core.MyExecutor;
import net.miz_hi.smileessence.listener.TimelineScrollListener;
import net.miz_hi.smileessence.model.status.user.UserModel;
import net.miz_hi.smileessence.model.statuslist.timeline.impl.UserTimeline;
import net.miz_hi.smileessence.statuslist.StatusListAdapter;
import net.miz_hi.smileessence.statuslist.StatusListManager;
import net.miz_hi.smileessence.util.UiHandler;
import net.miz_hi.smileessence.view.fragment.IRemovable;
import net.miz_hi.smileessence.view.fragment.ISingleton;
import net.miz_hi.smileessence.view.fragment.NamedFragment;

import java.util.concurrent.ExecutionException;

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
        listView.setOnScrollListener(new TimelineScrollListener(adapter, StatusListManager.getUserTimeline(user.userId)));
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

    private void refresh()
    {
        UserTimeline timeline = StatusListManager.getUserTimeline(user.userId);
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
        StatusListManager.removeUserTimeline(user.userId);
    }

}
