package co.edu.usta.telco.iot.web;

import co.edu.usta.telco.iot.data.model.Device;
import co.edu.usta.telco.iot.data.repository.CaptureRepository;
import co.edu.usta.telco.iot.data.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/devices")
public class DeviceMainController {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private CaptureRepository captureRepository;

    @RequestMapping(method = RequestMethod.GET)
    String getAllModel(Model model) {
        List<Device> listDevices = deviceRepository.findAll();

        model.addAttribute("devices", listDevices );
        model.addAttribute("device", new Device());
        return "thingsPage";
    }

    @RequestMapping(method = RequestMethod.POST)
    String createDevice(@ModelAttribute Device device, Model model) {
        deviceRepository.save(device);

        List<Device> listThings = deviceRepository.findAll();
        model.addAttribute("things", listThings);
        model.addAttribute("thing", new Device());

        return "thingsPage";
    }

    @RequestMapping(path = "/delete/{thingId}", method = RequestMethod.GET)
    String createThing(@PathVariable String thingId, Model model) {
        deviceRepository.delete(thingId);
        List<Device> listThings = deviceRepository.findAll();
        model.addAttribute("things", listThings);
        model.addAttribute("thing", new Device());
        return "thingsPage";
    }

    @RequestMapping(path = "/edit/{thingId}", method = RequestMethod.GET)
    String editThing(@PathVariable String deviceId, Model model) {
        Device device = deviceRepository.findOne(deviceId);
        model.addAttribute("device", device);
        return "editPage";
    }


    @RequestMapping(path = "/saveEdit", method = RequestMethod.POST)
    String editThing(@ModelAttribute Device thing, Model model) {

        deviceRepository.save(thing);
        List<Device> listThings = deviceRepository.findAll();
        model.addAttribute("things", listThings);
        model.addAttribute("thing", new Device());
        return "thingsPage";
    }

}
