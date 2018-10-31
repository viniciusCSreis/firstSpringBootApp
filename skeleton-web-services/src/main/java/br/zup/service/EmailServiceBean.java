package br.zup.service;

import br.zup.model.Greeting;
import br.zup.util.AsyncResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class EmailServiceBean implements EmailService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Boolean send(Greeting greeting) {
        logger.info("> send");
        Boolean success = Boolean.FALSE;
        // Simulate method execution time
        long pause = 5000;
        try {
            Thread.sleep(pause);
        } catch (Exception e) {
            // do nothing
        }
        logger.info("Processing time was {} seconds.", pause / 1000);
        success = Boolean.TRUE;
        logger.info("< send");
        return success;
    }

    @Async
    @Override
    public void sendAsync(Greeting greeting) {
        logger.info("> sendAsync");

        try {
            send(greeting);
        }catch (Exception e){
            logger.warn("Exception caught sending asynchronous mail.",e);
        }

        logger.info("< sendAsync");
    }

    @Async
    @Override
    public Future<Boolean> sendAsyncWithResult(Greeting greeting) {
        logger.info("> sendAsyncWithResult");
        AsyncResponse<Boolean> response = new AsyncResponse<Boolean>();
        try {
            Boolean success = send(greeting);
            response.complete(success);
        } catch (Exception e) {
            logger.warn("Exception caught sending asynchronous mail.", e);
            response.completeExceptionally(e);
        }
        logger.info("< sendAsyncWithResult");
        return response;
    }

}
