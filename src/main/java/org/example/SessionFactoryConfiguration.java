package org.example;


import jakarta.annotation.PreDestroy;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionFactoryConfiguration {

    @PreDestroy
    void destroy()
    {
        sessionFactory().close();
    }
    @Bean
    SessionFactory sessionFactory()
    {
        return new org.hibernate.cfg.Configuration()
                .addAnnotatedClass(UserProfile.class)
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }
}
