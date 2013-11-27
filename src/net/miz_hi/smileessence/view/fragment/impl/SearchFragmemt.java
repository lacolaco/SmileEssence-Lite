package net.miz_hi.smileessence.view.fragment.impl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import net.miz_hi.smileessence.view.fragment.IRemovable;
import net.miz_hi.smileessence.view.fragment.ISingleton;
import net.miz_hi.smileessence.view.fragment.NamedFragment;

public class SearchFragmemt extends NamedFragment implements IRemovable, ISingleton
{

    @Override
    public String getTitle()
    {
        return "Search";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onRemoved()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
