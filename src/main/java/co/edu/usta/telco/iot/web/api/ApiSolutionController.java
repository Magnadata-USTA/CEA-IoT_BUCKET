package co.edu.usta.telco.iot.web.api;

import co.edu.usta.telco.iot.data.model.Solution;
import co.edu.usta.telco.iot.data.model.User;
import co.edu.usta.telco.iot.data.repository.SolutionRepository;
import co.edu.usta.telco.iot.data.repository.UserRepository;
import co.edu.usta.telco.iot.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/api/solutions")
public class ApiSolutionController {
    private static Logger LOG = LoggerFactory.getLogger(ApiSolutionController.class);

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<Solution>> getAll(@RequestParam String userToken) {
        User user = userService.validateToken(userToken);
        if(Objects.isNull(user)) {
            LOG.debug("The user does not have permission over the entity");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Solution> solutions = solutionRepository.findByLogin(user.getLogin());
        return new ResponseEntity<>(solutions, HttpStatus.OK);
    }

    @RequestMapping(value = "/{solutionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Solution> getSolutionInformation(@PathVariable String solutionId, @RequestParam String userToken) {
        User user = userService.validateToken(userToken);
        if(Objects.isNull(user)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Solution solution = solutionRepository.findOne(solutionId);
        if (! StringUtils.equals(user.getLogin(), solution.getLogin())) {
            LOG.debug("The user does not have permission over the entity");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(solution, HttpStatus.OK);
    }

}
