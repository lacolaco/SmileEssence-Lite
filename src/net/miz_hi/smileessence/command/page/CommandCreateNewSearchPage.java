package net.miz_hi.smileessence.command.page;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.EditText;
import net.miz_hi.smileessence.command.MenuCommand;
import net.miz_hi.smileessence.data.search.Search;
import net.miz_hi.smileessence.data.search.SearchManager;
import net.miz_hi.smileessence.dialog.ContentDialog;
import net.miz_hi.smileessence.model.statuslist.timeline.impl.SearchTimeline;
import net.miz_hi.smileessence.statuslist.StatusListAdapter;
import net.miz_hi.smileessence.statuslist.StatusListManager;
import net.miz_hi.smileessence.system.PageController;
import net.miz_hi.smileessence.view.fragment.impl.SearchFragment;

public class CommandCreateNewSearchPage extends MenuCommand
{

    Activity activity;

    public CommandCreateNewSearchPage(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    public String getName()
    {
        return "検索タブを追加";
    }

    @Override
    public void workOnUiThread()
    {
        ContentDialog dialog = new ContentDialog(activity, "検索クエリを入力");
        final EditText editText = new EditText(activity);
        dialog.setContentView(editText);
        dialog.setOnClickListener(new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                switch (which)
                {
                    case Dialog.BUTTON_POSITIVE:
                    {
                        if (editText.length() == 0)
                        {
                            return;
                        }
                        String query = editText.getText().toString();
                        createNewSearch(query);
                        break;
                    }
                    default:
                    {

                    }
                }
            }
        });
        dialog.create().show();
    }

    private void createNewSearch(String query)
    {
        Search search = new Search(query);
        SearchManager.addSearch(search);
        SearchTimeline timeline = new SearchTimeline(query);
        StatusListManager.registerSearchTimeline(search.getId(), timeline, new StatusListAdapter(activity, timeline));
        SearchFragment fragment = SearchFragment.getInstance(search.getId(), search.getQuery());
        PageController.getInstance().addPage(fragment);
        PageController.getInstance().moveToLast();
        timeline.loadNewer();
    }

}
