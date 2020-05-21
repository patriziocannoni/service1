package br.com.cannoni.service1.controller;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cannoni.service1.SpringAppCfg;
import br.com.cannoni.service1.model.Greeting;
import br.com.cannoni.service1.service.AsyncService;
import br.com.cannoni.service1.service.Service1;

@RestController
@RequestMapping("service1")
public class Service1Controller {

    private static final Logger LOGGER = LogManager.getLogger();

    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
    private Service1 service1;

    @Autowired
    private AsyncService asyncService;

    @RequestMapping("/write")
    public Greeting write() {
        Long iterations = 0L;
        
        LOGGER.info("Invoking method async ... " + Thread.currentThread().getName());
        
        try {
            Future<Long> future = asyncService.writeAndInsertStrings();
            iterations = future.get();
            
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        
        LOGGER.info("Iterations: " + iterations + " - Call executed. " + Thread.currentThread().getName());
        
        return new Greeting(counter.incrementAndGet(), "");
    }
    
    @RequestMapping("/map-size")
    public Integer getMapSize(@RequestParam(value = "mapName", defaultValue = SpringAppCfg.STRING_CACHE, required = true) final String mapName) {
        return service1.count(mapName);
    }

}
