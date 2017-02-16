package co.edu.usta.telco.iot.web;

import co.edu.usta.telco.iot.data.model.Solution;
import co.edu.usta.telco.iot.data.repository.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/solutions")
public class SolutionMainController {

    @Autowired
    private SolutionRepository solutionRepository;

    @RequestMapping(method = RequestMethod.GET)
    String getAllSolutions(Model model, @PathVariable String thingId) {
        solutionRepository.findOne(thingId);
        List<Solution> listSolutions = solutionRepository.findAll();

        model.addAttribute("captures", listSolutions);
        return "solutions/list";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/solutions/{solutionId")
    String editSolution(Model model, @PathVariable String solutionId) {
        Solution solution = solutionRepository.findOne(solutionId);

        model.addAttribute("solution", solution);
        return "solutions/editPage";
    }

}
