package co.edu.usta.telco.iot.service;

import co.edu.usta.telco.iot.data.model.Capture;
import co.edu.usta.telco.iot.data.model.Device;
import co.edu.usta.telco.iot.data.model.Sensor;
import co.edu.usta.telco.iot.data.model.Solution;

/**
 * Deletes data with cascade delete.
 *
 * @version 1.0
 * @since 1.0
 */
public interface DataCleanerService {

    /**
     * Deletes device by the ID cascading {@link Sensor} data delete.
     *
     * @param deviceId the {@link Device#id} to delete.
     * @see #deleteSensorById(java.lang.String) 
     */
    void deleteDeviceById(String deviceId);

    /**
     * Deletes sensor by the ID with its related {@link Capture} data.
     *
     * @param sensorId the {@link Sensor#id} to delete.
     */
    void deleteSensorById(String sensorId);

    /**
     * Deletes solution by the ID cascading {@link Device} data delete.
     *
     * @param solutionId the {@link Solution#id} to delete.
     * @see #deleteDeviceById(java.lang.String) 
     */
    void deleteSolutionById(String solutionId);
}
