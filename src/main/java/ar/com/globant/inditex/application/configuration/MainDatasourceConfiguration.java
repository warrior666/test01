package ar.com.globant.inditex.application.configuration;

import static java.util.Collections.singletonMap;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(basePackages = "ar.com.globant.inditex.model.repository", entityManagerFactoryRef = "mainEntityManagerFactory", transactionManagerRef = "mainTransactionManager")
public class MainDatasourceConfiguration {

    @Primary
    @Bean(name = "mainEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mainEntityManagerFactory(EntityManagerFactoryBuilder builder, Environment environment) {
        return builder
            .dataSource(mainDataSource())
            .packages("ar.com.globant.inditex.model.entity")
            .properties(singletonMap("hibernate.hbm2ddl.auto", environment.getProperty("db.main.hbm2ddl")))
            .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager mainTransactionManager(
        final @Qualifier("mainEntityManagerFactory") LocalContainerEntityManagerFactoryBean mainEntityManagerFactory) {
        EntityManagerFactory value = mainEntityManagerFactory.getObject();
        if(value != null) {
            return new JpaTransactionManager(value);
        }
        throw new NullPointerException("mainEntityManagerFactory cant be null");
    }

    @Bean
    @Primary
    public DataSource mainDataSource() {
        return mainDataSourceProperties()
            .initializeDataSourceBuilder()
            .type(HikariDataSource.class)
            .build();
    }

    @Bean
    @Primary
    @ConfigurationProperties("db.main")
    public DataSourceProperties mainDataSourceProperties() {
        return new DataSourceProperties();
    }
}
