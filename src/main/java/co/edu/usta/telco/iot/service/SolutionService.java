package co.edu.usta.telco.iot.service;

import co.edu.usta.telco.iot.data.model.Solution;
import co.edu.usta.telco.iot.data.model.User;

/**
 */
public interface SolutionService {

    boolean validatePermissionsForSolution(String login, String solutionId);

}