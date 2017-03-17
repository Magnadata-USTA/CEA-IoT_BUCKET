package co.edu.usta.telco.iot.service.impl;

import co.edu.usta.telco.iot.data.model.User;
import co.edu.usta.telco.iot.data.repository.UserRepository;
import co.edu.usta.telco.iot.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 */
@Service
public class UserServiceImpl implements UserService{

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User validateToken(String token) {
        if (StringUtils.isEmpty(token)) {
            LOG.debug("Error: userToken not found on request");
            return null;
        }
        User user = userRepository.findByToken(token);
        if (Objects.isNull(user)) {
            LOG.debug("Error: User not found in database. userToken " + token);
            return null;
        }
        return user;
    }
}
