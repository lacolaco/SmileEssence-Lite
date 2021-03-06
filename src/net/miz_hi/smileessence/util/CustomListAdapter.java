package net.miz_hi.smileessence.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;

public abstract class CustomListAdapter<T> extends BaseAdapter
{

    private T[] array;
    private ArrayList<T> list;
    private int count;
    private final Object lock = new Object();
    private boolean canNotifyOnChange = true;
    private Activity activity;
    private LayoutInflater inflater;
    private int capacity;

    public CustomListAdapter(Activity activity, int capacity)
    {
        this.capacity = capacity;
        this.list = new ArrayList<T>();
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
    }

    public void addAll(Collection<T> collection)
    {
        synchronized (lock)
        {
            for (T element : collection)
            {
                if (list.contains(element))
                {
                    list.remove(element);
                }
                list.add(element);
                if (list.size() >= capacity)
                {
                    break;
                }
            }
        }
    }

    public void addFirst(T element)
    {
        synchronized (lock)
        {
            if (list.contains(element))
            {
                return;
            }
            list.add(0, element);

            if (list.size() >= capacity)
            {
                list.remove(list.size() - 1);
            }
        }
    }

    public void addLast(T element)
    {
        synchronized (lock)
        {
            if (list.contains(element))
            {
                list.remove(element);
            }
            list.add(element);
        }
    }

    public void removeElement(T element)
    {
        synchronized (lock)
        {
            list.remove(element);
        }
    }

    public void clear()
    {
        synchronized (lock)
        {
            list.clear();
        }
    }

    public void notifyAdapter()
    {
        if (canNotifyOnChange)
        {
            forceNotifyAdapter();
        }
    }

    public void forceNotifyAdapter()
    {
        synchronized (lock)
        {
            new UiHandler()
            {

                @Override
                public void run()
                {
                    notifyDataSetChanged();
                }
            }.post();
        }
    }

    public void setCanNotifyOnChange(boolean notifyOnChange)
    {
        synchronized (lock)
        {
            this.canNotifyOnChange = notifyOnChange;
        }
    }

    public boolean getCanNotifyOnChange()
    {
        synchronized (lock)
        {
            return canNotifyOnChange;
        }
    }

    public Activity getActivity()
    {
        return activity;
    }


    @Override
    public void notifyDataSetChanged()
    {
        synchronized (lock)
        {
            array = (T[]) list.toArray();
            count = array.length;
            CustomListAdapter.super.notifyDataSetChanged();
        }
    }

    @Override
    public int getCount()
    {
        return count;
    }

    @Override
    public Object getItem(int position)
    {
        synchronized (lock)
        {
            if (array != null && array.length >= position)
            {
                return array[position];
            }
            else
            {
                return null;
            }
        }
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public LayoutInflater getInflater()
    {
        return inflater;
    }

    @Override
    public abstract View getView(int position, View convertedView, ViewGroup parent);

}
