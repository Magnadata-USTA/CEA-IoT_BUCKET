package co.edu.usta.telco.iot.service;

import co.edu.usta.telco.iot.data.model.User;
import org.springframework.stereotype.Service;

/**
 */
public interface UserService {
    User validateToken(String token);

    User activateToken(User user);

    User saveNewToken(User user);

    String getLoggedUser();

}
