package com.inkdrop.infrastructure.config.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties(prefix = "params.datasource")
@Profile({"default", "docker"})
public class DataSourceConfiguration extends HikariConfig {

  @Bean
  public DataSource dataSource() throws SQLException {
    return new HikariDataSource(this);
  }
}
