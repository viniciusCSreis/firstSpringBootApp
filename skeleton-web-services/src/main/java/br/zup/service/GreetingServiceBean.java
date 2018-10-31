package br.zup.service;

import br.zup.model.Greeting;
import br.zup.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(
        propagation = Propagation.SUPPORTS,
        readOnly = true
)
public class GreetingServiceBean implements GreetingService{


    @Autowired
    private GreetingRepository greetingRepository;


    @Override
    public Collection<Greeting> findAll() {
        return greetingRepository.findAll();
    }

    @Override
    public Greeting findOne(Long id) {
        return greetingRepository.findOne(id);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false
    )
    public Greeting create(Greeting greeting) {
        if(greeting.getId() != null){
            return null;
        }
        Greeting createdGreeting = greetingRepository.save(greeting);
        if(createdGreeting.getId() == 4){
            throw new RuntimeException("Roll Back please !");
        }
        return createdGreeting;
    }

    @Override
    public Greeting update(Greeting greeting) {
        Greeting greetingToUpdate = findOne(greeting.getId());
        if(greetingToUpdate == null){
            return null;
        }
        greetingToUpdate.setText(greeting.getText());
        return greetingRepository.save(greetingToUpdate);
    }

    @Override
    public void delete(Long id) {
        greetingRepository.delete(id);
    }
}
