package br.zup.service;

import br.zup.model.Greeting;
import org.springframework.stereotype.Service;

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
            nextId = 1L;
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
        nextId = (long) +1;
        greetingMap.put(greeting.getId(),greeting);
        return greeting;

    }

    private void remove(Long id){
        greetingMap.remove(id);
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
        return greetingMap.values();
    }

    @Override
    public Greeting findOne(Long id) {
        return greetingMap.get(id);
    }

    @Override
    public Greeting create(Greeting greeting) {

        return save(greeting);
    }

    @Override
    public Greeting update(Greeting greeting) {
        return save(greeting);
    }

    @Override
    public void delete(Long id) {
        remove(id);
    }
}
