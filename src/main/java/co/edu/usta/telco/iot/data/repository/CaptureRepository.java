package co.edu.usta.telco.iot.data.repository;

import co.edu.usta.telco.iot.data.model.Capture;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Capture repository.
 *
 * @author Felipe on 29/09/2016.
 * @version 1.0
 * @since 1.0
 */
public interface CaptureRepository extends MongoRepository<Capture, String> {

    List<Capture> findAllByOrderBySaveDateDesc();

    List<Capture> findAllBySensorIdOrderBySaveDateDesc(String sensorId, Pageable pageable);

    List<Capture> findBySensorId(String sensorId);

    List<Capture> findBySensorIdOrderBySaveDateDesc(String sensorId, Pageable pageable);

    @Query("{ 'captureDate' :   { $gte :  ?0, $lte :  ?1 } }")
    List<Capture> findBetweenCaptureDates(Date startDate, Date endDate);

    void deleteBySensorId(String sensorId);

}
