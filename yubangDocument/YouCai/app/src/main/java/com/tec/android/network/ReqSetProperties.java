package com.tec.android.network;

import com.sales.common.until.CommonDefs;
import com.sales.vo.AppConfigReq;
import com.sales.vo.AppVersionReq;
import com.sales.vo.CashOutReq;
import com.sales.vo.CommitCashReq;
import com.sales.vo.ExitLoginReq;
import com.sales.vo.GetAboutInfoReq;
import com.sales.vo.GetAccountInfoReq;
import com.sales.vo.GetAddressListReq;
import com.sales.vo.GetBankListReq;
import com.sales.vo.GetCancelOrderPromptReq;
import com.sales.vo.GetCashAccountListReq;
import com.sales.vo.GetCashHistReq;
import com.sales.vo.GetCashInfoReq;
import com.sales.vo.GetContactInfoReq;
import com.sales.vo.GetFriendListReq;
import com.sales.vo.GetHistOrdersReq;
import com.sales.vo.GetMyCashReq;
import com.sales.vo.GetOrderReq;
import com.sales.vo.GetVerifyCodeReq;
import com.sales.vo.LoadGoodsDetailReq;
import com.sales.vo.LoadGoodsListReq;
import com.sales.vo.ModifyAddressInfoReq;
import com.sales.vo.ModifyOrderReq;
import com.sales.vo.PostLoginReq;
import com.sales.vo.PostLoginResp;
import com.sales.vo.PreLoginReq;
import com.sales.vo.SendSmsReq;
import com.sales.vo.ShareReq;
import com.sales.vo.UploadImgReq;
import com.sales.vo.VerifySmsReq;
import com.sales.vo.base.AddressInfo;
import com.sales.vo.base.CashInfo;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesMsgUtils;
import com.tec.android.utils.bean.QQLoginBean;

import java.io.File;

/**
 * Created by andy on 15/8/7.
 */
public class ReqSetProperties
{
    private Object mObject;
    public ReqSetProperties()
    {

    }
    public ReqSetProperties(Object object)
    {
        this.mObject = object;
    }

    public Object setProperties(Object object)
    {
        if (object instanceof AppConfigReq)
        {

        }
        else if (object instanceof GetOrderReq)
        {

        }
        return null;
    }

    /**
     * 获取订单请求的参数设置
     * @param getOrderReq 对象
     * @param orderId 订单号
     * @param format 格式, html还是json
     * @return json
     */
    public static String setGetOrderReqProperties(GetOrderReq getOrderReq,String orderId, String format)
    {
        getOrderReq.setSeq("111");
        getOrderReq.setWidth(10);
        getOrderReq.setHeight(10);
        getOrderReq.setAppversion("1.0");
        getOrderReq.setOs("test");
        getOrderReq.setOrderid(orderId);
        getOrderReq.setUserid("11");
        getOrderReq.setAuthorizedtoken("111");
        getOrderReq.setFormat(format);
        String reqJson = SalesMsgUtils.toJson(getOrderReq);
        return reqJson;
    }

    /**
     * 获取用户信息请求的参数设置
     * @param getAccountInfoReq 被设置对象
     * @param format 格式, html 还是json
     * @param postLoginResp
     * @return json
     */
    public static String setGetAccountInfoReqProperties(GetAccountInfoReq getAccountInfoReq, String format, PostLoginResp postLoginResp)
    {

        getAccountInfoReq.setWidth(10);
        getAccountInfoReq.setHeight(10);
        getAccountInfoReq.setAppversion("1.0");
        getAccountInfoReq.setOs("test");
        getAccountInfoReq.setFormat(format);

        //已经判断了没登陆
        if (postLoginResp == null)
        {
            getAccountInfoReq.setSeq("111");
            getAccountInfoReq.setUserid("11");
            getAccountInfoReq.setFuserid("11");
            getAccountInfoReq.setAuthorizedtoken("111");
        }
        else
        {
            //判断出来已经登陆了
            getAccountInfoReq.setSeq(postLoginResp.getSeq());
            getAccountInfoReq.setUserid(postLoginResp.getUserid());
            getAccountInfoReq.setFuserid(postLoginResp.getUserid());
            getAccountInfoReq.setAuthorizedtoken(postLoginResp.getAuthorizedtoken());
        }

        String reqJson = SalesMsgUtils.toJson(getAccountInfoReq);
        return reqJson;
    }

    /**
     * 某一个好友的个人中心界面的参数设置
     * @param getAccountInfoReq
     * @param format
     * @param postLoginResp
     * @param fuserid
     * @return
     */
    public static String setGetFriendAccountInfoReqProperties(GetAccountInfoReq getAccountInfoReq, String format, PostLoginResp postLoginResp, String fuserid) {
        getAccountInfoReq.setWidth(10);
        getAccountInfoReq.setHeight(10);
        getAccountInfoReq.setAppversion("1.0");
        getAccountInfoReq.setOs("test");
        getAccountInfoReq.setFormat(format);


        getAccountInfoReq.setSeq("111");
        getAccountInfoReq.setUserid("11");
        getAccountInfoReq.setFuserid(fuserid);
        getAccountInfoReq.setAuthorizedtoken("111");
        String reqJson = SalesMsgUtils.toJson(getAccountInfoReq);
        return reqJson;
    }

    /**
     * 获取app配置信息请求的参数设置
     * @param appConfigReq 被设置对象
     * @param format 格式, html还是json
     * @return json
     */
    public static String setAppConfigReqProperties(AppConfigReq appConfigReq, String format)
    {
        appConfigReq.setApptype("test");
        appConfigReq.setAppversion("1.0");
        appConfigReq.setDeviceid("d");
        appConfigReq.setHeight(10);
        appConfigReq.setWidth(10);
        appConfigReq.setOs("test");
        appConfigReq.setFormat(format);
        String reqJson = SalesMsgUtils.toJson(appConfigReq);
        return reqJson;
    }

    /**
     * 获取商品详细信息的请求 参数的设置
     * @param loadGoodsDetailReq 被设置对象
     * @param goodsId 商品id
     * @param format 格式, html还是json
     * @return json
     */
    public static String setLoadGoodsDetailReqProperties(LoadGoodsDetailReq loadGoodsDetailReq, String goodsId, String format)
    {
        loadGoodsDetailReq.setSeq("123");
        loadGoodsDetailReq.setWidth(10);
        loadGoodsDetailReq.setHeight(10);
        loadGoodsDetailReq.setApptype("test");
        loadGoodsDetailReq.setAppversion("1.0");
        loadGoodsDetailReq.setDeviceid("d");
        loadGoodsDetailReq.setOs("test");
        loadGoodsDetailReq.setFormat("html");
        loadGoodsDetailReq.setGoodsid(goodsId);
        String json = SalesMsgUtils.toJson(loadGoodsDetailReq);
        return json;
    }

    /**
     * 获取商品列表请求的参数设置
     * @param loadGoodsListReq 被设置的对象
     * @param format 格式, json还是html
     * @return json
     */
    public static String setLoadGoodsListReqProperties(LoadGoodsListReq loadGoodsListReq, String format)
    {
        loadGoodsListReq.setApptype("test");
        loadGoodsListReq.setAppversion("1.0");
        loadGoodsListReq.setDeviceid("d");
        loadGoodsListReq.setHeight(10);
        loadGoodsListReq.setWidth(10);
        loadGoodsListReq.setOs("test");
        loadGoodsListReq.setFormat(format);
        String reqJson = SalesMsgUtils.toJson(loadGoodsListReq);
        return reqJson;
    }


    //TODO
    /**
     * 获取关于退货须知请求的参数设置
     * @param getAboutInfoReq 被设置对象
     * @param format 格式  json html
     * @return json
     */
    public static String setGetAboutInfoReqProperties(GetAboutInfoReq getAboutInfoReq, String format)
    {
        getAboutInfoReq.setSeq("111");
        getAboutInfoReq.setDeviceid("11");
        getAboutInfoReq.setAppversion("1.0");
        getAboutInfoReq.setApptype("android");
        getAboutInfoReq.setOs("test");
        //TODO  设置不了 宽高
        String reqJson = SalesMsgUtils.toJson(getAboutInfoReq);
        return reqJson;
    }

    /**
     * 获取联系客服请求的参数设置
     * @param getContactInfoReq 被设置对象
     * @param format 格式  json html
     * @return json
     */
    public static String setGetContactInfoReqProperties(GetContactInfoReq getContactInfoReq, String format) {
        getContactInfoReq.setSeq("111");
        getContactInfoReq.setDeviceid("11");
        getContactInfoReq.setAppversion("1.0");
        getContactInfoReq.setApptype("android");
        getContactInfoReq.setOs("test");
        getContactInfoReq.setFormat(format);
        String reqJson = SalesMsgUtils.toJson(getContactInfoReq);
        return reqJson;
    }

    /**
     * 获取朋友列表的请求的参数设置
     * @param getFriendListReq 被设置对象
     * @param format 格式   json html
     * @return json
     */
    public static String setGetFriendListReqProperties(GetFriendListReq getFriendListReq, String format) {
        getFriendListReq.setSeq("111");
        getFriendListReq.setApptype("test");
        getFriendListReq.setAuthorizedtoken("111");
        getFriendListReq.setFormat(format);
        getFriendListReq.setFuserid("11");
        getFriendListReq.setOs("test");
        getFriendListReq.setUserid("11");
        getFriendListReq.setAppversion("1.0");
        getFriendListReq.setDeviceid("test");
        getFriendListReq.setWidth(10);
        getFriendListReq.setHeight(10);
        String reqJson = SalesMsgUtils.toJson(getFriendListReq);
        return reqJson;
    }

    /**
     * 获取历史订单请求的参数设置
     * @param getHistOrdersReq 被设置对象
     * @param format 格式 html json
     * @param status 状态
     * @return json
     */
    public static String setGetHistOrdersReqProperties(GetHistOrdersReq getHistOrdersReq, String format, String status) {
        getHistOrdersReq.setSeq("111");
        getHistOrdersReq.setApptype("test");
        getHistOrdersReq.setAuthorizedtoken("111");
        getHistOrdersReq.setFormat(format);
        getHistOrdersReq.setOs("test");
        getHistOrdersReq.setUserid("11");
        getHistOrdersReq.setAppversion("1.0");
        getHistOrdersReq.setDeviceid("test");
        getHistOrdersReq.setWidth(10);
        getHistOrdersReq.setHeight(10);
        getHistOrdersReq.setStatus(status);
        String reqJson = SalesMsgUtils.toJson(getHistOrdersReq);
        return reqJson;
    }

    /**
     * 获取地址列表请求的参数设置
     * @param getAddressListReq 被设置对象
     * @param format 格式 html json
     * @return json
     */
    public static String setGetAddressListReqProperties(GetAddressListReq getAddressListReq, String format) {
        getAddressListReq.setSeq("111");
        getAddressListReq.setUsrid("11");
        getAddressListReq.setApptype("test");
        getAddressListReq.setAuthorizedtoken("111");
        getAddressListReq.setWidth(10);
        getAddressListReq.setHeight(10);
        getAddressListReq.setFormat(format);
        getAddressListReq.setOs("test");
        getAddressListReq.setUserid("11");
        getAddressListReq.setAppversion("1.0");
        getAddressListReq.setDeviceid("test");
        getAddressListReq.setFormat(format);
        String reqJson = SalesMsgUtils.toJson(getAddressListReq);
        return reqJson;
    }

    /**
     * 获取我的提现请求  的参数设置
     * @param getMyCashReq 被设置对象
     * @param format 格式 json  html
     * @return json
     */
    public static String setGetMyCashReqProperties(GetMyCashReq getMyCashReq, String format) {
        getMyCashReq.setSeq("111");
        getMyCashReq.setApptype("test");
        getMyCashReq.setAuthorizedtoken("111");
        getMyCashReq.setFormat(format);
        getMyCashReq.setOs("test");
        getMyCashReq.setUserid("11");
        getMyCashReq.setAppversion("1.0");
        getMyCashReq.setDeviceid("test");
        String reqJson = SalesMsgUtils.toJson(getMyCashReq);
        return reqJson;
    }
    /**
     * 获取登陆请求  的参数设置
     * @param postLoginReq 被设置对象
     * @param format 格式 json  html
     * @param qqLoginBean
     * @return json
     */
    public static String setGetPostLoginReqProperties(PostLoginReq postLoginReq, String format, QQLoginBean qqLoginBean) {
        postLoginReq.setSeq("111");
        postLoginReq.setApptype("test");
        postLoginReq.setFormat(format);
        postLoginReq.setOs("test");
        postLoginReq.setAppversion("1.0");
        postLoginReq.setDeviceid("test");
        postLoginReq.setCode(qqLoginBean.getPay_token());
        postLoginReq.setCountry("cn");
        postLoginReq.setErrCode(qqLoginBean.getRet() + "");
        postLoginReq.setLang("ch");
        postLoginReq.setLoginType("qq");
        postLoginReq.setQqOpenId(qqLoginBean.getOpenid());
        String token  = qqLoginBean.getAccess_token();
        postLoginReq.setAccessToken(token);

        String reqJson = SalesMsgUtils.toJson(postLoginReq);
        return reqJson;
    }

    /**
     * 预登陆请求的参数设置
     * @param preLoginReq 被设置对象
     * @param format 格式
     * @return json
     */
    public static String setGetPreLoginReqProperties(PreLoginReq preLoginReq, String format) {
        preLoginReq.setSeq("111");
        preLoginReq.setApptype("test");
        preLoginReq.setFormat(format);
        preLoginReq.setOs("test");
        preLoginReq.setAppversion("1.0");
        preLoginReq.setDeviceid("test");
        preLoginReq.setFormat(format);
        preLoginReq.setMethod("qq");
        String reqJson = SalesMsgUtils.toJson(preLoginReq);
        return reqJson;
    }



    /**
     * 退出登陆请求的参数设置
     * @param exitLoginReq    被设置对象
     * @param format          格式
     * @return json
     */
    public static String setExitLoginReqProperties(ExitLoginReq exitLoginReq, String format) {
        exitLoginReq.setUserid("111");
        exitLoginReq.setFormat(format);
        exitLoginReq.setSeq("11");
        exitLoginReq.setApptype("test");
        exitLoginReq.setAppversion("1.0");
        exitLoginReq.setDeviceid("11");
        exitLoginReq.setOs("text");
        exitLoginReq.setMsgtype(MSG_TYPES.MSG_ExitLogin);
        exitLoginReq.setWidth(10);
        exitLoginReq.setHeight(10);
        exitLoginReq.setAuthorizedtoken("11111");
        String json = SalesMsgUtils.toJson(exitLoginReq);
        return json;
    }

    /**
     * 退货须知
     * @param getCancelOrderPromptReq
     * @param format
     * @return json
     */
    public static String setGetCancelOrderPromptReqProperties(GetCancelOrderPromptReq getCancelOrderPromptReq, String format) {
        getCancelOrderPromptReq.setOs("tst");
        getCancelOrderPromptReq.setDeviceid("11");
        getCancelOrderPromptReq.setAppversion("1.0");
        getCancelOrderPromptReq.setSeq("111");
        String reqJson = SalesMsgUtils.toJson(getCancelOrderPromptReq);
        return reqJson;
    }

    /**
     * 分享的参数设置  请求后台的参数设置
     * @param shareReq
     * @param format
     * @param goodsId
     * @return
     */
    public static String setShareReqProperties(ShareReq shareReq, String format, String goodsId) {

        shareReq.setSeq("111");
        shareReq.setAppversion("1.0");
        shareReq.setDeviceid("111");
        shareReq.setOs("test");
        shareReq.setFormat(format);
        shareReq.setUserid("11");
        shareReq.setApptype("11");
        shareReq.setAccessToken("111");
        shareReq.setShareType(CommonDefs.SHAREGOOD);
        shareReq.setGoodsid(goodsId);
        String reqJson =  SalesMsgUtils.toJson(shareReq);
        return reqJson;

    }

    /**
     * 分享的参数设置  请求后台的参数设置
     * @param shareReq
     * @param format
     * @param goodsId
     * @return
     */
    public static String setShareFriendReqProperties(ShareReq shareReq, String format, String goodsId) {

        shareReq.setSeq("111");
        shareReq.setAppversion("1.0");
        shareReq.setDeviceid("111");
        shareReq.setOs("test");
        shareReq.setFormat(format);
        shareReq.setUserid("11");
        shareReq.setApptype("11");
        shareReq.setAccessToken("111");
        shareReq.setShareType(CommonDefs.SHAREGOOD);
        shareReq.setGoodsid(goodsId);
        String reqJson =  SalesMsgUtils.toJson(shareReq);
        return reqJson;

    }


    /**
     * 修改地址
     * @param modifyAddressInfoReq
     * @param format
     * @param action add update
     * @return
     */
    public static String setModifyAddressReqProperties(ModifyAddressInfoReq modifyAddressInfoReq, String format, String action, AddressInfo addressInfo) {
        modifyAddressInfoReq.setAuthorizedtoken("111");
        modifyAddressInfoReq.setUserid("11");
        modifyAddressInfoReq.setFormat(format);
        modifyAddressInfoReq.setAction(action);
        modifyAddressInfoReq.setOs("test");
        modifyAddressInfoReq.setAddressinfo(addressInfo);
        modifyAddressInfoReq.setApptype("11");
        modifyAddressInfoReq.setAppversion("1.0");
        modifyAddressInfoReq.setDeviceid("11");
        modifyAddressInfoReq.setHeight(11);
        modifyAddressInfoReq.setWidth(11);
        modifyAddressInfoReq.setAction(action);
        String reqJson = SalesMsgUtils.toJson(modifyAddressInfoReq);
        return reqJson;
    }

    /**
     * 上传图片
     * @param uploadImgReq
     * @param format
     * @param imagefile
     * @param imagename
     * @return
     */
    public static String setUploadImgReqProperties(UploadImgReq uploadImgReq, String format,File imagefile,String imagename) {
        uploadImgReq.setDeviceid("111");
        uploadImgReq.setUserid("11");
        uploadImgReq.setFormat(format);
        uploadImgReq.setAuthorizedtoken("111");
        uploadImgReq.setImage_file(imagefile);
        uploadImgReq.setImgname(imagename);
        uploadImgReq.setMsgtype(MSG_TYPES.MSG_UPLOAD_IMG);
        String reqJson = SalesMsgUtils.toJson(uploadImgReq);
        return reqJson;
    }

    /**
     * 获取银行列表
     * @param getBankListReq
     * @param format
     * @return
     */
    public static String setGetBankListReqProperties(GetBankListReq getBankListReq, String format) {
        getBankListReq.setFormat(format);
        getBankListReq.setMsgtype(MSG_TYPES.MSG_GET_BANK_LIST);
        getBankListReq.setSeq("111");
        getBankListReq.setApptype("test");
        getBankListReq.setDeviceid("111111");
        getBankListReq.setOs("test");
        getBankListReq.setAppversion("1.0");
        String reqJson = SalesMsgUtils.toJson(getBankListReq);
        return reqJson;
    }


    /**
     * cashOut
     * @param cashOutReq
     * @param format
     * @param cashInfo
     * @return
     */
    public static String setGetCashOutReqProperties(CashOutReq cashOutReq, String format, CashInfo cashInfo) {

        cashOutReq.setAuthorizedtoken("111");
        cashOutReq.setFormat(format);
        cashOutReq.setMsgtype(MSG_TYPES.MSG_CASH_OUT);
        cashOutReq.setUserid("11");
        cashOutReq.setCashInfo(cashInfo);
        String reqJson = SalesMsgUtils.toJson(cashOutReq);
        return reqJson;
    }

    /**
     * 提交现金申请
     * @param commitCashReq
     * @param format
     * @param cashInfo
     * @return
     */
    public static String setCommitCashReqProperties(CommitCashReq commitCashReq, String format, CashInfo cashInfo) {
        commitCashReq.setCashInfo(cashInfo);
        commitCashReq.setUserid("11");
        commitCashReq.setAuthorizedtoken("111");
        commitCashReq.setFormat(format);
        commitCashReq.setMsgtype(MSG_TYPES.MSG_COMMIT_CASH);
        //TODO  这个code是什么
        commitCashReq.setVerifycode("111");
        String reqJson = SalesMsgUtils.toJson(commitCashReq);
        return reqJson;
    }

    /**
     * 发送短信请求的参数设置
     * @param sendSmsReq 被设置对象
     * @param format 格式
     * @return json
     */
    public static String setSendSmsReqProperties(SendSmsReq sendSmsReq, String format, String phone) {
        sendSmsReq.setSeq("111");
        sendSmsReq.setApptype("test");
        sendSmsReq.setFormat(format);
        sendSmsReq.setOs("test");
        sendSmsReq.setAppversion("1.0");
        sendSmsReq.setDeviceid("test");
        sendSmsReq.setFormat(format);
        sendSmsReq.setUserid("11");
        sendSmsReq.setAuthorizedtoken("111");
        sendSmsReq.setPhone(phone);
        sendSmsReq.setWidth(10);
        sendSmsReq.setHeight(10);
        sendSmsReq.setMsgtype(MSG_TYPES.MSG_SENDSMS);
        String reqJson = SalesMsgUtils.toJson(sendSmsReq);
        return reqJson;
    }

    /**
     * 验证手机验证码
     * @param verifySmsReq
     * @param format  格式
     * @param captcha 验证码
     * @return
     */
    public static String setVerifySmsReqProperties(VerifySmsReq verifySmsReq, String format, String captcha) {

        verifySmsReq.setAuthorizedtoken("111");
        verifySmsReq.setFormat(format);
        verifySmsReq.setMsgtype(MSG_TYPES.MSG_VERFITYSMS);
        verifySmsReq.setUserid("111");
        verifySmsReq.setCaptcha(captcha);
        verifySmsReq.setSeq("111");
        String reqJson = SalesMsgUtils.toJson(verifySmsReq);
        return reqJson;
    }

    /**
     * 获取已有账户信息  这个就是用户提现时， 可以选择之前用过的提现账户
     * @param getCashAccountListReq
     * @param format
     * @return
     */
    public static String setGetCashAccountListReqProperties(GetCashAccountListReq getCashAccountListReq, String format) {
        getCashAccountListReq.setFormat(format);
        getCashAccountListReq.setMsgtype(MSG_TYPES.MSG_GET_CASH_ACCOUNT_LIST);
        getCashAccountListReq.setUserid("11");
        getCashAccountListReq.setAuthorizedtoken("111");
        getCashAccountListReq.setSeq("111");
        getCashAccountListReq.setAppversion("1.0");
        getCashAccountListReq.setHeight(10);
        getCashAccountListReq.setWidth(10);
        getCashAccountListReq.setOs("test");
        String reqJson = SalesMsgUtils.toJson(getCashAccountListReq);
        return  reqJson;
    }

    /**
     * 提现历史记录
     * @param getCashHistReq
     * @param format
     * @param status
     * @return
     */
    public static String setGetCashHistReqProperties(GetCashHistReq getCashHistReq, String format, String status) {
        getCashHistReq.setAuthorizedtoken("111");
        getCashHistReq.setFormat(format);
        getCashHistReq.setMsgtype(MSG_TYPES.MSG_GET_CASH_HIST_LIST);
        getCashHistReq.setUserid("11");
        getCashHistReq.setPid(1);
        getCashHistReq.setSeq("111");
        getCashHistReq.setStatus(status);
        getCashHistReq.setAppversion("1.0");
        getCashHistReq.setOs("test");
        getCashHistReq.setWidth(10);
        getCashHistReq.setHeight(10);
        String reqJson = SalesMsgUtils.toJson(getCashHistReq);
        return reqJson;
    }

    /**
     * 个人中心我的体现列表里面  点击查看详情   去那道提现详情
     * @param getCashInfoReq
     * @param format
     * @param cashId
     * @return
     */
    public static String setGetCashInfoReqProperties(GetCashInfoReq getCashInfoReq, String format, String cashId) {
        getCashInfoReq.setAppversion("1.0");
        getCashInfoReq.setFormat(format);
        getCashInfoReq.setMsgtype(MSG_TYPES.MSG_GET_CASH_INFO);
        getCashInfoReq.setWidth(10);
        getCashInfoReq.setHeight(10);
        getCashInfoReq.setUserid("11");
        getCashInfoReq.setAuthorizedtoken("111");
        getCashInfoReq.setCashid(cashId);
        getCashInfoReq.setDeviceid("111");
        getCashInfoReq.setOs("test");
        getCashInfoReq.setSeq("111");
        String reqJson = SalesMsgUtils.toJson(getCashInfoReq);
        return reqJson;
    }

    /**
     * 获取手机验证码
     * @param getVerifyCodeReq 请求
     * @param format 格式
     * @param phoneNum 手机号码
     * @return json
     */
    public static String setGetVerifyCodeReqProperties(GetVerifyCodeReq getVerifyCodeReq, String format, String phoneNum) {
        getVerifyCodeReq.setFormat(format);
        getVerifyCodeReq.setMsgtype(MSG_TYPES.MSG_GET_VERIFY_CODE);
        getVerifyCodeReq.setAuthorizedtoken("111");
        getVerifyCodeReq.setOs("test");
        getVerifyCodeReq.setUserid("11");
        getVerifyCodeReq.setSeq("111");
        getVerifyCodeReq.setDeviceid("111111");
        getVerifyCodeReq.setWidth(10);
        getVerifyCodeReq.setHeight(10);
        getVerifyCodeReq.setAppversion("1.0");
        //TODO trigger 是什么意思
        getVerifyCodeReq.setTrigger(phoneNum);
        String reqJson = SalesMsgUtils.toJson(getVerifyCodeReq);
        return reqJson;
    }

    /**
     * 修改订单
     * @param modifyOrderReq
     * @param action
     *@param format
     * @param orderid
     * @param addressInfo    @return
     */
    public static String setModifyOrderReqProperties(ModifyOrderReq modifyOrderReq, String format, String orderid, String action, AddressInfo addressInfo) {
        modifyOrderReq.setMsgtype(MSG_TYPES.MSG_MODIFY_ORDER);
        modifyOrderReq.setAuthorizedtoken("111");
        modifyOrderReq.setOrderid(orderid);
        modifyOrderReq.setUserid("11");
        modifyOrderReq.setAction(action);

        modifyOrderReq.setSeq("111");
        modifyOrderReq.setAppversion("1.0");
        modifyOrderReq.setHeight(10);
        modifyOrderReq.setWidth(10);
        modifyOrderReq.setAddressinfo(addressInfo);
        modifyOrderReq.setFormat(format);
//        modifyOrderReq.set
        String reqJson = SalesMsgUtils.toJson(modifyOrderReq);
        return reqJson;
    }

    /**
     * 确认收货的修改订单
     * @param modifyOrderReq
     * @param html
     * @param orderid
     * @param action
     * @return
     */
    public static String setModifyOrderReceiveReqProperties(ModifyOrderReq modifyOrderReq, String html, String orderid, String action) {

        modifyOrderReq.setMsgtype(MSG_TYPES.MSG_MODIFY_ORDER);
        modifyOrderReq.setAuthorizedtoken("111");
        modifyOrderReq.setOrderid(orderid);
        modifyOrderReq.setUserid("11");
        modifyOrderReq.setAction(action);

        modifyOrderReq.setSeq("111");
        modifyOrderReq.setAppversion("1.0");
        modifyOrderReq.setHeight(10);
        modifyOrderReq.setWidth(10);
        modifyOrderReq.setFormat(html);
        modifyOrderReq.setAppversion("1.0");
        String reqJson = SalesMsgUtils.toJson(modifyOrderReq);
        return reqJson;
    }


    /**
     * 检查app版本信息
     * @param appVersionReq
     * @param format
     * @param currentAppversion
     * @return
     */
    public static String setCheckAppVersionReqProperties(AppVersionReq appVersionReq, String format, String currentAppversion) {
        appVersionReq.setSeq("111");
        appVersionReq.setFormat(format);
        appVersionReq.setMsgtype(MSG_TYPES.MSG_APP_VERSION);
        appVersionReq.setHeight(10);
        appVersionReq.setWidth(10);
        appVersionReq.setAppversion(currentAppversion);
        appVersionReq.setApptype("test");
        appVersionReq.setDeviceid("111");
        appVersionReq.setOs("android");
        String reqJson = SalesMsgUtils.toJson(appVersionReq);
        return reqJson;
    }


}
