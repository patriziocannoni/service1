package br.com.cannoni.service1.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.ClientConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.springdata.repository.config.EnableIgniteRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableIgniteRepositories
public class SpringAppCfg {
    
    public static final String STRING_CACHE = "StringCache";

    @Bean
    public Ignite igniteInstance() {
        IgniteConfiguration cfg = new IgniteConfiguration();

        // Setting some custom name for the node.
        cfg.setIgniteInstanceName("service1-ignite");

        // Enabling peer-class loading feature.
        // cfg.setPeerClassLoadingEnabled(true);

        // Defining and creating a new cache to be used by Ignite Spring Data repository.
        CacheConfiguration<Long, String> cacheCfg = new CacheConfiguration<Long, String>(STRING_CACHE);

        // Setting SQL schema for the cache.
        cacheCfg.setIndexedTypes(Long.class, String.class);

        cfg.setCacheConfiguration(cacheCfg);

        return Ignition.start(cfg);
    }

    @Bean
    public ClientConfiguration igniteClientConfiguration() {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setAddresses("127.0.0.1:10800");
        return clientConfiguration;
    }

}
