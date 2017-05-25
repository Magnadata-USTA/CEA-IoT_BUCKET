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

    @RequestMapping(path = "/sensors/{sensorId}/alerts/delete/{alertId}", method = RequestMethod.GET)
    String delete(@PathVariable String sensorId, @PathVariable String alertId, Model model, Principal principal) {
        alertRepository.delete(alertId);

        Sensor linkedSensor = sensorRepository.findOne(sensorId);
        Device linkedDevice = deviceRepository.findOne(linkedSensor.getDeviceId());
        model.addAttribute("alert", new Alert());
        return getAllModel(model, linkedDevice.getSolutionId(), linkedSensor.getDeviceId(), sensorId, principal);
    }

    @RequestMapping(path = "/sensors/{sensorId}/alerts/edit/{alertId}", method = RequestMethod.GET)
    String edit(@PathVariable String alertId, Model model) {
        Alert alert = alertRepository.findOne(alertId);
        model.addAttribute("alert", alert);
        return "alerts/editAlert";
    }

    @RequestMapping(path = "/sensors/{sensorId}/alerts/saveEdit", method = RequestMethod.POST)
    String saveEdit(@ModelAttribute Alert alert, Model model, Principal principal) {
        Sensor linkedSensor = sensorRepository.findOne(alert.getSensorId());
        Device linkedDevice = deviceRepository.findOne(linkedSensor.getDeviceId());
        alertRepository.save(alert);
        return getAllModel(model, linkedDevice.getSolutionId(),
                           linkedSensor.getDeviceId(), alert.getSensorId(), principal);
    }

}
