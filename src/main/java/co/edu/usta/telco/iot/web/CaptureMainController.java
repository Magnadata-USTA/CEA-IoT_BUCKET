package co.edu.usta.telco.iot.web;

import co.edu.usta.telco.iot.data.model.Capture;
import co.edu.usta.telco.iot.data.repository.CaptureRepository;
import co.edu.usta.telco.iot.data.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CaptureMainController {

    @Autowired
    private CaptureRepository captureRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/things/{thingId}/captures")
    String getAllModel(Model model, @PathVariable String thingId) {
        deviceRepository.findOne(thingId);
        List<Capture> listCapture = captureRepository.findAll();

        model.addAttribute("captures", listCapture);
        return "capturesPage";
    }

}
