package com.example.multidatasource.dbconfig;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Component
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "studentEntityManagerFactory",
        transactionManagerRef = "studentTransactionManager", basePackages = {"com.example.multidatasource.student.repository"})
public class StudentDBConfig {

    @Primary
    @Bean(name = "studentDataSource")
    @ConfigurationProperties(prefix = "spring.student.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "studentEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean studentEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("studentDataSource") DataSource dataSource) {

        Map<String, String> prop = new HashMap<>();
        prop.put("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        prop.put("spring.jpa.hibernate.ddl-auto", "update");
        prop.put("spring.jpa.show-sql", "true");

        return builder.dataSource(dataSource)
                .packages("com.example.multidatasource.student.entity")
                .persistenceUnit("Student")
                .properties(prop)
                .build();
    }
    
    @Primary
    @Bean(name = "studentTransactionManager")
    public PlatformTransactionManager studentTransactionManager(
            @Qualifier("studentEntityManagerFactory") EntityManagerFactory studentEntityManagerFactory) {
        return new JpaTransactionManager(studentEntityManagerFactory);
    }

}
