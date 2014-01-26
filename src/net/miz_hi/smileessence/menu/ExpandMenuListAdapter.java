package net.miz_hi.smileessence.menu;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.R;
import net.miz_hi.smileessence.command.ICommand;
import net.miz_hi.smileessence.theme.IColorTheme;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExpandMenuListAdapter extends BaseExpandableListAdapter
{

    private List<MenuElement> elements;
    private Activity activity;
    private LayoutInflater inflater;

    public ExpandMenuListAdapter(Activity activity, Collection<MenuElement> elements)
    {
        this.activity = activity;
        this.elements = new ArrayList<MenuElement>(elements);
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    public Object getChild(int arg0, int arg1)
    {
        return elements.get(arg0).getChildren().get(arg1);
    }

    @Override
    public long getChildId(int arg0, int arg1)
    {
        return arg1;
    }

    @Override
    public View getChildView(int arg0, int arg1, boolean arg2, View view, ViewGroup arg4)
    {

        view = inflater.inflate(Client.getSettings().getTheme().getMenuItemLayout(), null);

        final ICommand item = ((MenuElement) getChild(arg0, arg1)).getCommand();

        TextView textView = (TextView) view.findViewById(R.id.menuitem_text);
        textView.setText(item.getName());
        view.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                item.run();
            }

        });
        return view;
    }

    @Override
    public int getChildrenCount(int arg0)
    {
        return elements.get(arg0).getChildren().size();
    }

    @Override
    public Object getGroup(int arg0)
    {
        return elements.get(arg0);
    }

    @Override
    public int getGroupCount()
    {
        return elements.size();
    }

    @Override
    public long getGroupId(int arg0)
    {
        return arg0;
    }

    @Override
    public View getGroupView(int arg0, boolean isExpanded, View view, ViewGroup parent)
    {
        MenuElement element = elements.get(arg0);
        IColorTheme theme = Client.getSettings().getTheme();
        if(element.isParent())
        {
            view = inflater.inflate(theme.getMenuParentLayout(), null);
            TextView textView = (TextView) view.findViewById(R.id.menuitem_text);
            textView.setText(element.getName());
            ImageView indicator = (ImageView) view.findViewById(R.id.menuparent_indicator);
            indicator.setImageResource(isExpanded ? theme.getMenuParentOpenIcon() : theme.getMenuParentCloseIcon());
        }
        else
        {
            view = inflater.inflate(theme.getMenuItemLayout(), null);

            final ICommand item = element.getCommand();

            TextView textView = (TextView) view.findViewById(R.id.menuitem_text);
            textView.setText(item.getName());

            view.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    item.run();
                }

            });
        }

        return view;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1)
    {
        return true;
    }


}
