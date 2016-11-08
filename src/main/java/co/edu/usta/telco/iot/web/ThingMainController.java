package co.edu.usta.telco.iot.web;

import co.edu.usta.telco.iot.data.model.Capture;
import co.edu.usta.telco.iot.data.model.Thing;
import co.edu.usta.telco.iot.data.repository.CaptureRepository;
import co.edu.usta.telco.iot.data.repository.ThingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/things")
public class ThingMainController {

    @Autowired
    private ThingRepository thingRepository;

    @Autowired
    private CaptureRepository captureRepository;

    @RequestMapping(method = RequestMethod.GET)
    String getAllModel(Model model) {
        List<Thing> listThings = thingRepository.findAll();

        model.addAttribute("things", listThings );
        model.addAttribute("thing", new Thing());
        return "thingsPage";
    }

    @RequestMapping(method = RequestMethod.POST)
    String createThing(@ModelAttribute Thing thing, Model model) {
        thingRepository.save(thing);

        List<Thing> listThings = thingRepository.findAll();
        model.addAttribute("things", listThings);
        model.addAttribute("thing", new Thing());

        return "thingsPage";
    }

    @RequestMapping(path = "/delete/{thingId}", method = RequestMethod.GET)
    String createThing(@PathVariable String thingId, Model model) {
        thingRepository.delete(thingId);
        List<Thing> listThings = thingRepository.findAll();
        model.addAttribute("things", listThings);
        model.addAttribute("thing", new Thing());
        return "thingsPage";
    }

    @RequestMapping(path = "/edit/{thingId}", method = RequestMethod.GET)
    String editThing(@PathVariable String thingId, Model model) {
        Thing thing = thingRepository.findOne(thingId);
        model.addAttribute("thing", thing);
        return "editPage";
    }


    @RequestMapping(path = "/saveEdit", method = RequestMethod.POST)
    String editThing(@ModelAttribute Thing thing, Model model) {

        thingRepository.save(thing);
        List<Thing> listThings = thingRepository.findAll();
        model.addAttribute("things", listThings);
        model.addAttribute("thing", new Thing());
        return "thingsPage";
    }

}
