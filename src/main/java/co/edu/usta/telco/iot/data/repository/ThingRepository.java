package co.edu.usta.telco.iot.data.repository;

import co.edu.usta.telco.iot.data.model.Thing;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Felipe on 29/09/2016.
 */
public interface ThingRepository extends MongoRepository<Thing, String>{
    public List<Thing> findAll();

}
