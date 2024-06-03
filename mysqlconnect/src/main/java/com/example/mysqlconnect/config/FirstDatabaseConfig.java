package com.example.mysqlconnect.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        basePackages = "com.example.mysqlconnect.firstdb.repository",
        entityManagerFactoryRef = "firstEntityManager",
        transactionManagerRef = "firstTransactionManager"
)
@Slf4j
public class FirstDatabaseConfig {


    @Value("${db.driver-class-name}")
    String driverClassName;
    @Value("${db.first-url}")
    String url;
    @Value("${db.username}")
    String username;
    @Value("${db.password}")
    String password;

    // DB 우선순위를 위한 Primary -> 안할 시 error 발생할 수 있음.
    @Primary
    @Bean
    public PlatformTransactionManager firstTransactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(firstEntityManager().getObject());

        return transactionManager;
    }

    /**
     * Entity를 관리하는 Manager
     */
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean firstEntityManager(){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(firstDataSource());
        em.setPackagesToScan(new String[]{"com.example.mysqlconnect.firstdb.entity"}); // Entity Scan -> 경로를 통해 지정
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
    @Primary
    @Bean
    public DataSource firstDataSource(){
        return DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    @PostConstruct
    public void firstPrintDatabaseConfig() {
        log.info("First Database Configuration:");
        log.info("Driver Class Name: {}", driverClassName);
        log.info("URL: {}", url);
        log.info("Username: {}", username);
        log.info("Password: {}", password);
    }

}
