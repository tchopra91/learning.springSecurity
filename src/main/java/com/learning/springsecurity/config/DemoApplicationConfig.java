package com.learning.springsecurity.config;

import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.cpdsadapter.DriverAdapterCPDS;
import org.apache.commons.dbcp2.datasources.SharedPoolDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.learning.springsecurity")
@PropertySource({ "classpath:application.properties" })
public class DemoApplicationConfig {

    @Autowired
    private Environment env;

    private Logger logger = Logger.getLogger(getClass().getName());

    @Bean
    public DataSource myDataSource() {
        // for sanity's sake, let's log url and user ... just to make sure we are
        // reading the data
        logger.info("jdbc.url=" + env.getProperty("jdbc.url"));
        logger.info("jdbc.user=" + env.getProperty("jdbc.username"));

        DriverAdapterCPDS driverAdapterCPDS = new DriverAdapterCPDS();

        // set the jdbc driver
        try {
            driverAdapterCPDS.setDriver(env.getProperty("jdbc.driver"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // set database connection props
        driverAdapterCPDS.setUrl(env.getProperty("jdbc.url"));
        driverAdapterCPDS.setUser(env.getProperty("jdbc.username"));
        driverAdapterCPDS.setPassword(env.getProperty("jdbc.password"));

        // create connection pool
        SharedPoolDataSource poolDataSource = new SharedPoolDataSource();

        // set connection pool props
        poolDataSource.setConnectionPoolDataSource(driverAdapterCPDS);
        poolDataSource.setMaxTotal(Integer.parseInt(env.getProperty("connection.pool.maxPoolSize")));
        poolDataSource.setDefaultMinIdle(Integer.parseInt(env.getProperty("connection.pool.minPoolSize")));

        return poolDataSource;
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setPrefix("/WEB-INF/view");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }
}