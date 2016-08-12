
package com.common.log;


public class Util
{

    public static String formatStrByLen(String str, int len)
    {   
        if (str == null)
            str = "";
        if (len == 0)
            return "";
        StringBuffer resultStr = new StringBuffer();
        resultStr.append(str);
        int str_leng = lengthOfHZ(str);
        if (str_leng == len)
        {
            return resultStr.toString();
        }
        if (str_leng < len)
        {

        }
        else
        {
            String result = formatByLen(str, len, "");
            return result;
        }
        return resultStr.toString();
    }


    public static String formatStr(String str)
    {

        if (str == null)
            return "";
        return str;
    }

    
    public static int lengthOfHZ(String aStr) {

        char c;
        int length = 0;
        for (int i = 0; i < aStr.length(); i++) {
            c = aStr.charAt(i);
            if (c >= 127) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length;
    }
    
    public static String formatByLen(String aStr, int len, String endStr) {

        char c;
        int length = len;
        int aStrLen = aStr.length();
        StringBuffer resultStr = new StringBuffer();
        int i;
        for (i = 0; i < aStrLen; i++) {
            c = aStr.charAt(i);
            if (c >= 127) {
                length -= 2;
            } else {
                length -= 1;
            }
            if (length >= 0) {
                resultStr.append(c);
            } else {
                break;
            }
        }
        if (i < aStrLen) {
            resultStr.append(endStr);
        }
        return resultStr.toString();
    }
}
