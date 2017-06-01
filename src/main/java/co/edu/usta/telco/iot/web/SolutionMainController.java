package co.edu.usta.telco.iot.web;

import co.edu.usta.telco.iot.data.model.Solution;
import co.edu.usta.telco.iot.data.repository.SolutionRepository;
import co.edu.usta.telco.iot.exception.UnauthorizedException;
import co.edu.usta.telco.iot.service.SolutionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/solutions")
public class SolutionMainController {

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private SolutionService solutionService;

    @RequestMapping(method = RequestMethod.GET)
    String getAllSolutions(Model model, Principal principal) {

        if(Objects.isNull(principal)) {
            throw new UnauthorizedException();
        }

        List<Solution> listSolutions = solutionRepository.findByLogin(principal.getName());

        model.addAttribute("solutions", listSolutions);
        model.addAttribute("solution", new Solution());
        return "solutions/listSolutions";
    }

    @RequestMapping(method = RequestMethod.POST)
    String createSolution(Model model, @ModelAttribute Solution solution, Principal principal) {
        if(Objects.isNull(principal) || StringUtils.equals(solution.getLogin(), principal.getName())) {
            throw new UnauthorizedException();
        }

        solution.setLogin(principal.getName());
        solutionRepository.save(solution);
        List<Solution> listSolutions = solutionRepository.findByLogin(principal.getName());

        model.addAttribute("solutions", listSolutions);
        model.addAttribute("solution", new Solution());
        return "solutions/listSolutions";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete/{solutionId}")
    String deleteSolution(Model model, @PathVariable String solutionId, RedirectAttributes redirectAttributes, Principal principal) {
        if(StringUtils.isEmpty(solutionId)) return addSolutionSimpleError("Empty id for delete", model, principal);
        solutionRepository.delete(solutionId);
        List<Solution> listSolutions = solutionRepository.findByLogin(principal.getName());

        model.addAttribute("solutions", listSolutions);
        model.addAttribute("solution", new Solution());
        return "solutions/listSolutions";
    }

    @RequestMapping(path = "/edit/{solutionId}", method = RequestMethod.GET)
    String editSolution(@PathVariable String solutionId, Model model, RedirectAttributes redirectAttributes, Principal principal) {
        if (StringUtils.isEmpty(solutionId)) return addSolutionSimpleError("Empty id for edit", model, principal);
        Solution solution = solutionRepository.findOne(solutionId);
        model.addAttribute("solution", solution);
        return "solutions/editSolution";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveEdit")
    String saveEditSolution(Model model, @ModelAttribute Solution solution, Principal principal) {

        if ( ! solutionService.validatePermissionsForSolution(principal.getName(), solution.getId()) )  return addSolutionSimpleError("Access denied", model, principal);

        if ( ! StringUtils.equals(principal.getName(), solution.getLogin()) )  return addSolutionSimpleError("Operation denied", model, principal);

        solutionRepository.save(solution);
        List<Solution> listSolutions = solutionRepository.findByLogin(principal.getName());

        model.addAttribute("solutions", listSolutions);
        model.addAttribute("solution", new Solution());
        return "solutions/listSolutions";
    }

    private String addSolutionSimpleError(String error, Model model, Principal principal) {
        model.addAttribute("errorMessage", error);
        return getAllSolutions(model, principal);
    }

}
