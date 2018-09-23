package io.pivotal.datafow.samples.tasksample.config;

import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("cloud")
public class DataSourceConfiguration extends AbstractCloudConfig {

    @Bean(name = "taskDataSource")
    public DataSource taskDataSource() {
        return connectionFactory().dataSource("relational-cd75dcb0-9f85-4325-a103-1ddde18515b0");
    }


    /// @Primary will let Spring Data JPA autoconfig use the following bean definitions

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        return connectionFactory().dataSource("mymysql");
    }

}