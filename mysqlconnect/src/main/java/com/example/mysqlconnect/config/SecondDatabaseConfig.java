package com.example.mysqlconnect.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.mysqlconnect.seconddb.repository",
        entityManagerFactoryRef = "secondEntityManager",
        transactionManagerRef = "secondTransactionManager"
)
@Slf4j
public class SecondDatabaseConfig {

    @Value("${db.driver-class-name}")
    String driverClassName;
    @Value("${db.second-url}")
    String url;
    @Value("${db.username}")
    String username;
    @Value("${db.password}")
    String password;



    @Bean
    public PlatformTransactionManager secondTransactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(secondEntityManager().getObject());

        return transactionManager;
    }

    /**
     * Entity를 관리하는 Manager
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean secondEntityManager(){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(secondDataSource());
        em.setPackagesToScan(new String[]{"com.example.mysqlconnect.seconddb.entity"}); // Entity Scan -> 경로를 통해 지정
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter()); // JPA 연결을 위한 부분

        // DDL Auto 설정
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");

        em.setJpaPropertyMap(properties);

        return em;
    }

    /**
     * 데이터 베이스 연결 관련
     */
    @Bean
    public DataSource secondDataSource(){
        return DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    @PostConstruct
    public void secondPrintDatabaseConfig() {
        log.info("Second Database Configuration:");
        log.info("Driver Class Name: {}", driverClassName);
        log.info("URL: {}", url);
        log.info("Username: {}", username);
        log.info("Password: {}", password);
    }

}
