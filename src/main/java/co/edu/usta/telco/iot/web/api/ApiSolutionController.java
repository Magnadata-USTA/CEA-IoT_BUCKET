package co.edu.usta.telco.iot.web.api;

import co.edu.usta.telco.iot.data.model.Solution;
import co.edu.usta.telco.iot.data.repository.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/solutions")
public class ApiSolutionController {

    @Autowired
    private SolutionRepository solutionRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<Solution>> getAll() {
        return new ResponseEntity<>(solutionRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{solutionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Solution> getSolutionInformation(@PathVariable String solutionId) {
        return new ResponseEntity<>(solutionRepository.findOne(solutionId), HttpStatus.OK);
    }

}
