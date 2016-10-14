package com.test.demo.server;


import com.alibaba.druid.pool.DruidDataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.test.demo.server.resource.mapper"})
@EnableConfigurationProperties({DemoProperties.class})
public class MybatisConfiguration {
    private static Logger logger = LoggerFactory.getLogger(MybatisConfiguration.class);

    @Autowired
    private DemoProperties demoProperties;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    @Bean
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setPassword(demoProperties.getPassword());
        druidDataSource.setUsername(demoProperties.getUsername());
        druidDataSource.setUrl(demoProperties.getUrl());
        druidDataSource.setDriverClassName(demoProperties.getDriver());
        druidDataSource.setMaxActive(demoProperties.getPoolMaximumActiveConnections());
        return druidDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory;
        try {
            SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
            sessionFactory.setDataSource(dataSource);
            sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                    .getResources("classpath:mapping/*.xml"));

            sqlSessionFactory = sessionFactory.getObject();
        } catch (Exception e) {
            logger.error("not install sessionFactory", e);
            throw new RuntimeException("fail to create session factory");
        }
        return sqlSessionFactory;
    }

    @Bean
    public DataSourceTransactionManager transaction() {
        return new DataSourceTransactionManager(dataSource);
    }
}
