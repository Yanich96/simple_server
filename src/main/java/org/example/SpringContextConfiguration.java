package org.example;

import org.example.database.DatabaseRelationImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"/application.properties"})
public class SpringContextConfiguration {
    @Value("${database.driver}")
    String driver;
    @Value("${database.connection}")
    String connection;
    @Value("${database.user}")
    String user;
    @Value("${database.password}")
    String password;

    @Bean
    DatabaseRelationImpl.Configuration databaseConfiguration() {
        return new DatabaseRelationImpl.Configuration(
                driver,
                connection,
                user,
                password
        );
    }
}
