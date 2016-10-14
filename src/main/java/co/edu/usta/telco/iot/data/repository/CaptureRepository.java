package co.edu.usta.telco.iot.data.repository;

import co.edu.usta.telco.iot.data.model.Capture;
import co.edu.usta.telco.iot.data.model.Thing;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Felipe on 29/09/2016.
 */
public interface CaptureRepository extends MongoRepository<Capture, String>{
    public List<Capture> findAll();
    public List<Capture> findByDeviceId(String deviceId);

}
