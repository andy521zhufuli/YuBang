package com.car.yubangapk.utils.Warn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.car.yubangapk.ui.UpdateAppActivity;
import com.car.yubangapk.view.AlertDialog;

/**
 * 提示用户没有产品包
 * Created by andy on 16/4/16.
 */
public class NoProductPackage {

    public static void tishiNoPPkg(final Activity context)
    {
        AlertDialog alertDialog = new AlertDialog(context);
        alertDialog.builder()
                .setTitle("提示")
                .setMsg("没有相关产品包")
                .setCancelable(false)
                .setPositiveButton("返回", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setClass(context, UpdateAppActivity.class);
                        context.finish();
                    }
                })

                .show();
    }

}
