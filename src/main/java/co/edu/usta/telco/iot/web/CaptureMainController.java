package co.edu.usta.telco.iot.web;

import co.edu.usta.telco.iot.data.model.Capture;
import co.edu.usta.telco.iot.data.repository.CaptureRepository;
import co.edu.usta.telco.iot.data.repository.ThingRepository;
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
    private ThingRepository thingRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/things/{thingId}/captures")
    String getAllModel(Model model, @PathVariable String thingId) {
        thingRepository.findOne(thingId);
        List<Capture> listCapture = captureRepository.findAll();

        model.addAttribute("captures", listCapture);
        return "capturesPage";
    }

}
