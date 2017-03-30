package co.edu.usta.telco.iot.web.api;

import co.edu.usta.telco.iot.data.model.Capture;
import co.edu.usta.telco.iot.data.model.Sensor;
import co.edu.usta.telco.iot.data.repository.CaptureRepository;
import co.edu.usta.telco.iot.data.repository.SensorRepository;
import com.jasongoodwin.monads.Try;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/api/sensors/{sensorId}/captures")
public class ApiCaptureController {

    @Autowired
    private CaptureRepository captureRepository;
    @Autowired
    private SensorRepository sensorRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<Capture>> getAll(@PathVariable String sensorId) {
        return new ResponseEntity<List<Capture>>(
                captureRepository.findAllBySensorIdOrderBySaveDateDesc(sensorId, new PageRequest(0, 20)),
                HttpStatus.OK);
    }

    @RequestMapping(value="/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<Capture>> search(@RequestParam(required = false) String captureStartDate, @RequestParam(required = false) String captureEndDate) {
        if (Objects.isNull(captureStartDate) || Objects.isNull(captureEndDate)) {
            return new ResponseEntity("Error: missing date parameters",
                    HttpStatus.BAD_REQUEST);
        }

        Optional<Date> optionalStartDate = Try.ofFailable(() ->
                new SimpleDateFormat(DateFormatUtils.ISO_DATE_FORMAT.getPattern()).parse(captureStartDate)
        ).toOptional();

        Optional<Date> optionalEndDate = Try.ofFailable(() ->
                new SimpleDateFormat(DateFormatUtils.ISO_DATE_FORMAT.getPattern()).parse(captureEndDate)
        ).toOptional();

        if (BooleanUtils.isFalse(optionalStartDate.isPresent()) || BooleanUtils.isFalse(optionalEndDate.isPresent())) {
            return new ResponseEntity("Error: Date must have the format : " + DateFormatUtils.ISO_DATE_FORMAT.getPattern(),
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<List<Capture>>(
                captureRepository.findBetweenCaptureDates(
                        DateUtils.truncate(optionalStartDate.get(), Calendar.DATE),
                        DateUtils.addMilliseconds(DateUtils.ceiling(optionalEndDate.get(), Calendar.DATE) , -1) ),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/{captureId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Capture> getThingInformation(@PathVariable String captureId) {
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
