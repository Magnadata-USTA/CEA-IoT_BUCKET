package co.edu.usta.telco.iot.web;

import co.edu.usta.telco.iot.data.model.Device;
import co.edu.usta.telco.iot.data.model.Solution;
import co.edu.usta.telco.iot.data.repository.CaptureRepository;
import co.edu.usta.telco.iot.data.repository.DeviceRepository;
import co.edu.usta.telco.iot.data.repository.SensorRepository;
import co.edu.usta.telco.iot.data.repository.SolutionRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;
import java.util.List;

@Controller
public class CaptureMainController {

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private CaptureRepository captureRepository;

    @RequestMapping(value = {"/sensors/captures","/sensors/{deviceId}/captures"}, method = RequestMethod.GET)
    String getAllModel(Model model, @PathVariable(required = false) String solutionId) {
        List<Device> listDevices = Collections.emptyList();
        Solution chosenSolution = new Solution();
        Device device = new Device();
        if (StringUtils.isNotEmpty(solutionId)) {
            listDevices = deviceRepository.findBySolutionId(solutionId);
            chosenSolution = solutionRepository.findOne(solutionId);
            device.setSolutionId(solutionId);
        }
        List<Solution> listSolutions = solutionRepository.findAll();
        model.addAttribute("chosenSolution", chosenSolution);
        model.addAttribute("solutions", listSolutions );
        model.addAttribute("devices", listDevices );
        model.addAttribute("device", device);
        return "devices/listDevices";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/sensors/{deviceId}/captures")
    String create(@ModelAttribute Device device, Model model, @PathVariable(required = false) String solutionId) {
        deviceRepository.save(device);

        List<Device> listThings = deviceRepository.findAll();
        model.addAttribute("devices", listThings);
        model.addAttribute("device", new Device());
        Solution chosenSolution = solutionRepository.findOne(solutionId);

        return getAllModel(model, solutionId);
    }

    @RequestMapping(path = "/sensors/{deviceId}/captures/delete/{deviceId}", method = RequestMethod.GET)
    String delete(@PathVariable String solutionId, @PathVariable String deviceId, Model model) {
        deviceRepository.delete(deviceId);
        List<Device> listThings = deviceRepository.findAll();
        model.addAttribute("devices", listThings);
        model.addAttribute("device", new Device());
        return getAllModel(model, solutionId);
    }

    @RequestMapping(path = "/sensors/{deviceId}/captures/edit/{deviceId}", method = RequestMethod.GET)
    String edit(@PathVariable String deviceId, Model model) {
        Device device = deviceRepository.findOne(deviceId);
        model.addAttribute("device", device);
        return "devices/editDevice";
    }

    @RequestMapping(path = "/sensors/{deviceId}/captures/saveEdit", method = RequestMethod.POST)
    String saveEdit(@ModelAttribute Device device, Model model) {
        deviceRepository.save(device);
        return getAllModel(model, device.getSolutionId());
    }

}
