package com.toolmall.v8.helper;

import com.eclipsesource.v8.V8;

import java.util.List;

public class J2V8Bean {

    /**
     * 读取类型
     */
    private String readType;
    /**
     * js脚本
     */
    private String jsScript;
    /**
     * js方法函数名
     */
    private String jsFunction;
    /**
     * 调取js方法时的上传参数集合
     */
    private List<Object> paramsList;
    /**
     * java回调方法名
     */
    private String javaFunction;
    /**
     * v8对象
     */
    private V8 v8;
    /**
     * 调用的java类
     */
    private Object objectClass;
    /**
     * 调用的java类中的函数名
     */
    private String objectFunction;
    /**
     * 调用的java类中函数类型
     */
    private Class<?>[] parameterTypes;

    public J2V8Bean(){}

    /**
     * 构造方法
     * @param jsFunction js方法函数名
     * @param paramsList 调取js方法时的上传参数集合
     */
    public J2V8Bean(String jsFunction, List<Object> paramsList) {
        this.jsFunction = jsFunction;
        this.paramsList = paramsList;
    }

    /**
     * 构造方法
     * @param jsScript js脚本
     * @param jsFunction js方法函数名
     * @param paramsList 调取js方法时的上传参数集合
     */
    public J2V8Bean(String jsScript, String jsFunction, List<Object> paramsList) {
        this.jsScript = jsScript;
        this.jsFunction = jsFunction;
        this.paramsList = paramsList;
    }

    /**
     * 构造方法
     * @param jsFunction js方法函数名
     * @param paramsList 调取js方法时的上传参数集合
     * @param javaFunction java回调方法名
     */
    public J2V8Bean(String jsFunction, List<Object> paramsList, String javaFunction) {
        this.jsFunction = jsFunction;
        this.paramsList = paramsList;
        this.javaFunction = javaFunction;
    }

    /**
     * 构造方法
     * @param jsScript js脚本
     * @param jsFunction js方法函数名
     * @param paramsList 调取js方法时的上传参数集合
     * @param javaFunction java回调方法名
     */
    public J2V8Bean(String jsScript, String jsFunction, List<Object> paramsList, String javaFunction) {
        this.jsScript = jsScript;
        this.jsFunction = jsFunction;
        this.paramsList = paramsList;
        this.javaFunction = javaFunction;
    }

    /**
     * 反射java类中的函数所用到的数据集
     * @param jsScript js脚本
     * @param jsFunction js方法函数名
     * @param paramsList 调取js方法时的上传参数集合
     * @param javaFunction java回调方法名
     * @param objectClass java类
     * @param objectFunction java类中的函数名
     * @param parameterTypes java类中的函数类型
     */
    public J2V8Bean(String jsScript, String jsFunction, List<Object> paramsList, String javaFunction, Object objectClass,String objectFunction, Class<?>[] parameterTypes) {
        this.jsScript = jsScript;
        this.jsFunction = jsFunction;
        this.paramsList = paramsList;
        this.javaFunction = javaFunction;
        this.objectClass = objectClass;
        this.objectFunction = objectFunction;
        this.parameterTypes = parameterTypes;
    }

    public String getJsScript() {
        return jsScript;
    }

    public void setJsScript(String jsScript) {
        this.jsScript = jsScript;
    }

    public String getJsFunction() {
        return jsFunction;
    }

    public void setJsFunction(String jsFunction) {
        this.jsFunction = jsFunction;
    }

    public List<Object> getParamsList() {
        return paramsList;
    }

    public void setParamsList(List<Object> paramsList) {
        this.paramsList = paramsList;
    }

    public String getJavaFunction() {
        return javaFunction;
    }

    public void setJavaFunction(String javaFunction) {
        this.javaFunction = javaFunction;
    }

    public String getReadType() {
        return readType;
    }

    public void setReadType(String readType) {
        this.readType = readType;
    }

    public V8 getV8() {
        return v8;
    }

    public void setV8(V8 v8) {
        this.v8 = v8;
    }

    public Object getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(Object objectClass) {
        this.objectClass = objectClass;
    }

    public String getObjectFunction() {
        return objectFunction;
    }

    public void setObjectFunction(String objectFunction) {
        this.objectFunction = objectFunction;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
}
