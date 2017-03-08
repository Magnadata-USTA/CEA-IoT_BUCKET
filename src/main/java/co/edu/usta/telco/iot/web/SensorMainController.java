package co.edu.usta.telco.iot.web;

import co.edu.usta.telco.iot.data.model.Device;
import co.edu.usta.telco.iot.data.model.Sensor;
import co.edu.usta.telco.iot.data.model.Solution;
import co.edu.usta.telco.iot.data.repository.DeviceRepository;
import co.edu.usta.telco.iot.data.repository.SensorRepository;
import co.edu.usta.telco.iot.data.repository.SolutionRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
public class SensorMainController {

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @RequestMapping(value = {"/devices/sensors"}, method = RequestMethod.GET)
    String getAllModelFiltered(Model model, @RequestParam(required = false) String solutionId,
                               @RequestParam(required = false) String deviceId) {
        // Filtering logic
        List<Device> listDevices = Collections.emptyList();
        List<Sensor> listSensors = Collections.emptyList();
        Solution chosenSolution = new Solution();
        Device chosenDevice = new Device();
        Sensor sensor = new Sensor();
        if (StringUtils.isNotEmpty(solutionId)) {
            listDevices = deviceRepository.findBySolutionId(solutionId);
            chosenSolution = solutionRepository.findOne(solutionId);
        }

        if (StringUtils.isNotEmpty(deviceId)) {
            chosenDevice = deviceRepository.findOne(deviceId);
            listSensors = sensorRepository.findByDeviceId(deviceId);
            sensor.setDeviceId(deviceId);
        }

        List<Solution> listSolutions = solutionRepository.findAll();
        model.addAttribute("chosenSolution", chosenSolution);
        model.addAttribute("chosenDevice", chosenDevice);
        model.addAttribute("solutions", listSolutions );
        model.addAttribute("devices", listDevices );
        model.addAttribute("sensors", listSensors );
        model.addAttribute("sensor", sensor);

        return "sensors/listSensors";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/devices/{deviceId}/sensors")
    String create(@ModelAttribute Sensor sensor, Model model, @PathVariable(required = false) String solutionId) {
        sensorRepository.save(sensor);

        List<Device> listThings = deviceRepository.findAll();
        model.addAttribute("devices", listThings);
        model.addAttribute("device", new Device());

        return getAllModelFiltered(model, solutionId, sensor.getDeviceId());
    }

    @RequestMapping(path = "/devices/{deviceId}/sensors/delete/{sensorId}", method = RequestMethod.GET)
    String delete(@PathVariable String deviceId, @PathVariable String sensorId, Model model) {
        deviceRepository.delete(sensorId);
        List<Device> listThings = deviceRepository.findAll();
        Device linkedDevice = deviceRepository.findOne(deviceId);
        model.addAttribute("devices", listThings);
        model.addAttribute("device", new Device());
        return getAllModelFiltered(model, linkedDevice.getSolutionId(), deviceId);
    }

    @RequestMapping(path = "/devices/{deviceId}/sensors/edit/{sensorId}", method = RequestMethod.GET)
    String edit(@PathVariable String deviceId, @PathVariable String sensorId, Model model) {
        Sensor sensor = sensorRepository.findOne(sensorId);
        model.addAttribute("sensor", sensor);
        return "sensors/editSensor";
    }

    @RequestMapping(path = "/devices/{deviceId}/sensors/saveEdit", method = RequestMethod.POST)
    String saveEdit(@ModelAttribute Sensor sensor, Model model) {
        Device linkedDevice = deviceRepository.findOne(sensor.getDeviceId());
        sensorRepository.save(sensor);
        return getAllModelFiltered(model, linkedDevice.getSolutionId(), sensor.getDeviceId());
    }

}
