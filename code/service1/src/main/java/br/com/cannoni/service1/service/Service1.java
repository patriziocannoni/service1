package br.com.cannoni.service1.service;

import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cannoni.service1.aspects.LogExecutionTime;

/**
 * @author patrizio
 * @since 08/01/019
 * 
 */
@Service
public class Service1 {

    private static final Logger LOGGER = LogManager.getLogger();
    
    @Autowired
    private ClientConfiguration igniteClientConfiguration;
    
    @LogExecutionTime
    public int count(final String cacheName) {
        int size = 0;
        
        try (IgniteClient ic = Ignition.startClient(igniteClientConfiguration)) {
            ClientCache<Long, String> stringCache = ic.cache(cacheName);
            size = stringCache.size();
            LOGGER.info(size);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        
        return size;
    }
    
}
