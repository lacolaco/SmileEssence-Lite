package net.miz_hi.smileessence.system;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import net.miz_hi.smileessence.view.fragment.NamedFragment;
import net.miz_hi.smileessence.view.fragment.NamedFragmentPagerAdapter;
import net.miz_hi.smileessence.view.fragment.impl.HistoryFragment;
import net.miz_hi.smileessence.view.fragment.impl.HomeFragment;
import net.miz_hi.smileessence.view.fragment.impl.MentionsFragment;
import net.miz_hi.smileessence.view.fragment.impl.PostFragment;

import java.util.ArrayList;
import java.util.List;


public class PageController
{

    private static PageController instance;
    public static final int PAGE_POST = 0;
    private NamedFragmentPagerAdapter adapter;
    private ViewPager pager;
    private FragmentActivity activity;

    public static PageController getInstance()
    {
        return instance;
    }

    public static void init(FragmentActivity activity, ViewPager pager)
    {
        instance = new PageController(activity, pager);
    }

    private PageController(FragmentActivity activity, ViewPager pager)
    {
        adapter = new NamedFragmentPagerAdapter(activity.getSupportFragmentManager());
        this.activity = activity;
        this.pager = pager;
        initPages(activity);
    }

    private void initPages(Activity activity)
    {
        adapter.add((NamedFragment) Fragment.instantiate(activity, PostFragment.class.getName()));
        adapter.add((NamedFragment) Fragment.instantiate(activity, HomeFragment.class.getName()));
        adapter.add((NamedFragment) Fragment.instantiate(activity, MentionsFragment.class.getName()));
        adapter.add((NamedFragment) Fragment.instantiate(activity, HistoryFragment.class.getName()));
    }

    public void move(int index)
    {

        pager.setCurrentItem(index, true);
    }

    public void moveToLast()
    {
        pager.setCurrentItem(adapter.getCount(), false);
    }

    public int getCurrentPage()
    {
        return instance.pager.getCurrentItem();
    }

    public void addPage(NamedFragment fragment)
    {
        adapter.add(fragment);
    }

    public void removePage()
    {
        int current = pager.getCurrentItem();
        adapter.remove(current);
        List<NamedFragment> list = new ArrayList<NamedFragment>();
        list.addAll(adapter.getList());
        adapter.clear();
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
        pager.setCurrentItem(current, true);
    }

    public NamedFragmentPagerAdapter getAdapter()
    {
        return adapter;
    }
}
