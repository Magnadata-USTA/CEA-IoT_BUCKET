package co.edu.usta.telco.iot.service.impl;

import co.edu.usta.telco.iot.aspect.alert.AlertAspectService;
import co.edu.usta.telco.iot.data.model.*;
import co.edu.usta.telco.iot.data.repository.CaptureRepository;
import co.edu.usta.telco.iot.data.repository.DeviceRepository;
import co.edu.usta.telco.iot.data.repository.SensorRepository;
import co.edu.usta.telco.iot.data.repository.SolutionRepository;
import co.edu.usta.telco.iot.service.CaptureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

/**
 */
@Service
public class CaptureServiceImpl implements CaptureService {

    private static final Logger LOG = LoggerFactory.getLogger(CaptureServiceImpl.class);

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private CaptureRepository captureRepository;

    @Autowired
    public AlertAspectService alertAspectService;

    @Override
    public boolean validatePermissionsForCapture(User user, Capture capture) {

        if (user == null || user.getLogin() == null || capture == null) {
            LOG.debug("null arguments on validatePermissionsForCapture");
            return false;
        }

        Sensor sensor = sensorRepository.findOne(capture.getSensorId());

        Device device = deviceRepository.findOne(sensor.getDeviceId());

        Solution solution = solutionRepository.findOne(device.getSolutionId());

        if ( StringUtils.equals( user.getLogin() , solution.getLogin() ) ) {
            return true;
        }

        return false;
    }


    public boolean validatePermissionsForSensor(User user, Sensor sensor) {

        // TODO: *** move to sensors Service
        if (user == null || user.getLogin() == null || sensor == null) {
            LOG.debug("null arguments on validatePermissionsForCapture");
            return false;
        }

        Device device = deviceRepository.findOne(sensor.getDeviceId());

        Solution solution = solutionRepository.findOne(device.getSolutionId());

        if ( StringUtils.equals( user.getLogin(), solution.getLogin() ) ) {
            return true;
        }

        return false;
    }

    @Override
    public Capture save(Capture capture) {
        capture = captureRepository.save(capture);
        // TODO: to be called using an aspect
        alertAspectService.afterCreatedCapture(capture);
        return capture;
    }

}
