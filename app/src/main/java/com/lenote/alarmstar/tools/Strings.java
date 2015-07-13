package com.lenote.alarmstar.tools;

/**
 * Created by shanerle on 2015/7/13.
 */
public class Strings {
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.length() == 0 || "null".equals(string.toLowerCase().trim());
    }

    public static String emptyToNull(String string) {
        return isNullOrEmpty(string) ? null : string;
    }

    public static String nullToEmpty(String string) {
        return (string == null) ? "" : string;
    }
}
