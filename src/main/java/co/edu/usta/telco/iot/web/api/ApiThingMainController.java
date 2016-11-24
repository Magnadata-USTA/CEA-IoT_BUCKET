package co.edu.usta.telco.iot.web.api;

import co.edu.usta.telco.iot.data.model.Device;
import co.edu.usta.telco.iot.data.repository.CaptureRepository;
import co.edu.usta.telco.iot.data.repository.ThingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/thing")
public class ApiThingMainController {

    @Autowired
    private ThingRepository thingRepository;

    @Autowired
    private CaptureRepository captureRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<Device>> getAll() {
        return new ResponseEntity<List<Device>>(thingRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{thingId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Device> getThingInformation(@PathVariable String thingId, @RequestParam(required = false) String thing2) { // thing2 sample for url?things=value

        Device thing = thingRepository.findOne(thingId);
//        thing.setCaptures(captureRepository.findByDeviceId(thing.getId()));
        return new ResponseEntity<Device>(thing, HttpStatus.OK);
    }

    @RequestMapping( method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity createThingInformation(@RequestBody Device thing) {
        thingRepository.save(thing);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
