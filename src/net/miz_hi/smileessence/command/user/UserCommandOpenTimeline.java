package net.miz_hi.smileessence.command.user;

import android.app.Activity;
import android.app.ProgressDialog;
import net.miz_hi.smileessence.model.status.ResponseConverter;
import net.miz_hi.smileessence.model.status.user.UserModel;
import net.miz_hi.smileessence.model.statuslist.timeline.impl.UserTimeline;
import net.miz_hi.smileessence.statuslist.StatusListAdapter;
import net.miz_hi.smileessence.statuslist.StatusListManager;
import net.miz_hi.smileessence.system.PageController;
import net.miz_hi.smileessence.task.impl.GetUserTask;
import net.miz_hi.smileessence.view.fragment.impl.UserTimelineFragment;
import twitter4j.User;

public class UserCommandOpenTimeline extends UserCommand
{

    Activity activity;

    public UserCommandOpenTimeline(String userName, Activity activity)
    {
        super(userName);
        this.activity = activity;
    }

    @Override
    public String getName()
    {
        return "ユーザーのタイムラインを開く";
    }

    @Override
    public void workOnUiThread()
    {
        final ProgressDialog pd = ProgressDialog.show(activity, null, "取得中...", true);
        new GetUserTask(userName)
        {
            @Override
            public void onPostExecute(User user)
            {
                UserModel model = ResponseConverter.convert(user);
                UserTimeline timeline = new UserTimeline(model.userId);
                StatusListManager.registerUserTimeline(model.userId, timeline, new StatusListAdapter(activity, timeline));
                UserTimelineFragment fragment = UserTimelineFragment.newInstance(model);
                PageController.getInstance().addPage(fragment);
                PageController.getInstance().moveToLast();
                timeline.loadNewer();
                pd.dismiss();
            }
        }.callAsync();

    }

}
