package br.com.cannoni.service1.service;

import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author patrizio
 * @since 11/04/2019
 * 
 */
@Service
public class AsyncService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Async
    public Future<Boolean> doSomething() {
        LOGGER.info("Doing something async ... " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        LOGGER.info("Done");
        return null;
    }

}
