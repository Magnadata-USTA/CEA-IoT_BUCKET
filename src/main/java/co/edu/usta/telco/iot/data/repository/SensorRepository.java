package co.edu.usta.telco.iot.data.repository;

import co.edu.usta.telco.iot.data.model.Sensor;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Sensors repository.
 *
 * @version 1.0
 * @since 1.0
 */
public interface SensorRepository extends MongoRepository<Sensor, String> {

    List<Sensor> findAll();

    List<Sensor> findByDeviceId(String id);
}
