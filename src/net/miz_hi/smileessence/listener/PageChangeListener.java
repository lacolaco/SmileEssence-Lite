package net.miz_hi.smileessence.listener;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import net.miz_hi.smileessence.system.PageController;
import net.miz_hi.smileessence.view.fragment.NamedFragment;

public class PageChangeListener implements OnPageChangeListener
{

    int lastPosition = -1;

    @Override
    public void onPageScrollStateChanged(int position)
    {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2)
    {
    }

    @Override
    public void onPageSelected(int position)
    {
        if (lastPosition > -1 && lastPosition < PageController.getInstance().getCount())
        {
            NamedFragment from = PageController.getInstance().getPage(lastPosition);
            from.onDeselect();
        }
        NamedFragment to = PageController.getInstance().getPage(position);
        to.onSelected();
        lastPosition = position;
    }

}
