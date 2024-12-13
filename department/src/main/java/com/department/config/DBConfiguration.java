package com.department.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jmx.support.RegistrationPolicy;

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class DBConfiguration {

    @Bean(name = "mysqlDbCollege")
    @ConfigurationProperties(prefix = "spring.collegedatasource")
    public DataSource mysqlDataCollegeSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "mysqlJdbcTemplateCollege")
    public JdbcTemplate mysqlJdbcTemplateCollege(@Qualifier("mysqlDbCollege") DataSource dsMySql) {
        return new JdbcTemplate(dsMySql);
    }

    @Bean(name = "mysqlDbStudent")
    @ConfigurationProperties(prefix = "spring.studentdatasource")
    public DataSource mysqlDataStudentSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mysqlJdbcTemplateStudent")
    public JdbcTemplate mysqlJdbcTemplateStudent(@Qualifier("mysqlDbStudent") DataSource dsMySql) {
        return new JdbcTemplate(dsMySql);
    }
}