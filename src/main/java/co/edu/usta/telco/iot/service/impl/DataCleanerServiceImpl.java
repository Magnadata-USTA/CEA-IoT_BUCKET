package co.edu.usta.telco.iot.service.impl;

import co.edu.usta.telco.iot.data.repository.CaptureRepository;
import co.edu.usta.telco.iot.data.repository.DeviceRepository;
import co.edu.usta.telco.iot.data.repository.SensorRepository;
import co.edu.usta.telco.iot.data.repository.SolutionRepository;
import co.edu.usta.telco.iot.service.DataCleanerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link DataCleanerService} service implementation.
 *
 * @version 1.0
 * @since 1.0
 */
@Service
public class DataCleanerServiceImpl implements DataCleanerService {

    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private CaptureRepository captureRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private SolutionRepository solutionRepository;

    @Override
    public void deleteSensorById(String sensorId) {
        captureRepository.deleteBySensorId(sensorId);
        sensorRepository.delete(sensorId);
    }

    @Override
    @Transactional
    public void deleteDeviceById(String deviceId) {
        deviceRepository.delete(deviceId);
        sensorRepository.findByDeviceId(deviceId)
                .stream().map(sensor -> sensor.getId())
                .forEach(this::deleteSensorById);
    }

    @Override
    public void deleteSolutionById(String solutionId) {
        solutionRepository.delete(solutionId);
        deviceRepository.findBySolutionId(solutionId)
                .stream().map(solution -> solution.getId())
                .forEach(this::deleteDeviceById);
    }

}
