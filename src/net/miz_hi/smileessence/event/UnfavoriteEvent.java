package net.miz_hi.smileessence.event;

import twitter4j.Status;
import twitter4j.User;

public class UnfavoriteEvent extends StatusEvent implements IAttackEvent
{

	public UnfavoriteEvent(User source, Status targetStatus)
	{
		super(source, targetStatus);
	}

	@Override
	public String getText()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(source.getScreenName());
		sb.append("にあんふぁぼされた");
		return sb.toString();
	}

}
