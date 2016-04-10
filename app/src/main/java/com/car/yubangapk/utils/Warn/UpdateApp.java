package com.car.yubangapk.utils.Warn;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.car.yubangapk.ui.UpdateAppActivity;
import com.car.yubangapk.view.AlertDialog;

/**
 * Created by andy on 16/4/10.
 */
public  class UpdateApp {

    public static void gotoUpdateApp(final Context context)
    {
        AlertDialog alertDialog = new AlertDialog(context);
        alertDialog.builder()
                .setTitle("提示")
                .setMsg("您当前版本太低,请升级新版本")
                .setCancelable(false)
                .setPositiveButton("立即更新", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setClass(context, UpdateAppActivity.class);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("下次再说", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .show();
    }
}
