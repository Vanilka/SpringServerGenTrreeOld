package gentree.server.manager.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Martyna SZYMKOWIAK on 16/10/2017.
 */
@Configuration
@EnableTransactionManagement
@PropertySource(value="classpath:db.properties")
public class DBConfig {


    @Autowired
    Environment env;

    private String db_driver;
    private String db_url;
    private String db_user;
    private String db_password;
    private String db_schema;

    private String hibernate_ddl_auto;
    private String hibernate_show_sql;
    private String hibernate_dialect;
    private String hibernate_use_sql;


    @PostConstruct
    private void init() {
        db_driver = env.getProperty(DBProperties.PARAM_DB_SOURCE_DRIVER_CLASS_NAME);
        db_url = env.getProperty(DBProperties.PARAM_DB_SOURCE_URL);
        db_user = env.getProperty(DBProperties.PARAM_DB_SOURCE_USER);
        db_password = env.getProperty(DBProperties.PARAM_DB_SOURCE_PASSWORD);
        db_schema = env.getProperty(DBProperties.PARAM_DB_SOURCE_SCHEMA);
        hibernate_dialect = env.getProperty(DBProperties.PARAM_HIBERNATE_DIALECT);
        hibernate_ddl_auto = env.getProperty(DBProperties.PARAM_HIBERNATE_HBM2DDL_AUTO);
        hibernate_show_sql = env.getProperty(DBProperties.PARAM_HIBERNATE_SHOW_SQL);
        hibernate_use_sql = env.getProperty(DBProperties.PARAM_HIBERNATE_USE_SQL_COMMENTS);

    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("gentree.server");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(db_driver);
        dataSource.setUrl(db_url);
        dataSource.setUsername(db_user);
        dataSource.setPassword(db_password);
        dataSource.setSchema(db_schema);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty(DBProperties.PARAM_HIBERNATE_HBM2DDL_AUTO, hibernate_ddl_auto);
        properties.setProperty(DBProperties.PARAM_HIBERNATE_DIALECT, hibernate_dialect);
        properties.setProperty(DBProperties.PARAM_HIBERNATE_SHOW_SQL, hibernate_show_sql);
        properties.setProperty(DBProperties.PARAM_HIBERNATE_USE_SQL_COMMENTS, hibernate_use_sql);
        return properties;
    }
}