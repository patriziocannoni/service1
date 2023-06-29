package br.com.cannoni.service1.controller;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cannoni.service1.config.SpringAppCfg;
import br.com.cannoni.service1.model.Greeting;
import br.com.cannoni.service1.service.AsyncService;
import br.com.cannoni.service1.service.Service1;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("service1")
public class Service1Controller {

    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
    private Service1 service1;

    @Autowired
    private AsyncService asyncService;

    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public Greeting write() {
        Long iterations = 0L;
        
        log.info("Invoking method async ... " + Thread.currentThread().getName());
        
        try {
            Future<Long> future = asyncService.writeAndInsertStrings();
            iterations = future.get();
            
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        
        log.info("Iterations: " + iterations + " - Call executed. " + Thread.currentThread().getName());
        
        return new Greeting(counter.incrementAndGet(), "");
    }
    
    @RequestMapping(value = "/map-size", method = RequestMethod.GET)
    public Integer getMapSize(@RequestParam(value = "mapName", defaultValue = SpringAppCfg.STRING_CACHE, required = true) final String mapName) {
        return service1.count(mapName);
    }

}
