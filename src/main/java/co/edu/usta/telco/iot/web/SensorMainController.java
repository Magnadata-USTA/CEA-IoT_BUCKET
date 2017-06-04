package co.edu.usta.telco.iot.web;

import co.edu.usta.telco.iot.data.model.Device;
import co.edu.usta.telco.iot.data.model.Sensor;
import co.edu.usta.telco.iot.data.model.Solution;
import co.edu.usta.telco.iot.data.repository.DeviceRepository;
import co.edu.usta.telco.iot.data.repository.SensorRepository;
import co.edu.usta.telco.iot.data.repository.SolutionRepository;
import co.edu.usta.telco.iot.exception.UnauthorizedException;
import co.edu.usta.telco.iot.service.DataCleanerService;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SensorMainController {

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private DataCleanerService dataCleanerService;

    @RequestMapping(value = {"/devices/sensors"}, method = RequestMethod.GET)
    String getAllModelFiltered(Model model, @RequestParam(required = false) String solutionId,
                               @RequestParam(required = false) String deviceId, Principal principal) {
        // Filtering logic
        List<Device> listDevices = Collections.emptyList();
        List<Sensor> listSensors = Collections.emptyList();
        Solution chosenSolution = new Solution();
        Device chosenDevice = new Device();
        Sensor sensor = new Sensor();

        if (Objects.isNull(principal) || StringUtils.isBlank(principal.getName())) {
            throw new UnauthorizedException();
        }

        if (StringUtils.isNotEmpty(solutionId)) {
            listDevices = deviceRepository.findBySolutionId(solutionId);
            chosenSolution = solutionRepository.findOne(solutionId);
        }

        if (StringUtils.isNotEmpty(deviceId)) {
            chosenDevice = deviceRepository.findOne(deviceId);
            listSensors = sensorRepository.findByDeviceId(deviceId);
            sensor.setDeviceId(deviceId);
        }

        List<Solution> listSolutions = solutionRepository.findByLogin(principal.getName());
        model.addAttribute("chosenSolution", chosenSolution);
        model.addAttribute("chosenDevice", chosenDevice);
        model.addAttribute("solutions", listSolutions );
        model.addAttribute("devices", listDevices );
        model.addAttribute("sensors", listSensors );
        model.addAttribute("sensor", sensor);

        return "sensors/listSensors";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/devices/{deviceId}/sensors")
    String create(@ModelAttribute Sensor sensor, Model model,
                  @PathVariable(required = false) String solutionId, Principal principal) {
        sensorRepository.save(sensor);
        return getAllModelFiltered(model, solutionId, sensor.getDeviceId(), principal);
    }

    @RequestMapping(path = "/devices/{deviceId}/sensors/delete/{sensorId}", method = RequestMethod.GET)
    String delete(@PathVariable String deviceId,
                  @PathVariable String sensorId, Model model, Principal principal) {
        dataCleanerService.deleteSensorById(sensorId);
        Device linkedDevice = deviceRepository.findOne(deviceId);
        return getAllModelFiltered(model, linkedDevice.getSolutionId(), deviceId, principal);
    }

    @RequestMapping(path = "/devices/{deviceId}/sensors/edit/{sensorId}", method = RequestMethod.GET)
    String edit(@PathVariable String deviceId, @PathVariable String sensorId, Model model) {
        Sensor sensor = sensorRepository.findOne(sensorId);
        model.addAttribute("sensor", sensor);
        return "sensors/editSensor";
    }

    @RequestMapping(path = "/devices/{deviceId}/sensors/saveEdit", method = RequestMethod.POST)
    String saveEdit(@ModelAttribute Sensor sensor, Model model, Principal principal) {
        Device linkedDevice = deviceRepository.findOne(sensor.getDeviceId());
        sensorRepository.save(sensor);
        return getAllModelFiltered(model, linkedDevice.getSolutionId(), sensor.getDeviceId(), principal);
    }

}
