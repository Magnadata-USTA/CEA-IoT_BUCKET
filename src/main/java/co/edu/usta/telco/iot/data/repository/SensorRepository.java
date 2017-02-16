package co.edu.usta.telco.iot.data.repository;

import co.edu.usta.telco.iot.data.model.Sensor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 */
public interface SensorRepository extends MongoRepository<Sensor, String>{
    List<Sensor> findAll();
}
