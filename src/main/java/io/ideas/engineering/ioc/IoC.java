package io.ideas.engineering.ioc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IoC {

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(IoC.class.getPackageName());
    }

}
