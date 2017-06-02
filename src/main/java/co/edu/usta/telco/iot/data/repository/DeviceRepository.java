package co.edu.usta.telco.iot.data.repository;

import co.edu.usta.telco.iot.data.model.Device;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Device repository.
 *
 * @author Felipe on 29/09/2016.
 * @version 1.0
 * @since 1.0
 */
public interface DeviceRepository extends MongoRepository<Device, String> {

    List<Device> findBySolutionId(String solutionId);

    List<Device> findBySolutionIdIn(List<String> ids);
}
