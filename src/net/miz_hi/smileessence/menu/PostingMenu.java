package net.miz_hi.smileessence.menu;

import android.app.Activity;
import net.miz_hi.smileessence.command.ICommand;
import net.miz_hi.smileessence.command.post.*;
import net.miz_hi.smileessence.data.hashtag.Hashtag;
import net.miz_hi.smileessence.data.hashtag.HashtagManager;
import net.miz_hi.smileessence.data.template.Template;
import net.miz_hi.smileessence.data.template.TemplateManager;
import net.miz_hi.smileessence.dialog.ExpandMenuDialog;

import java.util.ArrayList;
import java.util.List;

public class PostingMenu extends ExpandMenuDialog
{

    public PostingMenu(Activity activity)
    {
        super(activity);
    }

    private List<ICommand> getHashtagMenu()
    {
        List<ICommand> list = new ArrayList<ICommand>();
        for (Hashtag hashtag : HashtagManager.getAll())
        {
            list.add(new CommandAppendHashtag(hashtag));
        }
        return list;
    }

    private List<ICommand> getTemplateMenu()
    {
        List<ICommand> list = new ArrayList<ICommand>();
        for (Template template : TemplateManager.getTemplates())
        {
            list.add(new CommandInsertText(template.getText()));
        }
        return list;
    }

    @Override
    public List<MenuElement> getElements()
    {
        List<MenuElement> list = new ArrayList<MenuElement>();

        MenuElement convert = new MenuElement("変換");
        convert.addChild(new MenuElement(new CommandParseMorse()));
        convert.addChild(new MenuElement(new CommandMakeAnonymous()));
        convert.addChild(new MenuElement(new CommandZekamashi()));
        list.add(convert);

        MenuElement template = new MenuElement("定型文");
        List<ICommand> templates = getTemplateMenu();
        if (!templates.isEmpty())
        {
            for (ICommand iCommand : templates)
            {
                template.addChild(new MenuElement(iCommand));
            }
            list.add(template);
        }

        MenuElement hashtag = new MenuElement("よく使うハッシュタグ");
        List<ICommand> hashtags = getHashtagMenu();
        if (!hashtags.isEmpty())
        {
            for (ICommand iCommand : hashtags)
            {
                hashtag.addChild(new MenuElement(iCommand));
            }
            list.add(hashtag);
        }
        return list;
    }


}
