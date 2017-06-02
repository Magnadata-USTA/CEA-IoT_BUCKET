package co.edu.usta.telco.iot.data.repository;

import co.edu.usta.telco.iot.data.model.Sensor;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 */
public interface SensorRepository extends MongoRepository<Sensor, String>{
    List<Sensor> findAll();
    List<Sensor> findByDeviceId(String id);
}
