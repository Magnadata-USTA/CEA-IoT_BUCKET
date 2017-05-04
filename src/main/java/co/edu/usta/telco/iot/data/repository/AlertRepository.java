package co.edu.usta.telco.iot.data.repository;

import co.edu.usta.telco.iot.data.model.Alert;
import co.edu.usta.telco.iot.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 */
public interface AlertRepository extends MongoRepository<Alert, String> {

    void findBySensorId(String sensorId);
}
