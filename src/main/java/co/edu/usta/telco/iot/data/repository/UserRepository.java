package co.edu.usta.telco.iot.data.repository;

import co.edu.usta.telco.iot.data.model.Capture;
import co.edu.usta.telco.iot.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Felipe on 29/09/2016.
 */
public interface UserRepository extends MongoRepository<User, String>{
    public List<User> findAll();
}
