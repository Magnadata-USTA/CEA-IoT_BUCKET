package co.edu.usta.telco.iot.data.repository;

import co.edu.usta.telco.iot.data.model.Solution;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 */
public interface SolutionRepository extends MongoRepository<Solution, String>{
    List<Solution> findAll();
}
