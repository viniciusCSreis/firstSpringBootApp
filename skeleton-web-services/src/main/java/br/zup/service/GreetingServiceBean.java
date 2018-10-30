package br.zup.service;

import br.zup.model.Greeting;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class GreetingServiceBean implements GreetingService{


    private static Long nextId;
    private static Map<Long, Greeting> greetingMap;

    private static Greeting save(Greeting greeting){
        if(greetingMap == null){
            greetingMap = new HashMap<Long, Greeting>();
            nextId = new Long(1);
        }

        if(greeting.getId()!= null)
        {
            Greeting oldGreeting = greetingMap.get(greeting.getId());
            if(oldGreeting == null) {
                return null;
            }
            greetingMap.remove(greeting.getId());
            greetingMap.put(greeting.getId(),greeting);
            return greeting;
        }
        greeting.setId(nextId);
        nextId = Long.valueOf(+ 1);
        greetingMap.put(greeting.getId(),greeting);
        return greeting;

    }

    public boolean remove(Long id){
        Greeting deletedGreeting = greetingMap.remove(id);
        if(deletedGreeting == null) {
            return false;
        }
        return true;
    }

    static {
        Greeting g1 = new Greeting();
        g1.setText("Hello Wolrd");
        save(g1);

        Greeting g2 = new Greeting();
        g2.setText("Hello World 2");
        save(g2);


        Greeting g3 = new Greeting();
        g3.setText("Hello World 3");
        save(g3);
    }





    @Override
    public Collection<Greeting> findAll() {
        Collection<Greeting> greetings = greetingMap.values();
        return  greetings;
    }

    @Override
    public Greeting findOne(Long id) {
        Greeting greeting = greetingMap.get(id);
        return greeting;
    }

    @Override
    public Greeting create(Greeting greeting) {
        Greeting savedGreeting = save(greeting);

        return savedGreeting;
    }

    @Override
    public Greeting update(Greeting greeting) {
        Greeting updatedGreeting = save(greeting);
        return updatedGreeting;
    }

    @Override
    public void delete(Long id) {
        remove(id);
    }
}
