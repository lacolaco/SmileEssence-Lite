package net.miz_hi.smileessence.status;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.R;
import net.miz_hi.smileessence.cache.ImageCache;
import net.miz_hi.smileessence.core.Settings;
import net.miz_hi.smileessence.model.status.IStatusModel;
import net.miz_hi.smileessence.model.status.event.EventModel;
import net.miz_hi.smileessence.model.status.event.StatusEvent;
import net.miz_hi.smileessence.model.status.tweet.EnumTweetType;
import net.miz_hi.smileessence.model.status.tweet.TweetModel;
import net.miz_hi.smileessence.model.status.user.UserModel;
import net.miz_hi.smileessence.preference.EnumPreferenceKey;
import net.miz_hi.smileessence.util.Morse;

public class StatusViewFactory
{

    LayoutInflater inflater;
    View baseView;
    NetworkImageView icon;
    TextView textTop;
    TextView textContent;
    TextView textBottom;
    ImageView favorited;
    View commands;
    int colorTop;
    int colorContent;
    int colorBottom;

    private StatusViewFactory()
    {
    }

    public static StatusViewFactory newInstance(LayoutInflater inflater, View baseView)
    {
        StatusViewFactory factory = new StatusViewFactory();
        factory.inflater = inflater;
        if(baseView == null)
        {
            factory.baseView = factory.inflater.inflate(R.layout.status_layout, null);
        }
        else
        {
            factory.baseView = baseView;
        }
        factory.icon = (NetworkImageView) factory.baseView.findViewById(R.id.status_icon);
        factory.textTop = (TextView) factory.baseView.findViewById(R.id.status_header);
        factory.textContent = (TextView) factory.baseView.findViewById(R.id.status_text);
        factory.textBottom = (TextView) factory.baseView.findViewById(R.id.status_footer);
        factory.favorited = (ImageView) factory.baseView.findViewById(R.id.status_favorited);
        factory.commands = factory.baseView.findViewById(R.id.status_commands);
        return factory;
    }

    public View getStatusView(IStatusModel model)
    {
        // initialize
        favorited.setVisibility(View.GONE);
        commands.setVisibility(View.GONE);
        Settings settings = Client.getSettings();
        int textSize = settings.getTextSize();
        textTop.setTextSize(textSize);
        textContent.setTextSize(textSize);
        textBottom.setTextSize(textSize - 2);
        colorTop = Client.getApplication().getResources().getColor(settings.getTheme().getHeaderTextColor());
        colorContent = Client.getApplication().getResources().getColor(settings.getTheme().getNormalTextColor());
        colorBottom = Client.getApplication().getResources().getColor(settings.getTheme().getHintTextColor());
        //adjust to model
        if(model instanceof TweetModel)
        {
            adjustToTweetView((TweetModel) model);
        }
        else if(model instanceof EventModel)
        {
            adjustToEventView((EventModel) model);
        }
        else if(model instanceof UserModel)
        {
            adjustToUserModel((UserModel) model);
        }
        //coloring
        textTop.setTextColor(colorTop);
        textContent.setTextColor(colorContent);
        textBottom.setTextColor(colorBottom);
        //set value
        icon.setDefaultImageResId(R.drawable.icon_refresh);
        ImageCache.setImageToView(model.getUser().iconUrl, icon);
        textTop.setText(model.getTextTop());
        String text;
        if(Morse.isMorse(model.getTextContent()) && Client.<Boolean>getPreferenceValue(EnumPreferenceKey.READ_MORSE))
        {
            text = model.getTextContent() + "\n(" + Morse.mcToJa(model.getTextContent()) + ")";
        }
        else
        {
            text = model.getTextContent();
        }
        textContent.setText(text);
        textBottom.setText(model.getTextBottom());

        return baseView;
    }

    private void adjustToTweetView(TweetModel model)
    {
        Settings settings = Client.getSettings();
        if(model.type == EnumTweetType.RETWEET)
        {
            baseView.setBackgroundColor(Client.getApplication()
                                              .getResources()
                                              .getColor(settings.getTheme().getRetweetBackgroundColor()));
        }
        else if(model.type == EnumTweetType.REPLY)
        {
            baseView.setBackgroundColor(Client.getApplication()
                                              .getResources()
                                              .getColor(settings.getTheme().getMentionsBackgroundColor()));
        }

        if(model.getUser().isMe())
        {
            colorTop = Client.getApplication().getResources().getColor(settings.getTheme().getSpecialTextColor());
        }
        favorited.setVisibility(model.isFavorited() ? View.VISIBLE : View.GONE);
    }

    private void adjustToEventView(EventModel model)
    {
        Settings settings = Client.getSettings();
        if(model instanceof StatusEvent)
        {
            colorTop = Client.getApplication().getResources().getColor(settings.getTheme().getSpecialTextColor());
        }
    }

    private void adjustToUserModel(UserModel model)
    {
        //TODO
    }

}
