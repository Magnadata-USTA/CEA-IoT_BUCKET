package co.edu.usta.telco.iot.web.api;

import co.edu.usta.telco.iot.data.model.Device;
import co.edu.usta.telco.iot.data.model.Solution;
import co.edu.usta.telco.iot.data.model.User;
import co.edu.usta.telco.iot.data.repository.DeviceRepository;
import co.edu.usta.telco.iot.data.repository.SolutionRepository;
import co.edu.usta.telco.iot.data.repository.UserRepository;
import co.edu.usta.telco.iot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/devices")
public class ApiDeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<Device>> getAll(@RequestParam String userToken) {
        User user = userService.validateToken(userToken);
        if(Objects.isNull(user)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<String> listSolutionIds =  solutionRepository.findByLogin(user.getLogin()).stream().map(solution -> solution.getId()).collect(Collectors.toList());
        List<Device> listDevices = deviceRepository.findBySolutionIdIn(listSolutionIds);
        return new ResponseEntity<>(listDevices, HttpStatus.OK);
    }

    @RequestMapping(value = "/{deviceId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Device> getThingInformation(@PathVariable String deviceId) {
        Device thing = deviceRepository.findOne(deviceId);
        return new ResponseEntity<Device>(thing, HttpStatus.OK);
    }

    @RequestMapping( method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity createDeviceInformation(@RequestBody Device device) {
        if (device == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (device.getId() != null) {
            return new ResponseEntity("Entity creation doesn't take an ID", HttpStatus.BAD_REQUEST);
        }
        deviceRepository.save(device);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{deviceId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity modifyDevice(@RequestBody Device device) {
        if (device.getId() == null) {
            return new ResponseEntity("Entity modification takes an ID", HttpStatus.BAD_REQUEST);
        }
        deviceRepository.save(device);
        return new ResponseEntity(HttpStatus.OK);
    }
}
