package co.edu.usta.telco.iot.web.api;

import co.edu.usta.telco.iot.data.model.Capture;
import co.edu.usta.telco.iot.data.model.Device;
import co.edu.usta.telco.iot.data.model.Sensor;
import co.edu.usta.telco.iot.data.repository.DeviceRepository;
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
@RequestMapping("/api/sensors")
public class ApiSensorController {

    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<Sensor>> getAll() {
        return new ResponseEntity<List<Sensor>>(sensorRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{sensorId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Sensor> getObject( @PathVariable String sensorId) {
        return new ResponseEntity<Sensor>(sensorRepository.findOne(sensorId), HttpStatus.OK);
    }

    @RequestMapping( method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity createSensor(@RequestBody Sensor sensor) {
        if (Objects.isNull(sensor) || StringUtils.isEmpty(sensor.getDeviceId())) {
            return new ResponseEntity("Error: Field 'Device id' is mandatory", HttpStatus.BAD_REQUEST);
        }
        Device device = deviceRepository.findOne(sensor.getDeviceId());
        if (Objects.isNull(device)) {
            return new ResponseEntity("Error: Device not found for the given 'Sensor id'", HttpStatus.BAD_REQUEST);
        }
        sensorRepository.save(sensor);
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
