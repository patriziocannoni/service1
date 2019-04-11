package br.com.cannoni.service1.controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cannoni.service1.model.Greeting;
import br.com.cannoni.service1.service.AsyncService;

@RestController
public class GreetingController {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String template = "Hello, %s!";

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private AsyncService asyncService;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        LOGGER.info("Invoking method async ... " + Thread.currentThread().getName());
        Future<Boolean> future = asyncService.doSomething();
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Call executed. " + Thread.currentThread().getName());
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

}
