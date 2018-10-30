package br.zup.api;


import br.zup.model.Greeting;
import br.zup.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import br.zup.service.GreetingService;

@RestController
public class GreetingController {

    @Autowired
    private GreetingService greetingService;

    @RequestMapping(value = "/api/greetings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Greeting>> getGreeting(){
        Collection<Greeting> greetings = greetingService.findAll();

        return new ResponseEntity<Collection<Greeting>>(greetings, HttpStatus.OK);
    }

    @RequestMapping(
            value = "api/greetings/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> getGreeting(@PathVariable Long id){
        Greeting greeting = greetingService.findOne(id);
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
        Greeting saveGreeting = greetingService.create(greeting);

        return new ResponseEntity<Greeting>(saveGreeting, HttpStatus.CREATED);

    }

    @RequestMapping(
            value = "/api/greetings/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Greeting> updateGreeting(@RequestBody Greeting greeting){

        Greeting updatedGreeting = greetingService.update(greeting);
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
    public ResponseEntity<Greeting> deleteGreeting(@PathVariable Long id){
        greetingService.delete(id);
        return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);
    }
}
