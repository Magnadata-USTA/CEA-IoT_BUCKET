package co.edu.usta.telco.iot.web;

import co.edu.usta.telco.iot.data.model.*;
import co.edu.usta.telco.iot.data.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class AlertMainController {

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private CaptureRepository captureRepository;

    @Autowired
    private AlertRepository alertRepository;

    @RequestMapping(value = {"/sensors/alerts"}, method = RequestMethod.GET)
    String getAllModel(Model model, @RequestParam(required = false) String solutionId,
                       @RequestParam(required = false) String deviceId,
                       @RequestParam(required = false) String sensorId, Principal principal) {
        // Filtering logic
        List<Device> listDevices = Collections.emptyList();
        List<Sensor> listSensors = Collections.emptyList();
        List<Alert> listAlerts = Collections.emptyList();
        Solution chosenSolution = new Solution();
        Device chosenDevice = new Device();
        Sensor chosenSensor = new Sensor();

        Alert alert = new Alert();
        if (StringUtils.isNotEmpty(solutionId)) {
            listDevices = deviceRepository.findBySolutionId(solutionId);
            chosenSolution = solutionRepository.findOne(solutionId);
        }

        if (StringUtils.isNotEmpty(deviceId)) {
            chosenDevice = deviceRepository.findOne(deviceId);
            listSensors = sensorRepository.findByDeviceId(deviceId);
        }

        if (StringUtils.isNotEmpty(sensorId)) {
            chosenSensor = sensorRepository.findOne(sensorId);
            listAlerts = alertRepository.findBySensorId(sensorId, new PageRequest(0, 20));
            alert.setSensorId(sensorId);
        }

        List<Solution> listSolutions = solutionRepository.findByLogin(principal.getName());
        model.addAttribute("chosenSolution", chosenSolution);
        model.addAttribute("chosenDevice", chosenDevice);
        model.addAttribute("chosenSensor", chosenSensor);
        model.addAttribute("solutions", listSolutions );
        model.addAttribute("devices", listDevices );
        model.addAttribute("sensors", listSensors );
        model.addAttribute("alerts", listAlerts);
        model.addAttribute("alert", alert);

        return "alerts/listAlerts";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/sensors/{sensorId}/alerts")
    String create(@ModelAttribute Alert alert, Model model,
                  @PathVariable(required = false) String sensorId, Principal principal) {
        alertRepository.save(alert);

        Sensor linkedSensor = sensorRepository.findOne(sensorId);
        Device linkedDevice = deviceRepository.findOne(linkedSensor.getDeviceId());

        return getAllModel(model, linkedDevice.getSolutionId(), linkedSensor.getDeviceId(), sensorId, principal);
    }

    /*
    @RequestMapping(path = "/sensors/{sensorId}/captures/delete/{captureId}", method = RequestMethod.GET)
    String delete(@PathVariable String sensorId, @PathVariable String captureId, Model model, Principal principal) {
        captureRepository.delete(captureId);

        Sensor linkedSensor = sensorRepository.findOne(sensorId);
        Device linkedDevice = deviceRepository.findOne(linkedSensor.getDeviceId());
        List<Capture> listCaptures = captureRepository.findAll();
        model.addAttribute("captures", listCaptures);
        model.addAttribute("capture", new Capture());
        return getAllModel(model, linkedDevice.getSolutionId(), linkedSensor.getDeviceId(), sensorId, principal);
    }

    @RequestMapping(path = "/sensors/{sensorId}/captures/edit/{captureId}", method = RequestMethod.GET)
    String edit(@PathVariable String captureId, Model model) {
        Capture capture = captureRepository.findOne(captureId);
        model.addAttribute("capture", capture);
        return "captures/editCapture";
    }

    @RequestMapping(path = "/sensors/{sensorId}/captures/saveEdit", method = RequestMethod.POST)
    String saveEdit(@ModelAttribute Capture capture, Model model, Principal principal) {
        Sensor linkedSensor = sensorRepository.findOne(capture.getSensorId());
        Device linkedDevice = deviceRepository.findOne(linkedSensor.getDeviceId());
        captureRepository.save(capture);
        return getAllModel(model, linkedDevice.getSolutionId(),
                           linkedSensor.getDeviceId(), capture.getSensorId(), principal);
    }
*/

}
