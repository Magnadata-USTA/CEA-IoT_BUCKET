package co.edu.usta.telco.iot.web;

import co.edu.usta.telco.iot.data.model.Solution;
import co.edu.usta.telco.iot.data.repository.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/solutions")
public class SolutionMainController {

    @Autowired
    private SolutionRepository solutionRepository;

    @RequestMapping(method = RequestMethod.GET)
    String getAllSolutions(Model model) {
        List<Solution> listSolutions = solutionRepository.findAll();

        model.addAttribute("solutions", listSolutions);
        model.addAttribute("solution", new Solution());
        return "solutions/listSolutions";
    }

    @RequestMapping(method = RequestMethod.POST)
    String createSolution(Model model, @ModelAttribute Solution solution) {
        solutionRepository.save(solution);
        List<Solution> listSolutions = solutionRepository.findAll();

        model.addAttribute("solutions", listSolutions);
        model.addAttribute("solution", new Solution());
        return "solutions/listSolutions";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete/{solutionId}")
    String deleteSolution(Model model, @PathVariable String solutionId) {
        solutionRepository.delete(solutionId);
        List<Solution> listSolutions = solutionRepository.findAll();

        model.addAttribute("solutions", listSolutions);
        model.addAttribute("solution", new Solution());
        return "solutions/listSolutions";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/saveEdit/{solutionId}")
    String saveEditSolution(Model model, @ModelAttribute Solution solution) {
        solutionRepository.save(solution);
        List<Solution> listSolutions = solutionRepository.findAll();

        model.addAttribute("solution", listSolutions);
        model.addAttribute("solution", new Solution());
        return "solutions/listSolutions";
    }



}
