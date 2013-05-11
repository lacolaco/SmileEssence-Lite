package net.miz_hi.smileessence.listener;

import de.keyboardsurfer.android.widget.crouton.Style;
import net.miz_hi.smileessence.Client;
import net.miz_hi.smileessence.core.Notifier;
import net.miz_hi.smileessence.data.StatusModel;
import net.miz_hi.smileessence.data.StatusStore;
import net.miz_hi.smileessence.data.extra.ExtraWord;
import net.miz_hi.smileessence.data.extra.ExtraWords;
import net.miz_hi.smileessence.event.BlockEvent;
import net.miz_hi.smileessence.event.DirectMessageEvent;
import net.miz_hi.smileessence.event.Event;
import net.miz_hi.smileessence.event.FavoriteEvent;
import net.miz_hi.smileessence.event.FollowEvent;
import net.miz_hi.smileessence.event.HistoryListAdapter;
import net.miz_hi.smileessence.event.ReplyEvent;
import net.miz_hi.smileessence.event.RetweetEvent;
import net.miz_hi.smileessence.event.UnblockEvent;
import net.miz_hi.smileessence.event.UnfavoriteEvent;
import net.miz_hi.smileessence.preference.EnumPreferenceKey;
import net.miz_hi.smileessence.status.StatusListAdapter;
import net.miz_hi.smileessence.system.MainSystem;
import net.miz_hi.smileessence.system.RelationSystem;
import net.miz_hi.smileessence.util.LogHelper;
import net.miz_hi.smileessence.view.ExtractFragment;
import net.miz_hi.smileessence.view.MainActivity;
import net.miz_hi.smileessence.view.RelationFragment;
import twitter4j.ConnectionLifeCycleListener;
import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;

public class MyUserStreamListener implements UserStreamListener, ConnectionLifeCycleListener
{
	private StatusListAdapter homeListAdapter;
	private StatusListAdapter mentionsListAdapter;
	private HistoryListAdapter eventListAdapter;
	
	private int exceptionCount;

	public MyUserStreamListener()
	{
	}

	public void setHomeAdapter(StatusListAdapter adapter)
	{
		this.homeListAdapter = adapter;
	}

	public void setMentionsAdapter(StatusListAdapter adapter)
	{
		this.mentionsListAdapter = adapter;
	}

	public void setHistoryAdapter(HistoryListAdapter adapter)
	{
		this.eventListAdapter = adapter;
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice arg0)
	{
		LogHelper.d("on status detete");
		final StatusModel model = StatusStore.get(arg0.getStatusId());
		if (model == null)
		{
			return;
		}
		else
		{
			homeListAdapter.removeElement(model);
			homeListAdapter.notifyAdapter();
			mentionsListAdapter.removeElement(model);
			mentionsListAdapter.notifyAdapter();
			for(StatusListAdapter adapter : RelationSystem.getAdapters())
			{
				adapter.removeElement(model);
				adapter.notifyAdapter();
			}
		}
	}

	@Override
	public void onScrubGeo(long arg0, long arg1)
	{
	}

	@Override
	public void onStallWarning(StallWarning arg0)
	{
	}

	@Override
	public void onStatus(Status status)
	{
		if(MainActivity.getInstance() == null || MainActivity.getInstance().isFinishing())
		{
			return;
		}
		
		final StatusModel model = StatusStore.put(status);

		if (model.isRetweet && model.isMine)
		{
			eventListAdapter.addFirst(new RetweetEvent(status.getUser(), status));
		}
		else if (!model.isRetweet && model.isReply)
		{
			Notifier.buildEvent(new ReplyEvent(status.getUser(), status)).raise();
		}

		if (model.isReply)
		{
			mentionsListAdapter.addFirst(model);
			mentionsListAdapter.notifyAdapter();
		}
		
		if(!model.isRetweet && !ExtraWords.getExtraWords().isEmpty())
		{
			for(ExtraWord word : ExtraWords.getExtraWords())
			{
				if(model.text.contains(word.getText()))
				{
					ExtractFragment.singleton().getAdapter().addFirst(model);
					ExtractFragment.singleton().getAdapter().notifyAdapter();
					break;
				}
			}
		}
		if(RelationSystem.isChasing())
		{
			RelationFragment rel = RelationSystem.getRelationByChasingId(model.inReplyToStatusId);
			if(rel != null)
			{
				StatusListAdapter relAdapter = RelationSystem.getAdapter(rel);
				relAdapter.addFirst(model);
				relAdapter.notifyAdapter();
				rel.setChasingId(model.statusId);
			}
		}

		homeListAdapter.addFirst(model);				
		homeListAdapter.notifyAdapter();
	}

	@Override
	public void onTrackLimitationNotice(int arg0)
	{
	}

	@Override
	public void onException(Exception arg0)
	{
		arg0.printStackTrace();
		Notifier.alert("接続が切れました");	
	}

	@Override
	public void onBlock(User sourceUser, User targetUser)
	{
		if (targetUser.getId() == Client.getMainAccount().getUserId())
		{
			eventListAdapter.addFirst(new BlockEvent(sourceUser));
			eventListAdapter.notifyAdapter();	
		}
	}

	@Override
	public void onDeletionNotice(long arg0, long arg1)
	{
	}

	@Override
	public void onDirectMessage(DirectMessage message)
	{
		if (message.getRecipientId() == Client.getMainAccount().getUserId())
		{
			eventListAdapter.addFirst(new DirectMessageEvent(message.getSender()));
			eventListAdapter.notifyAdapter();	
		}
	}

	@Override
	public void onFavorite(User sourceUser, User targetUser, Status targetStatus)
	{
		if(sourceUser.getId() == Client.getMainAccount().getUserId())
		{
			if(targetStatus.isRetweet())
			{
				StatusStore.putFavoritedStatus(targetStatus.getRetweetedStatus().getId());				
			}
			else
			{
				StatusStore.putFavoritedStatus(targetStatus.getId());
			}			

			MainSystem.getInstance().homeListAdapter.notifyAdapter();
			MainSystem.getInstance().mentionsListAdapter.notifyAdapter();
		}
		if (targetUser.getId() == Client.getMainAccount().getUserId())
		{
			eventListAdapter.addFirst(new FavoriteEvent(sourceUser, targetStatus));
			eventListAdapter.notifyAdapter();	
		}
	}

	@Override
	public void onFollow(User sourceUser, User targetUser)
	{
		if(sourceUser.getId() != Client.getMainAccount().getUserId())
		{
			eventListAdapter.addFirst(new FollowEvent(sourceUser));	
			eventListAdapter.notifyAdapter();	
		}
	}

	@Override
	public void onFriendList(long[] arg0)
	{
	}

	@Override
	public void onUnblock(User sourceUser, User targetUser)
	{
		if (targetUser.getId() == Client.getMainAccount().getUserId())
		{
			eventListAdapter.addFirst(new UnblockEvent(sourceUser));
			eventListAdapter.notifyAdapter();	
		}
	}

	@Override
	public void onUnfavorite(User sourceUser, User targetUser, Status targetStatus)
	{
		if(sourceUser.getId() == Client.getMainAccount().getUserId())
		{
			if(targetStatus.isRetweet())
			{
				StatusStore.removeFavoritedStatus(targetStatus.getRetweetedStatus().getId());				
			}
			else
			{
				StatusStore.removeFavoritedStatus(targetStatus.getId());
			}			

			MainSystem.getInstance().homeListAdapter.notifyAdapter();
			MainSystem.getInstance().mentionsListAdapter.notifyAdapter();
		}
		
		if(Client.<Boolean>getPreferenceValue(EnumPreferenceKey.NOTICE_UNFAV))
		{
			if (targetUser.getId() == Client.getMainAccount().getUserId())
			{
				eventListAdapter.addFirst(new UnfavoriteEvent(sourceUser, targetStatus));
				eventListAdapter.notifyAdapter();	
			}
		}
	}

	@Override
	public void onUserListCreation(User arg0, UserList arg1)
	{
	}

	@Override
	public void onUserListDeletion(User arg0, UserList arg1)
	{
	}

	@Override
	public void onUserListMemberAddition(User arg0, User arg1, UserList arg2)
	{
	}

	@Override
	public void onUserListMemberDeletion(User arg0, User arg1, UserList arg2)
	{
	}

	@Override
	public void onUserListSubscription(User arg0, User arg1, UserList arg2)
	{
	}

	@Override
	public void onUserListUnsubscription(User arg0, User arg1, UserList arg2)
	{
	}

	@Override
	public void onUserListUpdate(User arg0, UserList arg1)
	{
	}

	@Override
	public void onUserProfileUpdate(User arg0)
	{
	}

	@Override
	public void onCleanUp()
	{
	}

	@Override
	public void onConnect()
	{
		Notifier.info("接続しました");
	}

	@Override
	public void onDisconnect()
	{
	}

}
