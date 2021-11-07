package com.geek.week7.cofig;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author 吴振
 * @since 2021/11/8 上午2:35
 */

@Configuration
@EnableConfigurationProperties(DataSourceProperty.class)
@ConditionalOnProperty(prefix = "database.config", value = "enabled", havingValue = "true")
public class DataSourceAutoConfig implements InitializingBean, ApplicationContextAware {


    private final Class<? extends DataSource> type = HikariDataSource.class;

    private ConfigurableApplicationContext applicationContext;

    private final DataSourceProperty databaseProperties;

    private BeanFactory beanFactory;

    public DataSourceAutoConfig(DataSourceProperty databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    //配置一个写数据源
    @Bean
    public HikariDataSource writeDataSource() {
        DataSourceProperty.WriteableProperties writeableProperties = databaseProperties.getWriteable();
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(writeableProperties.getJdbcUrl());
        config.setDriverClassName(this.databaseProperties.getDriverClassName());
        config.setUsername(writeableProperties.getUsername());
        config.setPassword(writeableProperties.getPassword());
        config.setMaximumPoolSize(writeableProperties.getMaxPoolSize());
        config.setPoolName(DataSourceConfig.MASTER_KEY);
        return new HikariDataSource(config);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof ConfigurableApplicationContext)
            this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //注册多个读数据源
        if (CollectionUtils.isEmpty(this.databaseProperties.getReadOnly()))
            return;

        int index = 0;
        String prefex = "slave";
        for (DataSourceProperty.ReadOnlyProperties properties : this.databaseProperties.getReadOnly()) {
            String poolName = String.format("%s-%d", prefex, index);
            HikariDataSource dataSource = buildOneReadOnlyDataSource(poolName, properties);
            this.applicationContext.getBeanFactory().registerSingleton(poolName, dataSource);
        }

    }

    @Bean
    @ConditionalOnMissingBean
    @Order(value = Ordered.LOWEST_PRECEDENCE - 5)
    public DataSourceConfig dataSourceContainer(@Autowired List<HikariDataSource> dataSourceList) {
        DataSourceConfig dataSourceContainer = new DataSourceConfig();
        if (CollectionUtils.isEmpty(dataSourceList))
            throw new IllegalStateException("no datasource found");

        for (HikariDataSource dataSource : dataSourceList) {
            if (dataSource.isReadOnly())
                dataSourceContainer.registerReadOnly(dataSource);
            else dataSourceContainer.registerWriteable(dataSource);
        }

        dataSourceContainer.apply();

        return dataSourceContainer;
    }

    @Bean
    @Primary
    public JdbcTemplate jdbcTemplate(DataSourceConfig dataSourceContainer) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSourceContainer);
        return jdbcTemplate;
    }



    private HikariDataSource buildOneReadOnlyDataSource(String poolName, DataSourceProperty.ReadOnlyProperties properties) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getJdbcUrl());
        config.setDriverClassName(this.databaseProperties.getDriverClassName());
        config.setUsername(properties.getUsername());
        config.setPassword(properties.getPassword());
        config.setMaximumPoolSize(properties.getMaxPoolSize());
        config.setReadOnly(true);
        config.setPoolName(poolName);
        return new HikariDataSource(config);
    }
}
