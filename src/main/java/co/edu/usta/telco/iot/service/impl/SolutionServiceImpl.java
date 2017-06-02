package co.edu.usta.telco.iot.service.impl;

import co.edu.usta.telco.iot.data.model.Solution;
import co.edu.usta.telco.iot.data.model.User;
import co.edu.usta.telco.iot.data.repository.SolutionRepository;
import co.edu.usta.telco.iot.data.repository.UserRepository;
import co.edu.usta.telco.iot.service.SolutionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class SolutionServiceImpl implements SolutionService {

    private static final Logger LOG = LoggerFactory.getLogger(SolutionServiceImpl.class);

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean validatePermissionsForSolution(String login, String solutionId) {

        User user = userRepository.findByLogin(login);

        if (user == null || user.getLogin() == null || solutionId == null) {
            LOG.debug("user not found");
            return false;
        }

        Solution solution = solutionRepository.findOne(solutionId);

        if (StringUtils.isNotBlank(solutionId) && StringUtils.equals( user.getLogin(), solution.getLogin() ) ) {
            return true;
        }

        return false;
    }
}

