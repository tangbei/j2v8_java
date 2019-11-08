package com.toolmall.v8;

public class TestConsole {

    public void log(String str){
        System.out.println("我是log方法回调时 返回的:"+str);
    }

    public void error(String str){
        System.out.println("我是error方法回调时 返回的:"+str);
    }
}
