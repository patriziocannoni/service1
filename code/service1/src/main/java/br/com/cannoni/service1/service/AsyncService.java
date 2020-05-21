package br.com.cannoni.service1.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.cannoni.service1.SpringAppCfg;
import br.com.cannoni.service1.aspects.LogExecutionTime;
import br.com.cannoni.service1.exceptions.WriteFileException;

/**
 * @author patrizio
 * @since 11/04/2019
 * 
 */
@Service
public class AsyncService {

    private static final Logger LOGGER = LogManager.getLogger();
    
    @Autowired
    private ClientConfiguration igniteClientConfiguration;

    @Async
    @LogExecutionTime
    public Future<Long> writeAndInsertStrings() {
        long iterations = 100000;
        
        LOGGER.info("Write file async ... " + Thread.currentThread().getName());
        
        try {
            Path p = Paths.get("AsyncServiceOutput.txt");
            
            try (BufferedWriter bw = Files.newBufferedWriter(p);
                    IgniteClient ic = Ignition.startClient(igniteClientConfiguration)) {
                
                Random random = new Random();
                String val = "";
                for (int i = 0; i < iterations; i++) {
                    val = i + " " + String.valueOf(System.currentTimeMillis() + generateNewRandomLong().toString() + " go ...");
                    bw.write(val);
                    bw.newLine();
                    
                    ClientCache<Long, String> stringCache = ic.cache(SpringAppCfg.STRING_CACHE);
                    stringCache.put(random.nextLong(), val);
                }
            }
        } catch (IOException e) {
            throw new WriteFileException(e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        
        LOGGER.info("Done");
        
        CompletableFuture<Long> cf = new CompletableFuture<>();
        cf.complete(iterations);
        return cf;
    }

    private java.time.Instant generateNewRandomLong() {
        return Instant.now();
    }

}
