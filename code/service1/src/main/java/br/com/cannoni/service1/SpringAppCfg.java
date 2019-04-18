package br.com.cannoni.service1;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.log4j2.Log4J2Logger;
import org.apache.ignite.springdata.repository.config.EnableIgniteRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author patrizio
 * @since 17/04/2019
 * 
 */
@Configuration
@EnableIgniteRepositories
public class SpringAppCfg {

    /**
     * Creating Apache Ignite instance bean. A bean will be passed to
     * IgniteRepositoryFactoryBean to initialize all Ignite based Spring Data *
     * repositories and connect to a cluster.
     * @throws IgniteCheckedException 
     */
    @Bean
    public Ignite igniteInstance() throws IgniteCheckedException {
        IgniteConfiguration cfg = new IgniteConfiguration();

        // Setting some custom name for the node.
        cfg.setIgniteInstanceName("service1DataNode");

        // Enabling peer-class loading feature.
        cfg.setPeerClassLoadingEnabled(true);
        
        cfg.setGridLogger(new Log4J2Logger("log4j2-ignite.xml"));

        // Defining and creating a new cache to be used by Ignite Spring Data
        // repository.
        CacheConfiguration ccfg = new CacheConfiguration("StringCache");

        // Setting SQL schema for the cache.
        ccfg.setIndexedTypes(Long.class, String.class);

        cfg.setCacheConfiguration(ccfg);

        return Ignition.start(cfg);
    }

}