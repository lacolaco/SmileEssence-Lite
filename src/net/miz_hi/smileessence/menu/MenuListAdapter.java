package net.miz_hi.smileessence.menu;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import net.miz_hi.smileessence.R;
import net.miz_hi.smileessence.command.MenuCommand;
import net.miz_hi.smileessence.util.CustomListAdapter;

public class MenuListAdapter extends CustomListAdapter<MenuCommand>
{

    public MenuListAdapter(Activity activity)
    {
        super(activity, 100);

    }

    @Override
    public View getView(int position, View convertedView, ViewGroup parent)
    {
        if (convertedView == null)
        {
            convertedView = getInflater().inflate(R.layout.menuitem_white, null);
        }

        final MenuCommand item = (MenuCommand) getItem(position);

        TextView textView = (TextView) convertedView.findViewById(R.id.textView_menuItem);
        textView.setText(item.getName());
        convertedView.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                item.run();
            }
        });
        return convertedView;
    }

}
