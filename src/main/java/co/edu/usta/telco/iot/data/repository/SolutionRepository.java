package co.edu.usta.telco.iot.data.repository;

import co.edu.usta.telco.iot.data.model.Solution;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Solution repository.
 *
 * @version 1.0
 * @since 1.0
 */
public interface SolutionRepository extends MongoRepository<Solution, String> {

    List<Solution> findByLogin(String login);
}
