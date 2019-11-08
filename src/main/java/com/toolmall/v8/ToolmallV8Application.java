package com.toolmall.v8;


import com.eclipsesource.v8.V8;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToolmallV8Application {

    public static void main(String[] args) {
       SpringApplication.run(ToolmallV8Application.class, args);

        /*System.out.println("hello j2v8！");
        V8 v8 = V8.createV8Runtime();
        J2V8Controller controller = new J2V8Controller(v8);
        controller.test();*/
    }
}
