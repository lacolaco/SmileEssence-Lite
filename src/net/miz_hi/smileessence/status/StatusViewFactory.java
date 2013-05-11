package net.miz_hi.smileessence.status;

import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.R;
import net.miz_hi.smileessence.data.IconCaches;
import net.miz_hi.smileessence.data.StatusModel;
import net.miz_hi.smileessence.data.StatusStore;
import net.miz_hi.smileessence.preference.EnumPreferenceKey;
import net.miz_hi.smileessence.util.Morse;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class StatusViewFactory
{
	
	public static View getView(LayoutInflater layoutInflater, StatusModel model, View baseView)
	{
		if(baseView == null)
		{
			baseView = layoutInflater.inflate(R.layout.status_layout, null);
		}
		ImageView viewIcon = (ImageView) baseView.findViewById(R.id.imageView_icon);
		TextView viewHeader = (TextView) baseView.findViewById(R.id.textView_header);
		TextView viewText = (TextView) baseView.findViewById(R.id.textView_text);
		TextView viewFooter = (TextView) baseView.findViewById(R.id.textView_footer);
		ImageView viewFavorited = (ImageView)baseView.findViewById(R.id.imageView_favorited);

		if (model.isRetweet)
		{
			model.backgroundColor = Client.getColor(R.color.LightBlue);
		}
		else if (model.isReply)
		{
			model.backgroundColor = Client.getColor(R.color.LightRed);
		}
		else
		{
			model.backgroundColor = Client.getColor(R.color.White);
		}
		
		baseView.setBackgroundColor(model.backgroundColor);
		
		if (model.isMine)
		{
			model.nameColor = Client.getColor(R.color.DarkBlue);
		}
		else
		{
			model.nameColor = Client.getColor(R.color.ThickGreen);
		}
		
		model.textColor = Client.getColor(R.color.Gray);

		viewIcon.setTag(model.user.userId);
		IconCaches.setIconBitmapToView(model.user, viewIcon);
		
		viewFavorited.setVisibility(StatusStore.isFavorited(model.statusId) ? View.VISIBLE : View.GONE);
		
		int textSize = Client.getTextSize();
		viewHeader.setText(model.headerText);
		viewHeader.setTextColor(model.nameColor);
		viewHeader.setTextSize(textSize);
		String text;
		if(Morse.isMorse(model.text) && Client.<Boolean>getPreferenceValue(EnumPreferenceKey.READ_MORSE))
		{
			text = model.text + "\n(" + Morse.mcToJa(model.text) + ")";
		}
		else
		{
			text = model.text;
		}
		viewText.setText(text);
		viewText.setTextColor(model.textColor);
		viewText.setTextSize(textSize);
		viewFooter.setText(model.footerText);
		viewFooter.setTextColor(model.textColor);
		viewFooter.setTextSize(textSize - 1);
		
		return baseView;
	}

	public static View getView(LayoutInflater layoutInflater, StatusModel model)
	{
		return getView(layoutInflater, model, null);
	}
}
