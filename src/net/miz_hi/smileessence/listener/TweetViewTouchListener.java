package net.miz_hi.smileessence.listener;

import net.miz_hi.smileessence.core.UiHandler;
import net.miz_hi.smileessence.util.LogHelper;
import net.miz_hi.smileessence.view.MainActivity;
import net.miz_hi.smileessence.view.TweetViewManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class TweetViewTouchListener implements OnTouchListener
{

	// X位置の動作判断
	private float MARGIN_X = 60.0f;
	// 触れた瞬間のX位置
	private float mDownX;
	// 触れた瞬間のY位置
	private float mDownY;
	// 右移動
	private final static int MOVE_RIGHT = 1;
	// 左移動
	private final static int MOVE_LEFT = -1;

	public boolean onTouch(MotionEvent event)
	{
		int action = event.getAction();
		switch (action)
		{
			case MotionEvent.ACTION_DOWN:
			{
				actionDown(event);					
			}
			case MotionEvent.ACTION_UP:
			{
				return actionUp(event);
			}
		}
		return false;
	}
	
	private void actionDown(MotionEvent event)
	{
		// 位置を保存
		mDownX = event.getX();
		mDownY = event.getY();
	}

	private boolean actionUp(MotionEvent event)
	{
		// 現在位置と ACTION_DOWN 時の位置を比較
		float moveX = mDownX - event.getX();
		float moveY = mDownY - event.getY();
		// 移動距離が範囲内なら無視
		if (Math.abs(moveX) > MARGIN_X )
		{
			changeList(moveX > 0 ? MOVE_RIGHT : MOVE_LEFT);
			return true;
		}
		return false;
	}

	private void changeList(int i)
	{
		switch (i)
		{
			case MOVE_LEFT:
			{
				break;
			}
			case MOVE_RIGHT:
			{
				TweetViewManager.getInstance().close();
				break;
			}
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		return onTouch(event);
	}

}
