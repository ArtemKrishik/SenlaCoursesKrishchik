package com.github.krishchik.whowithme.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class JdbcConfiguration {

    @Value("${database.url}")
    private String databaseUrl;
    @Value("${database.login}")
    private String databaseLogin;
    @Value("${database.password}")
    private String databasePassword;

    @Bean(destroyMethod = "close")
    public Connection connection() throws SQLException {
       return DriverManager.getConnection(databaseUrl,databaseLogin, databasePassword);
    }

}
