package cn.ccc212.eduagent.config; // 建议放在 config 包下

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class VectorDataSourceConfig {

    // 配置数据源属性
    @Bean
    @ConfigurationProperties("spring.datasource.vector")
    public DataSourceProperties vectorDataSourceProperties() {
        return new DataSourceProperties();
    }

    // 创建 DataSource Bean
    @Bean
    public DataSource vectorDataSource() {
        return vectorDataSourceProperties().initializeDataSourceBuilder().build();
    }

    // 创建 JdbcTemplate Bean
    @Bean(name = "vectorJdbcTemplate") // 明确命名，提供给 PgVectorStore
    public JdbcTemplate vectorJdbcTemplate() {
        return new JdbcTemplate(vectorDataSource());
    }

}