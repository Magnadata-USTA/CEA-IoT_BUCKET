package co.edu.usta.telco.iot.web;

import co.edu.usta.telco.iot.data.model.Thing;
import co.edu.usta.telco.iot.data.repository.ThingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/thing")
public class ThingMainController {

    @Autowired
    private ThingRepository thingRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<Thing>> getAll() {
        return new ResponseEntity<List<Thing>>(thingRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pages/thing")
    String getAllModel() {
        System.out.println("Hielo");
        return "thingsPage";
    }

    @RequestMapping(value = "/{thingId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Thing> getThingInformation( @PathVariable String thingId, @RequestParam(required = false) String thing2) { // thing2 sample for url?things=value
        return new ResponseEntity<Thing>(thingRepository.findOne(thingId), HttpStatus.OK);
    }

    @RequestMapping( method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity createThingInformation(@RequestBody Thing thing) {
        thingRepository.save(thing);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
