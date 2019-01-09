package br.com.cannoni.service1;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.springdata.repository.config.EnableIgniteRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.cannoni.service1.model.Customer;

/**
 * @author patrizio
 * @since 09/01/019
 * 
 */
@Configuration
@EnableIgniteRepositories
public class SpringDataConfig {

	@Bean
	public Ignite igniteInstance() {
		IgniteConfiguration config = new IgniteConfiguration();

		CacheConfiguration cache = new CacheConfiguration("service1-cache");
		cache.setIndexedTypes(Integer.class, Customer.class);

		config.setCacheConfiguration(cache);
		return Ignition.start(config);
	}
}
