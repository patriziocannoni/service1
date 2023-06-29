package br.com.cannoni.service1.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.cannoni.service1.aspects.LogExecutionTime;
import br.com.cannoni.service1.config.SpringAppCfg;
import br.com.cannoni.service1.exceptions.WriteFileException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AsyncService {
    
    @Autowired
    private ClientConfiguration igniteClientConfiguration;
    
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    @Async
    @LogExecutionTime
    public Future<Long> writeAndInsertStrings() {
        long iterations = 100000;
        
        log.info("Write file async ... " + Thread.currentThread().getName());
        
        try {
            Path p = Paths.get("AsyncServiceOutput.txt");
            
            try (BufferedWriter bw = Files.newBufferedWriter(p);
                    IgniteClient ic = Ignition.startClient(igniteClientConfiguration)) {
                
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
            log.error(e.getMessage());
        }
        
        log.info("Done");
        
        CompletableFuture<Long> cf = new CompletableFuture<>();
        cf.complete(iterations);
        return cf;
    }

    private java.time.Instant generateNewRandomLong() {
        return Instant.now();
    }

}
