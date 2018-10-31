package br.zup.batch;

import br.zup.model.Greeting;
import br.zup.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class GreetingBatchBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GreetingService greetingService;

    @Scheduled(
            cron = "0,30 * * * * *"
    )
    public void cronJob(){
        logger.info("> cronJob");
        Collection<Greeting> greetings = greetingService.findAll();
        logger.info("There are {} greeting in the data store.",greetings.size());

    }


    @Scheduled(
            initialDelay = 5000,
            fixedDelay = 15000
    )
    public void fixedDelayJobWithInitialDelay(){
        logger.info("> fixedDelayJobWithInitialDelay");

        long pause = 5000;
        long start = System.currentTimeMillis();
        do{
            if(start + pause < System.currentTimeMillis())
                break;
        }while (true);
        logger.info("Processing time was {} seconds.", pause / 1000);
        logger.info("< fixedDelayJobWithInitialDelay");


    }

}
