package com.lenovo.wy.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author wy
 * @description
 * @date 2020/10/22
 */
@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class ApplicationConfig {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

//    @Autowired
//    private DataSource dataSource;
    @Autowired
    private SessionFactory sessionFactory;

    @Bean
    public DataSource dataSource(){
        return DataSourceBuilder.create()
                .url(jdbcUrl)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .build();
    }

    @Bean
    public LocalSessionFactoryBean localSessionFactoryBean(){
        Properties properties=new Properties();
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        LocalSessionFactoryBean lsb=new LocalSessionFactoryBean();
        lsb.setDataSource(dataSource());
//        lsb.setAnnotatedClasses(Student.class);这里demo可以这么写，但我们项目不可能就一个实体类，所以用下面的setPackagesToScan方法
        lsb.setPackagesToScan("com.lenovo.wy");
        lsb.setHibernateProperties(properties);

        return lsb;

    }



    @Bean
    public HibernateTemplate hibernateTemplate(){
        HibernateTemplate hibernateTemplate = new HibernateTemplate();
        hibernateTemplate.setSessionFactory(sessionFactory);
        return hibernateTemplate;
    }

    /*@Bean
    public HibernateTransactionManager hibernateTransactionManager(){
        HibernateTransactionManager ts = new HibernateTransactionManager();
        ts.setSessionFactory(sessionFactory);
        return ts;
    }*/

}
