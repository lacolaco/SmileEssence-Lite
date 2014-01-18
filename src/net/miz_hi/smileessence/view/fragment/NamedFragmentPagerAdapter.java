package net.miz_hi.smileessence.view.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class NamedFragmentPagerAdapter extends FragmentStatePagerAdapter
{

    private ArrayList<NamedFragment> pageList = new ArrayList<NamedFragment>();
    private HashMap<String, NamedFragment> pageMap = new HashMap<String, NamedFragment>();

    public NamedFragmentPagerAdapter(FragmentManager fm, Collection<NamedFragment> fragments)
    {
        this(fm);
        pageList.addAll(fragments);
    }

    public NamedFragmentPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public synchronized CharSequence getPageTitle(int position)
    {
        return pageList.get(position).getTitle();
    }

    @Override
    public synchronized int getCount()
    {
        return pageList.size();
    }

    public List<NamedFragment> getList()
    {
        return pageList;
    }

    public synchronized void add(NamedFragment element)
    {
        pageList.add(element);
        pageMap.put(element.getTitle(), element);
    }

    public synchronized void addAll(Collection<NamedFragment> list)
    {
        pageList.addAll(list);
        for(NamedFragment fragment : list)
        {
            pageMap.put(fragment.getTitle(), fragment);
        }
    }

    public synchronized void set(NamedFragment element, int index)
    {
        pageList.add(index, element);
        pageMap.put(element.getTitle(), element);
    }

    public synchronized NamedFragment getByName(String name)
    {
        return pageMap.get(name);
    }

    public synchronized void remove(int i)
    {
        NamedFragment fragment = pageList.get(i);
        remove(fragment);
    }

    public synchronized void remove(String name)
    {
        NamedFragment fragment = pageMap.get(name);
        if(fragment != null)
        {
            remove(fragment);
        }
    }

    public synchronized void remove(NamedFragment element)
    {
        pageList.remove(element);
        pageMap.remove(element.getTitle());
        if(element instanceof IRemovable)
        {
            ((IRemovable) element).onRemoved();
        }
    }

    public synchronized void clear()
    {
        pageList.clear();
    }

    @Override
    public Fragment getItem(int position)
    {
        return pageList.get(position);
    }

    @Override
    public int getItemPosition(Object object)
    {
        int index = pageList.indexOf(object);
        return index != -1 ? index : POSITION_NONE;
    }

}
