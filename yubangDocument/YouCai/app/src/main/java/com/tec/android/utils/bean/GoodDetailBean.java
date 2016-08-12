package com.tec.android.utils.bean;

import com.tec.android.db.DetailGoods;

/**
 * Created by andy on 15/8/4.
 */
public class GoodDetailBean {
    private DetailGoods detailGoods;
    private String returncode;
    private String errmsg;
    private String msgtype;
    private String seq;

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getSeq() {
        return seq;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public String getReturncode() {
        return returncode;
    }

    public void setReturncode(String returncode) {
        this.returncode = returncode;
    }

    public DetailGoods getDetailGoods() {
        return detailGoods;
    }

    public void setDetailGoods(DetailGoods detailGoods) {
        this.detailGoods = detailGoods;
    }
}
