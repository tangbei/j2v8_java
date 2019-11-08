package com.toolmall.v8.helper;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;
import org.apache.commons.lang3.StringUtils;
import java.io.InputStream;

public class J2V8Helper {

    private static final String TAG = "J2V8Helper----->";

    /**
     * 获取js解析后的数据
     * @param context interface对象
     * @return
     */
    public synchronized static String engineJs(J2V8Interface context){
        //判断如果有 传入反射类,则调取engineJsFun方法
        if (null != context.getBean() && null != context.getBean().getObjectClass()){
            return engineJsFun(context,null);
        }
        return engineJsStr(context,null);
    }

    /**
     * 获取js解析的数据
     * @param context interface对象
     * @param bean 实体
     * @return
     */
    public static String engineJs(J2V8Interface context,J2V8Bean bean){
        if (null != bean){
            context.setBean(bean);
        }
        if (null != bean.getObjectClass()){
            return engineJsFun(context,null);
        }
        return engineJsStr(context,null);
    }

    /**
     * 获取js解析后的数据
     * 可复用js脚本的方法环境等。比如获取更改后的全局变量等。
     * 可复用v8, 但必须在使用结束后设置isRelease = true,释放v8。
     * @param context 上下文
     * @param javaCallback java回调方法
     * @return js解析处理后的数据 string类型
     */
    public synchronized static String engineJsStr(J2V8Interface context, JavaCallback javaCallback) {
        if (null == context) return "";
        if (StringUtils.isEmpty(context.getBean().getJsFunction())) return "";
        String jsonJs = "";
        context.Log(TAG+"js处理开始");
        if (StringUtils.isEmpty(context.getBean().getJsScript())) {
            final InputStream INPUTSTREAM = context.getClass().getClassLoader().getResourceAsStream("j2v8/j2v8test.js");//获取js脚本的输入流
            try {
                jsonJs = J2V8Util.supplyAsync(INPUTSTREAM);//获取js处理后的字符串
                context.getBean().setJsScript(jsonJs);
                context.Log(TAG+"js读取本地js文件成功");
            } catch (Exception e) {
                e.printStackTrace();
                context.Log(TAG+"js读取本地js文件失败");
            }
        } else {
            jsonJs = context.getBean().getJsScript();
            context.Log(TAG+"js读取后台js脚本成功");
        }
        if (StringUtils.isEmpty(jsonJs)){
            context.Log(TAG+"无js脚本可读取");
            return "";
        }
        V8 v8;
        if (null == context.getBean().getV8()) {
            v8 = V8.createV8Runtime();//启动v8
            context.getBean().setV8(v8);
            context.Log(TAG+"v8已启动");
            //v8获取js的方法类
            v8.executeScript(jsonJs);
            context.Log(TAG+"js已注入到v8中");
        } else {
            v8 = context.getBean().getV8();
            if (v8.isReleased()){
                v8 = V8.createV8Runtime();
                context.getBean().setV8(v8);
                context.Log(TAG+"v8再次启动");
                //v8获取js的方法类
                v8.executeScript(jsonJs);
                context.Log(TAG+"js已注入到v8中");
            }else {
                context.Log(TAG+"v8已复用");
                //v8获取js的方法类
//                v8.executeScript(jsonJs);
//                context.Log(TAG+"js已注入到v8中");
            }
        }
        String response = "";//js解析结果
        //先判断v8是否存在，以及是否被释放
        if (null != javaCallback && StringUtils.isNotEmpty(context.getBean().getJavaFunction())) {
            //如果回调不为空，则注册回调
            v8.registerJavaMethod(javaCallback, context.getBean().getJavaFunction());
            context.Log(TAG + context.getBean().getJavaFunction() + "方法已注册到js");
        }
        if (null == context.getBean().getParamsList() || context.getBean().getParamsList().size() < 1) {
            response = J2V8Util.v8StringFunction(v8, context.getBean().getJsFunction(), null);
        } else {
            response = J2V8Util.v8StringFunction(v8, context.getBean().getJsFunction(), J2V8Util.getV8Array(v8, context.getBean().getParamsList()));
        }
        context.Log(TAG+"js处理完毕");
        context.Log(TAG+"js解析结果：" + response);
        if (context.isRelease()){
            v8.release();//使用完，释放v8
            context.Log(TAG+"v8已释放");
        }
        return response;
    }

    /**
     * 利用反射调取java类中的方法
     * 如果未用到反射，不要用此方法
     * @param context
     * @return
     */
    public static String engineJsFun(J2V8Interface context, JavaCallback javaCallback) {
        if (null == context) return "";
        if (null == context.getBean()) return "";
        if (null == context.getBean().getObjectClass()) return "";
        context.Log(TAG+"js处理java反射开始");
        String jsonJs = "";
        if (StringUtils.isEmpty(context.getBean().getJsScript())){
            final InputStream INPUTSTREAM =  context.getClass().getClassLoader().getResourceAsStream("j2v8/j2v8test.js");//获取js脚本的输入流
            try {
                jsonJs = J2V8Util.supplyAsync(INPUTSTREAM);//获取js处理后的字符串
                context.getBean().setJsScript(jsonJs);
                context.Log(TAG+"js读取本地js文件成功");
            } catch (Exception e) {
                e.printStackTrace();
                context.Log(TAG+"读取本地js文件失败");
            }
        }else {
            jsonJs = context.getBean().getJsScript();
            context.Log(TAG+"js读取后台js脚本成功");
        }
        if (StringUtils.isEmpty(jsonJs)){
            context.Log(TAG+"无js脚本可读取");
            return "";
        }
        V8 v8 = V8.createV8Runtime();
        context.Log(TAG+"v8已启动");
        V8Object v8Obj = new V8Object(v8);
        v8.add("console", v8Obj);
        v8Obj.registerJavaMethod(context.getBean().getObjectClass(), context.getBean().getObjectFunction(), context.getBean().getObjectFunction(), context.getBean().getParameterTypes());
        v8Obj.release();
        context.Log(TAG+"v8Object已释放");
        String response = "";//js解析结果
        //先判断v8是否存在，以及是否被释放
        if (!v8.isReleased()) {
            //v8获取js的方法类
            v8.executeScript(jsonJs);
            context.Log(TAG+"js已注入到v8中");
            if (null != javaCallback && StringUtils.isNotEmpty(context.getBean().getJavaFunction())) {
                //如果回调不为空，则注册回调
                v8.registerJavaMethod(javaCallback, context.getBean().getJavaFunction());
                context.Log(TAG + context.getBean().getJavaFunction() + "方法已注册到js");
            }
            if (null == context.getBean().getParamsList() || context.getBean().getParamsList().size() < 1) {
                response = J2V8Util.v8StringFunction(v8, context.getBean().getJsFunction(), null);
            } else {
                response = J2V8Util.v8StringFunction(v8, context.getBean().getJsFunction(), J2V8Util.getV8Array(v8, context.getBean().getParamsList()));
            }
            context.Log(TAG+"js处理完毕");
            context.Log(TAG+"js解析结果：" + response);
            v8.release();//使用完，释放v8
            context.Log(TAG+"v8已释放");
        }
        return response;
    }

}