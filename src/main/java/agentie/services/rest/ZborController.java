package agentie.services.rest;

import agentie.model.Zbor;
import agentie.persistence.ZborRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/agentie/zbor")
public class ZborController {

    @Autowired
    private ZborRepository zborRepository;

    public ZborController() {
        zborRepository = new ZborRepository();
    }

    private static final String template = "Hello, %s!";

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }


    @RequestMapping( method=RequestMethod.GET)
    public Zbor[] getAll() throws Exception
    {
        return zborRepository.findAll().toArray(new Zbor[0]);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Zbor create( Zbor user){
        zborRepository.save(user);
        return user;

    }
    @RequestMapping( method = RequestMethod.PUT)
    public Zbor update( Zbor zbor) {
        System.out.println("Updating zbor ...");
        zborRepository.update(zbor);
        return zbor;

    }

    @RequestMapping(path = "/{id}",method= RequestMethod.DELETE)
    public Zbor delete(@PathVariable Integer id){
        //System.out.println("Deleting user ... "+username);
            Zbor zbor=new Zbor();
            zbor.setId(id);
            zborRepository.delete(zbor);


        return  zbor;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String partError(Exception e) {
        return e.getMessage();
    }
}
