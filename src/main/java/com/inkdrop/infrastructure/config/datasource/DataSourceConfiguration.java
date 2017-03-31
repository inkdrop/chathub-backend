package com.inkdrop.infrastructure.config.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties(prefix = "params.datasource")
@Profile({"default", "cloud"})
@Slf4j
public class DataSourceConfiguration extends HikariConfig {

  @Bean
  public DataSource dataSource() throws SQLException {
    int cores = Runtime.getRuntime().availableProcessors();
    int poolSize = (cores * 2) + 1;
    log.info("Configuring Hikari pool size: " + poolSize);
    HikariDataSource ds = new HikariDataSource(this);
    ds.setMaximumPoolSize(poolSize);
    return ds;
  }
}
