package com.car.yubangapk.utils.Warn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.car.yubangapk.ui.LoginActivity;
import com.car.yubangapk.ui.UpdateAppActivity;
import com.car.yubangapk.view.AlertDialog;

/**
 * Created by andy on 16/4/16.
 */
public class NotLogin
{
    public static void gotoLogin(final Activity context)
    {
        AlertDialog alertDialog = new AlertDialog(context);
        alertDialog.builder()
                .setTitle("提示")
                .setMsg("您还没有登录")
                .setCancelable(false)
                .setPositiveButton("去登陆", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setClass(context, LoginActivity.class);
                        context.startActivity(intent);
                        context.finish();
                    }
                })
                .show();
    }
}
