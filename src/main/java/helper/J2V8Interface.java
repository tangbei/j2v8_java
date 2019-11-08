package helper;

public interface J2V8Interface {

    void Log(String log);

    /**
     * 是否释放v8,如果已经释放、同时再次调用时，会再次生成。
     * true为释放、false为再次复用
     * @return
     */
    boolean isRelease();

    /**
     * v8交互实体
     * @return
     */
    J2V8Bean getBean();

    void setBean(J2V8Bean bean);
}
