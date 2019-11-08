package com.toolmall.v8;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.V8Runnable;
import com.eclipsesource.v8.utils.V8Thread;
import com.toolmall.v8.entity.TestBean;
import helper.*;
import jdk.internal.org.objectweb.asm.Handle;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

public class J2V8Controller extends J2V8InterfaceImpl {

    private static final String TAG = "Toolmall-V8:";

    private V8 v8;

    private Handler mHandler = new Handler(){

        @Override
        public boolean handleMessage(MessageContext context) {
            return false;
        }

        @Override
        public boolean handleFault(MessageContext context) {
            return false;
        }

        @Override
        public void close(MessageContext context) {

        }
    };


    public J2V8Controller(V8 v8) {
        this.v8 = v8;
    }

    public void test(){
        test3();
    }

    private void test1(){
        j2V8Bean = new J2V8Bean("StringFunction",null);
//        System.out.println("开始时间:"+System.currentTimeMillis());
        String json = J2V8Helper.engineJs(this);
        j2V8Bean.setJsFunction("overAllTest");
        System.out.println("第1条的结果:" +json);
        String overJson = J2V8Helper.engineJs(this);
        System.out.println("第2条的结果:" +overJson);
//        System.out.println("结束时间:"+System.currentTimeMillis());
    }

    /**
     * 获取js实体
     */
    private void objFunction(){
        /*for (int i = 0;i < 100; i++){
            Object obj = J2V8Helper.engineFunctionJs(J2V8Controller.this, "objFunction", null, "", new J2V8Callback());
            List list = getFiledsInfo(obj);
            V8Object v8 = (V8Object) obj;
            log(v8.getString("name"));
        }*/
    }

    private void test2(){
        j2V8Bean = new J2V8Bean("StringFunction",null);
        System.out.println("开始时间:"+System.currentTimeMillis());
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;i < 100;i++){
                    strFunction(j2V8Bean,i);
                }
            }
        }).start();

        System.out.println("结束时间:"+System.currentTimeMillis());
    }

    private synchronized void strFunction(J2V8Bean j2V8Bean,int i){
        j2V8Bean.setV8(v8);
        List<Object> params = new ArrayList<>();
        params.add(String.valueOf(i));
        j2V8Bean.setParamsList(params);
        String json = J2V8Helper.engineJs(J2V8Controller.this);
        System.out.println("遍历第" + i + "条的结果:" +json);
    }

    private void StringObjFunction(){
        TestConsole console = new TestConsole();
        j2V8Bean = new J2V8Bean("","jsLog",null,"",console,"log",new Class<?>[] { String.class });
        J2V8Helper.engineJs(this);
    }

    public void test3() {
        j2V8Bean = new J2V8Bean("StringFunction",null);
        V8Runnable runnable = new V8Runnable() {
            @Override
            public void run(V8 v8) {
                long initTime = System.currentTimeMillis();
                for (int i = 0;i < 2;i++){
                    List<Object> params = new ArrayList<>();
                    params.add(String.valueOf(i));
                    j2V8Bean.setParamsList(params);
                    String json = J2V8Helper.engineJs(J2V8Controller.this);
                    System.out.println("遍历第" + i + "条的结果:" +json);
                    if (i == 9999){
                        long outTime = System.currentTimeMillis();
                        System.out.println("总耗时为："+(outTime-initTime)+"ms");
                    }
                }
//                V8Object v8Console = new V8Object(v8);
                /*for (int i = 0;i < 100;i++){
                    TestConsole console = new TestConsole();
                    v8.add("console", v8Console);
                    v8Console.registerJavaMethod(console, "log", "log", new Class<?>[] { String.class });
                    v8Console.registerJavaMethod(console, "error", "error", new Class<?>[] { String.class });
                    v8Console.release();
                    v8.executeScript("console.log('hello, world');");
                    v8.release();
                }*/
            }
        };
        new J2V8Thread(v8,runnable).start();

//        new J2V8Thread(v8,runnable).start();
    }

    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     * */
    private List getFiledsInfo(Object o){
        Field[] fields=o.getClass().getDeclaredFields();
        String[] fieldNames=new String[fields.length];
        List list = new ArrayList();
        Map infoMap=null;
        for(int i=0;i<fields.length;i++){
            infoMap = new HashMap();
            infoMap.put("type", fields[i].getType().toString());
            infoMap.put("name", fields[i].getName());
            infoMap.put("value", getFieldValueByName(fields[i].getName(), o));
            list.add(infoMap);
        }
        return list;
    }

    /**
     * 根据属性名获取属性值
     * */
    private Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            log(e.getMessage());
            return null;
        }
    }


    /**
     * 获取map型数据
     * @return
     */
    private TestBean getTestBean(){
        TestBean v8 = new TestBean(10000,"我是测试实体");
        return v8;
    }


    private void log(String log){
        System.out.println(TAG+log);
    }

    private void functionLimit(){
        System.out.println("开始时间:"+System.currentTimeMillis());
        V8 runtime = V8.createV8Runtime();
        for (int i = 0; i < 200; i++){
            if (null != runtime){
                int result = runtime.executeIntegerScript(""
                        + "var hello = 'hello,';\n"
                        + "var world = 'world!';\n"
                        + "hello.concat(world).length;\n");
                System.out.println("我是第"+i+"条循环的结果:"+result);
            }
        }
        runtime.release();
        System.out.println("结束时间:"+System.currentTimeMillis());
    }

    private void functionThread(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                functionLimit();
            }
        });
        thread.start();
    }

    private String getDate(){
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        return dateFormat.format(date);
    }

    @Override
    public void Log(String log) {
        System.out.println(TAG+log);
    }
}
