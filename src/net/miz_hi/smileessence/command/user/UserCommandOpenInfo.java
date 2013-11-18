package net.miz_hi.smileessence.command.user;

import android.app.Activity;
import android.app.ProgressDialog;
import net.miz_hi.smileessence.model.status.user.UserModel;
import net.miz_hi.smileessence.system.PageController;
import net.miz_hi.smileessence.task.impl.GetUserTask;
import net.miz_hi.smileessence.twitter.ResponseConverter;
import net.miz_hi.smileessence.view.fragment.impl.UserInfoFragment;
import twitter4j.User;

public class UserCommandOpenInfo extends UserCommand
{

    Activity activity;

    public UserCommandOpenInfo(String userName, Activity activity)
    {
        super(userName);
        this.activity = activity;
    }

    @Override
    public String getName()
    {
        return "ユーザー情報を見る";
    }

    @Override
    public void workOnUiThread()
    {
        final ProgressDialog pd = ProgressDialog.show(activity, null, "取得中...", true);
        new GetUserTask(userName)
        {
            @Override
            public void onPostExecute(User result)
            {
                UserModel model = ResponseConverter.convert(result);
                final UserInfoFragment fragment = UserInfoFragment.newInstance(model);
                PageController.getInstance().addPage(fragment);
                PageController.getInstance().moveToLast();
                pd.dismiss();
            }
        }.callAsync();
    }
}
