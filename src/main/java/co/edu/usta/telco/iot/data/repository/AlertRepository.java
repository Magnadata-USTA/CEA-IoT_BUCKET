package co.edu.usta.telco.iot.data.repository;

import co.edu.usta.telco.iot.data.model.Alert;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Alerts repository.
 *
 * @version 1.0
 * @since 1.0
 */
public interface AlertRepository extends MongoRepository<Alert, String> {

    List<Alert> findBySensorId(String sensorId);

    List<Alert> findBySensorId(String sensorId, PageRequest pageRequest);
}
