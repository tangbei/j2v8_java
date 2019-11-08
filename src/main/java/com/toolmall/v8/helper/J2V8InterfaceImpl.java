package com.toolmall.v8.helper;

/**
 * 所有使用j2v8解析json时，继承此类
 */
public abstract class J2V8InterfaceImpl implements J2V8Interface {

    public boolean isRelease;//是否释放v8

    public J2V8Bean j2V8Bean;//调用j2v8的准备数据

    public J2V8InterfaceImpl(){

    }

    public J2V8InterfaceImpl(J2V8Bean j2V8Bean) {
        this.j2V8Bean = j2V8Bean;
    }

    @Override
    public J2V8Bean getBean() {
        return j2V8Bean;
    }

    @Override
    public void setBean(J2V8Bean bean) {
        this.j2V8Bean = bean;
    }

    @Override
    public boolean isRelease() {
        return isRelease;
    }
}
