package br.zup.api;


import br.zup.model.Greeting;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GreetingController {

    private static BigInteger nextId;
    private static Map<BigInteger, Greeting> greetingMap;

    private static Greeting save(Greeting greeting){
        if(greetingMap == null){
            greetingMap = new HashMap<BigInteger, Greeting>();
            nextId = BigInteger.ONE;
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
        nextId = nextId.add(BigInteger.ONE);
        greetingMap.put(greeting.getId(),greeting);
        return greeting;

    }

    public boolean delete(BigInteger id){
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


    @RequestMapping(value = "/api/greetings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Greeting>> getGreeting(){
        Collection<Greeting> greetings = greetingMap.values();

        return new ResponseEntity<Collection<Greeting>>(greetings, HttpStatus.OK);
    }

    @RequestMapping(
            value = "api/greetings/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> getGreeting(@PathVariable BigInteger id){
        Greeting greeting = greetingMap.get(id);
        if(greeting ==  null){
            return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Greeting>(greeting,HttpStatus.OK);
    }

    @RequestMapping(
            value = "api/greetings",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting){
        Greeting saveGreeting = save(greeting);

        return new ResponseEntity<Greeting>(saveGreeting, HttpStatus.CREATED);

    }

    @RequestMapping(
            value = "/api/greetings/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Greeting> updateGreeting(@RequestBody Greeting greeting){

        Greeting updatedGreeting = save(greeting);
        if(updatedGreeting == null){
            return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Greeting>(updatedGreeting,HttpStatus.OK);

    }

    @RequestMapping(
            value = "api/greetings/{id}",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Greeting> deleteGreeting(@PathVariable BigInteger id){
        if(!delete(id)){
            return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);
    }
}
