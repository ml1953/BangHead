package com.ly.banghead.bangtools;

import android.util.Log;

import java.lang.reflect.Method;

import static com.ly.banghead.bangtools.BangScreenTools.TAG;


public class SystemProperties {
    private static Method getStringProperty;
    private static SystemProperties single;

    public static SystemProperties getSingle() {
        if (single == null) {
            synchronized (SystemProperties.class) {
                single = new SystemProperties();
            }
        }
        return single;
    }

    private SystemProperties() {
        getStringProperty = getMethod(getClass("android.os.SystemProperties"));
    }

    private Class getClass(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, e.getMessage());
            try {
                return ClassLoader.getSystemClassLoader().loadClass(name);
            } catch (ClassNotFoundException e1) {
                Log.e(TAG, e1.getMessage());
            }
        }
        return null;
    }

    private Method getMethod(Class clz) {
        Method method;
        try {
            method = clz != null ? clz.getMethod("get", String.class) : null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            method = null;
        }
        return method;
    }


    public final String get(String key) {
        if (key == null) return "";
        String value;
        try {
            value = (String) (getStringProperty != null ? getStringProperty.invoke(null, key) : null);
            if (value != null) {
                return value.trim();
            } else return "";
        } catch (Exception var4) {
            return "";
        }
    }


}
