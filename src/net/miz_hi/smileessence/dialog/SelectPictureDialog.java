package net.miz_hi.smileessence.dialog;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.provider.MediaStore;
import net.miz_hi.smileessence.command.MenuCommand;
import net.miz_hi.smileessence.core.EnumRequestCode;
import net.miz_hi.smileessence.system.MainActivitySystem;
import net.miz_hi.smileessence.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class SelectPictureDialog extends SimpleMenuDialog
{

    public SelectPictureDialog(Activity activity)
    {
        super(activity);
    }

    @Override
    public List<MenuCommand> getMenuList()
    {
        List<MenuCommand> list = new ArrayList<MenuCommand>();
        list.add(new MenuCommand()
        {
            @Override
            public void workOnUiThread()
            {
                startGallery();
            }

            @Override
            public String getName()
            {
                return "画像を選択";
            }
        });

        list.add(new MenuCommand()
        {
            @Override
            public void workOnUiThread()
            {
                startCamera();
            }

            @Override
            public String getName()
            {
                return "カメラを起動";
            }
        });
        return list;
    }


    private void startGallery()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, EnumRequestCode.PICTURE.ordinal());
    }

    private void startCamera()
    {
        MainActivitySystem system = MainActivity.getInstance().system;
        ContentValues values = new ContentValues();
        String filename = System.currentTimeMillis() + ".jpg";
        // 必要な情報を詰める
        values.put(MediaStore.MediaColumns.TITLE, filename);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

        // Uriを取得して覚えておく、Intentにも保存先として渡す
        system.tempFilePath = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        // インテントの設定
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, system.tempFilePath);
        activity.startActivityForResult(intent, EnumRequestCode.CAMERA.ordinal());
    }
}
