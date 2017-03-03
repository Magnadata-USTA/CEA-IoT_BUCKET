package co.edu.usta.telco.iot.data.repository;

import co.edu.usta.telco.iot.data.model.Device;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Device repository
 * Created by Felipe on 29/09/2016.
 */
public interface DeviceRepository extends MongoRepository<Device, String>{
    List<Device> findAll();
    List<Device> findBySolutionId(String solutionId);
}
