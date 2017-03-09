package co.edu.usta.telco.iot.web.api;

import co.edu.usta.telco.iot.data.model.Capture;
import co.edu.usta.telco.iot.data.model.Sensor;
import co.edu.usta.telco.iot.data.repository.CaptureRepository;
import co.edu.usta.telco.iot.data.repository.SensorRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/api/sensors/{sensorId}/captures")
public class ApiCaptureController {

    @Autowired
    private CaptureRepository captureRepository;
    @Autowired
    private SensorRepository sensorRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<Capture>> getAll() {
        return new ResponseEntity<List<Capture>>(captureRepository.findAllByOrderBySaveDateDesc(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{captureId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Capture> getThingInformation( @PathVariable String captureId) {
        return new ResponseEntity<Capture>(captureRepository.findOne(captureId), HttpStatus.OK);
    }

    @RequestMapping( method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity createCaptureInformation(@RequestBody Capture capture) {
        if (Objects.isNull(capture) || StringUtils.isEmpty(capture.getSensorId())) {
            return new ResponseEntity("Error: Field 'Sensor id' is mandatory", HttpStatus.BAD_REQUEST);
        }
        Sensor sensor = sensorRepository.findOne(capture.getSensorId());
        if (Objects.isNull(sensor)) {
            return new ResponseEntity("Error: Sensor not found for the given 'Sensor id'", HttpStatus.BAD_REQUEST);
        }
        captureRepository.save(capture);
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
