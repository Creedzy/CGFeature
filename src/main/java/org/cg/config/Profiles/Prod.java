package org.cg.config.Profiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.cloud.aws.jdbc.config.annotation.EnableRdsInstance;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Profile("prod")
@PropertySource(value = { "classpath:HibernateConfig.properties" })
@EnableRdsInstance(dbInstanceIdentifier = "west-1-mcdb",password = "c3r3pr0c", readReplicaSupport = true)
public @interface Prod {

}
