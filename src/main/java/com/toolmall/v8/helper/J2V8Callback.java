package com.toolmall.v8.helper;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;

public class J2V8Callback implements JavaCallback {

    public Object invoke(V8Object v8Object, V8Array v8Array) {
        String sss = "我是大地";
        if (null != v8Array.get(0)){
            String str = (String) v8Array.get(0);
            String[] array = str.split(",");
        }
        return sss;
    }
}
