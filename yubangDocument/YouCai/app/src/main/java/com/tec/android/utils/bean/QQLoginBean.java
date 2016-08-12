package com.tec.android.utils.bean;

/**
 * Created by andy on 15/8/12.
 */


public class QQLoginBean {
    private int ret;
    private String pay_token;
    private String pf;
    private int query_authority_cost;
    private long authority_cost;
    private String openid;
    private long expires_in;
    private String pfkey;
    private String msg;
    private String access_token;
    private int login_cost;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public String getPay_token() {
        return pay_token;
    }

    public void setPay_token(String pay_token) {
        this.pay_token = pay_token;
    }

    public int getLogin_cost() {
        return login_cost;
    }

    public void setLogin_cost(int login_cost) {
        this.login_cost = login_cost;
    }

    public String getAccess_token() {

        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPfkey() {

        return pfkey;
    }

    public void setPfkey(String pfkey) {
        this.pfkey = pfkey;
    }

    public long getExpires_in() {

        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getOpenid() {

        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public long getAuthority_cost() {

        return authority_cost;
    }

    public void setAuthority_cost(long authority_cost) {
        this.authority_cost = authority_cost;
    }

    public int getQuery_authority_cost() {

        return query_authority_cost;
    }

    public void setQuery_authority_cost(int query_authority_cost) {
        this.query_authority_cost = query_authority_cost;
    }
}
