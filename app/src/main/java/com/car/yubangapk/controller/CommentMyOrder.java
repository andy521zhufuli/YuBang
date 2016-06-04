package com.car.yubangapk.controller;

import android.app.Activity;

import com.car.yubangapk.network.myHttpReq.HttpReqPushComment;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.utils.Warn.NotLogin;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.CustomProgressDialog;

import java.util.List;

/**
 * Created by andy on 16/5/31.
 *
 * 我的订单 里面评价我的订单
 *
 */
public class CommentMyOrder implements HttpReqCallback
{

    public static final int COMMENT_TYPE_WORD = 0;
    public static final int COMMENT_TYPE_PIC  = 1;

    String userid;
    String orderId;
    String commentContent;
    int uploadType;//1 传图片  0 穿文字
    double star;
    List<String> filePath;
    Activity  context;

    CustomProgressDialog mProgress;

    /**
     *
     * @param userid
     * @param orderId
     * @param commentContent
     * @param uploadType
     * @param star
     * @param filePath
     * @param context
     */
    public CommentMyOrder(String userid,
                          String orderId,
                          String commentContent,
                          int uploadType,//1 传图片  0 穿文字
                          double star,
                          List<String> filePath,
                          Activity context
                          )
    {
        this.userid = userid;
        this.orderId = orderId;
        this.commentContent = commentContent;
        this.uploadType = uploadType;
        this.star = star;
        this.filePath = filePath;
        this.context = context;
        mProgress = new CustomProgressDialog(context);
    }

    /**
     * 上传评论文字
     */
    public void uploadContent()
    {
        HttpReqPushComment pushComment = new HttpReqPushComment();
        pushComment.setCallback(this);
        pushComment.pushComment(userid, orderId, commentContent, uploadType, (int) star);
        mProgress = mProgress.show(context, "正在上传...", false, null);

    }

    /**
     * 上传图片
     */
    public void uploadCommentPic()
    {

    }


    public void setUploadType(int type)
    {
        this.uploadType = type;
    }

    public int getUploadType()
    {
        return this.uploadType;
    }

    @Override
    public void onFail(int errorCode, String message) {
        mProgress.dismiss();

        if (errorCode == 100)
        {
            NotLogin.gotoLogin(this.context);
        }
    }

    @Override
    public void onSuccess(Object object) {
        toastMgr.builder.display("添加评论成功",1 );
        mProgress.dismiss();
        context.finish();
    }
}
