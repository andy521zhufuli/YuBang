package com.car.yubangapk.json;

import java.io.Serializable;

/**
 * Created by andy on 16/4/7.
 * 原则上希望所有的Bean都要继承这个的  但是好像之前的没考虑到这个设计
 */
public class Base implements Serializable{
    private boolean hasData;

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }
}
