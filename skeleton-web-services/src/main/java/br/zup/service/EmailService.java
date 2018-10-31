package br.zup.service;

import br.zup.model.Greeting;

import java.util.concurrent.Future;

public interface EmailService {

    Boolean send(Greeting greeting);

    void sendAsync(Greeting greeting);

    Future<Boolean> sendAsyncWithResult(Greeting greeting);


}
