package br.com.cannoni.service1.service;

import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cannoni.service1.aspects.LogExecutionTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Service1 {
    
    @Autowired
    private ClientConfiguration igniteClientConfiguration;
    
    @LogExecutionTime
    public int count(final String cacheName) {
        Integer size = 0;
        
        try (IgniteClient ic = Ignition.startClient(igniteClientConfiguration)) {
            ClientCache<Long, String> stringCache = ic.cache(cacheName);
            size = stringCache.size();
            log.info(size.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        
        return size;
    }
    
}
