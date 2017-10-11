package testconfigs;

import org.cg.config.ConfigurationService;
import org.cg.service.EncryptionService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(
                basePackageClasses = {ConfigurationService.class,EncryptionService.class}, 
                useDefaultFilters = false,
                includeFilters = {
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {ConfigurationService.class,EncryptionService.class})
                })
@PropertySource(value = { "classpath:store.properties" })
public class EncryptionTestConfig {

}
