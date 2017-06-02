package co.edu.usta.telco.iot.service;

import co.edu.usta.telco.iot.data.model.Capture;
import co.edu.usta.telco.iot.data.model.Sensor;
import co.edu.usta.telco.iot.data.model.User;

/**
 */
public interface CaptureService {

    boolean validatePermissionsForCapture(User user, Capture capture);

    boolean validatePermissionsForSensor(User user, Sensor sensor);

    Capture save(Capture capture);
}