package br.zup.batch;

import br.zup.model.Greeting;
import br.zup.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Profile("batch")
@Component
public class GreetingBatchBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GreetingService greetingService;

    @Scheduled(
            cron = "${batch.greeting.cron}"
    )
    public void cronJob(){
        logger.info("> cronJob");
        Collection<Greeting> greetings = greetingService.findAll();
        logger.info("There are {} greeting in the data store.",greetings.size());

    }


    @Scheduled(
            initialDelayString = "${batch.greeting.initialDelay}",
            fixedDelayString = "${batch.greeting.fixedDelay}"
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
