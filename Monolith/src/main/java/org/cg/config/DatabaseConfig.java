package org.cg.config;

import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.cg.config.Profiles.Dev;
import org.cg.config.Profiles.Prod;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.cloud.aws.jdbc.config.annotation.EnableRdsInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.datatype.joda.JodaModule;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"org.cg.repository"})
@Prod
@PropertySource(value = { "classpath:HibernateConfig.properties" })
public class DatabaseConfig {
		
	
	@Autowired
	   private Environment env;
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		
		
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(false);
		adapter.setGenerateDdl(true);
		adapter.setDatabase(Database.MYSQL);

		Properties props = new Properties();
		props.setProperty("hibernate.format_sql", "true");
		emf.setDataSource(dataSource);
		emf.setJpaProperties(hibernateProperties());
		emf.setJpaVendorAdapter(adapter);
		emf.setPackagesToScan("org.cg.Model");
		return emf;
	}
	
	@Bean
	JpaTransactionManager transactionManager(EntityManagerFactory managerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		return txManager;
	}
	
	/*	@Bean
	   public LocalSessionFactoryBean sessionFactory(DataSource dataSource) throws IOException {
	      LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	      sessionFactory.setDataSource(dataSource);
	      sessionFactory.setPackagesToScan(new String[] { "org.cg.Model" });
	      sessionFactory.setHibernateProperties(hibernateProperties());
	     
	      sessionFactory.afterPropertiesSet();
	      return sessionFactory;
	   }
	/*	@Bean
	   @Autowired
	   public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
	      HibernateTransactionManager txManager = new HibernateTransactionManager();
	      txManager.setSessionFactory(sessionFactory);	 
	      return txManager;
	   }
	
	/* 
	 * Entity Manager factory initialization error,
	 * @Bean 
	  public EntityManagerFactory entityManagerFactory(DataSource dataSource) {

	    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	    vendorAdapter.setGenerateDdl(true);

	    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	    factory.setJpaVendorAdapter(vendorAdapter);
	    factory.setPackagesToScan("com.acme.domain");
	    factory.setDataSource(dataSource);
	    factory.afterPropertiesSet();

	    return factory.getObject();
	  }
	 */
	@Bean
	   public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
	      return new PersistenceExceptionTranslationPostProcessor();
	   }
	 
	 
	   Properties hibernateProperties() {
	      return new Properties() {
	         {
	            setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
	            setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
	            setProperty("hibernate.globally_quoted_identifiers", "true");
	         }
	      };
	   }
	
	public Jackson2ObjectMapperFactoryBean objectMapper() {
		Jackson2ObjectMapperFactoryBean objectMapper = new Jackson2ObjectMapperFactoryBean();
		objectMapper.setSimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		objectMapper.setIndentOutput(true);
		return objectMapper;
	}
	
	public MethodInvokingFactoryBean factoryBean() {
		MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
		factoryBean.setTargetClass(Jackson2ObjectMapperFactoryBean.class);
		factoryBean.setTargetMethod("registerModule");
		Object[] obj=new Object[] {JodaModule.class};
		
		factoryBean.setArguments(obj);
		return factoryBean;
		
	}
}
