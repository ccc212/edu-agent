package cn.ccc212.eduagent.config; // 建议放在 config 包下

import com.alibaba.druid.pool.DruidDataSource; // 引入 DruidDataSource
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
// import org.springframework.jdbc.core.JdbcTemplate; // 如果需要JdbcTemplate
// import org.springframework.jdbc.datasource.DataSourceTransactionManager; // 事务管理器

import javax.sql.DataSource;

@Configuration
public class MySQLDataSourceConfig {

    // 配置 Druid 数据源属性
    @Bean
    @Primary // 标记为主数据源，当没有明确指定数据源时，会注入这个
    @ConfigurationProperties("spring.datasource.mysql")
    public DruidDataSource mysqlDataSource() {
        return new DruidDataSource();
    }

}