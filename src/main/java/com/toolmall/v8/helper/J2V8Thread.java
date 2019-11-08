package com.toolmall.v8.helper;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.utils.V8Runnable;

/**
 * 重写thread方法，
 * 目的是复用 v8
 */
public class J2V8Thread extends Thread {

    private final V8Runnable target;
    private V8 runtime;

    public J2V8Thread(V8 v8,V8Runnable target) {
        this.target = target;
        this.runtime = v8;
    }

    public void run() {
        //如果runtime为空，并且已经释放，则重新创建
        if (null == runtime || runtime.isReleased()){
            this.runtime = V8.createV8Runtime();
        }
        boolean var9 = false;

        try {
            var9 = true;
            this.target.run(this.runtime);
            var9 = false;
        } finally {
            if (var9) {
                synchronized(this) {
                    if (this.runtime.getLocker().hasLock()) {
                        this.runtime.release();
                        this.runtime = null;
                    }

                }
            }
        }

        synchronized(this) {
            if (this.runtime.getLocker().hasLock()) {
                //当
                if (!this.runtime.isReleased()){
                    this.runtime.release();
                }
                this.runtime = null;
            }

        }
    }


}
