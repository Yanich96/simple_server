package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
public class Main {
    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) throws Exception{


        try ( AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class)) {
            context.getBean(JettyConfiguration.class).join();
        }
    }
}