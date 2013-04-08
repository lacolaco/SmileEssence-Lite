package net.miz_hi.smileessence.command;

import twitter4j.TwitterException;
import net.miz_hi.smileessence.async.MyExecutor;
import net.miz_hi.smileessence.data.StatusModel;
import net.miz_hi.smileessence.event.ToastManager;
import net.miz_hi.smileessence.util.TwitterManager;

public class StatusCommandUnfavorite extends StatusCommand implements IHideable
{

	public StatusCommandUnfavorite(StatusModel status)
	{
		super(status);
	}

	@Override
	public String getName()
	{
		return "お気に入りを削除する";
	}
	
	@Override
	public void workOnUiThread()
	{
		MyExecutor.execute(new Runnable()
		{
			
			@Override
			public void run()
			{
				try
				{
					TwitterManager.getTwitter().destroyFavorite(status.statusId);
					ToastManager.toast("お気に入りを削除しました");
				}
				catch (TwitterException e)
				{
					e.printStackTrace();
					ToastManager.toast("お気に入りの削除に失敗しました");
				}
			}
		});

	}

}
