package com.car.yubangapk.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by andy on 16/4/17.
 */
public class String2UTF8 {

    public static String getUTF8String(String xml) {
        // A StringBuffer Object
        StringBuffer sb = new StringBuffer();
        sb.append(xml);
        String xmString = "";
        String xmlUTF8="";
        try {
            xmString = new String(sb.toString().getBytes("UTF-8"));

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // return to String Formed
        return xmString;
    }
}
