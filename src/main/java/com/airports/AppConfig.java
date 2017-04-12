package com.airports;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@Configuration
@EnableAsync
@EnableScheduling
public class AppConfig {

    private static final Logger log = LogManager.getLogger(AppConfig.class);

    private String dataSourceURL = "jdbc:h2:~/airportsDb";
    private String dataSourceUser = "sa";
    private String dataSourcePassword = "";

    @Autowired
    private DataSource dataSource;

    @Bean
    public DataSource dataSource() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL(dataSourceURL);
        ds.setUser(dataSourceUser);
        ds.setPassword(dataSourcePassword);
        DatabasePopulatorUtils.execute(createDatabasePopulator(), ds);
        log.info("Datasource been initialized!");
        return ds;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    private DatabasePopulator createDatabasePopulator() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(new ClassPathResource("sql/create.sql"));
        return databasePopulator;
    }
}