package net.miz_hi.smileessence.listener;

import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.async.MyExecutor;
import net.miz_hi.smileessence.core.Notifier;
import net.miz_hi.smileessence.preference.EnumPreferenceKey;
import net.miz_hi.smileessence.util.LogHelper;
import net.miz_hi.smileessence.util.UiHandler;
import net.miz_hi.smileessence.view.MainActivity;
import net.miz_hi.smileessence.view.PostFragment;
import android.content.Context;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class PageChangeListener implements OnPageChangeListener
{
	
	boolean isOpening = false;

	@Override
	public void onPageScrollStateChanged(int position)
	{
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2)
	{
	}

	@Override
	public void onPageSelected(final int position)
	{
		MyExecutor.execute(new Runnable()
		{
			
			@Override
			public void run()
			{
				if(position == MainActivity.PAGE_POST)
				{
					PostFragment.singleton().load();
					isOpening = true;
				}
				else
				{
					if(isOpening)
					{
						PostFragment.singleton().save();
					}
					isOpening = false;
				}
			}
		});

	}

}