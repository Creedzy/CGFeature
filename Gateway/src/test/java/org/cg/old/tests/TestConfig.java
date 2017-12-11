package org.cg.old.tests;

import javax.sql.DataSource;

import org.cg.config.DatabaseConfig;
import org.cg.config.Profiles.Dev;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Configuration
//@ComponentScan({"org.cg.model","org.cg.service",})
public class TestConfig {
	
	
	@Bean
	public DataSource dataSource(){
		return
			(new EmbeddedDatabaseBuilder())
			.setType(EmbeddedDatabaseType.HSQL)
			.addScript("classpath:schema.sql")
			.addScript("classpath:data.sql")
			.build();
	}	
}
