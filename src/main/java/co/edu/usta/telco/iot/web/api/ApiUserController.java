package co.edu.usta.telco.iot.web.api;

import co.edu.usta.telco.iot.data.model.User;
import co.edu.usta.telco.iot.data.repository.DeviceRepository;
import co.edu.usta.telco.iot.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
@Controller
@RequestMapping("/api/users")
public class ApiUserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<User> getUserInformation( @PathVariable String userId) {
        return new ResponseEntity<>(userRepository.findOne(userId), HttpStatus.OK);
    }

    @RequestMapping( method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity createUserInformation(@RequestBody User user) {
        if (user == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        userRepository.save(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }

}



 */