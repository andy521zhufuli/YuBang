package com.tec.android.utils.bean;

/**
 * 手机系统信息,
 * 宽 高 状态栏高度 手机系统类型 手机IMEI
 */
public class SystemInfoBean
{
    private int         screenWidth;
    private int         screenHeight;
    private int         screenStatusBarHeight;
    private String      osType;
    private String      IMEI;

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public int getScreenStatusBarHeight() {
        return screenStatusBarHeight;
    }

    public void setScreenStatusBarHeight(int screenStatusBarHeight) {
        this.screenStatusBarHeight = screenStatusBarHeight;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }
}
