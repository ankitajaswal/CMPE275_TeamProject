package cmpe275.lab2.configuration;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Provides transaction configuration information.
 */
@Configuration
@EnableTransactionManagement
public class TransactionConfig {

    @Autowired
    private DataSource dataSource;

    /**
     * Links transactionManager bean with default dataSource
     * 
     * @return PlatformTransactionManager the transaction manager to use for all transactional APIs
     */
    @Bean(name = "transactionManager")
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
