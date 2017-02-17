package co.edu.usta.telco.iot.web.api;

import co.edu.usta.telco.iot.data.model.Capture;
import co.edu.usta.telco.iot.data.model.Device;
import co.edu.usta.telco.iot.data.repository.CaptureRepository;
import co.edu.usta.telco.iot.data.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/thing/capture")
public class ApiCaptureController {

    @Autowired
    private CaptureRepository captureRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<Capture>> getAll() {
        return new ResponseEntity<List<Capture>>(captureRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{captureId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Capture> getThingInformation( @PathVariable String captureId, @RequestParam(required = false) String thing2) { // thing2 sample for url?things=value
        return new ResponseEntity<Capture>(captureRepository.findOne(captureId), HttpStatus.OK);
    }

    @RequestMapping( method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity createThingInformation(@RequestBody Capture capture) {
        captureRepository.save(capture);
        System.out.println(capture.getDeviceId());
//        deviceRepository.findOne(capture.getDeviceId()).addCapture(capture);
        Device thing = deviceRepository.findOne(capture.getDeviceId());
//        thing.setCaptures(captureRepository.findByDeviceId(capture.getDeviceId()));
        System.out.println(thing.getId());
        //thing.addCapture(capture);
        return new ResponseEntity(HttpStatus.CREATED);
    }


}
