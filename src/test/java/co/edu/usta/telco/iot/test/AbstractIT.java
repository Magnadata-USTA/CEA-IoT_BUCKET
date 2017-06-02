package co.edu.usta.telco.iot.test;

import co.edu.usta.telco.iot.data.model.Capture;
import co.edu.usta.telco.iot.data.model.Device;
import co.edu.usta.telco.iot.data.model.Sensor;
import co.edu.usta.telco.iot.data.model.Solution;
import co.edu.usta.telco.iot.data.repository.CaptureRepository;
import co.edu.usta.telco.iot.data.repository.DeviceRepository;
import co.edu.usta.telco.iot.data.repository.SensorRepository;
import co.edu.usta.telco.iot.data.repository.SolutionRepository;
import java.util.Arrays;
import java.util.Date;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Commons integration test operations.
 *
 * @version 1.0
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class AbstractIT {

    @Autowired
    protected SolutionRepository solutionRepository;
    @Autowired
    protected DeviceRepository deviceRepository;
    @Autowired
    protected SensorRepository sensorRepository;
    @Autowired
    protected CaptureRepository captureRepository;

    @After
    public void after() {
        clearResources();
    }

    @Before
    public void before() {
        clearResources();
    }

    /**
     * Creates a persisted sample fill solution data with following the configuration:
     * <pre>
     * solution1{
     *   device1{
     *     sensor1{randomCapture, randomCapture},
     *     sensor2{randomCapture, randomCapture}
     *   }
     *   device2{
     *     sensor3{randomCapture, randomCapture}
     *   }
     * }
     * </pre>
     *
     * @return {@link Solution} created.
     */
    protected Solution createFullSolution() {
        Solution solution = Solution.builder().name(Samples.SOLUTION_NAME).build();
        solutionRepository.save(solution);
        Device device1 = Device.builder().name(Samples.DEVICE_1_NAME)
                .solutionId(solution.getId()).build();
        Device device2 = Device.builder().name(Samples.DEVICE_2_NAME)
                .solutionId(solution.getId()).build();
        deviceRepository.save(device1);
        deviceRepository.save(device2);
        solution.getDevices().addAll(Arrays.asList(device1, device2));
        Sensor[] sensors = {Sensor.builder().name(Samples.SENSOR_1_NAME)
            .deviceId(device1.getId()).build(),
            Sensor.builder().name(Samples.SENSOR_2_NAME)
            .deviceId(device1.getId()).build(),
            Sensor.builder().name(Samples.SENSOR_3_NAME)
            .deviceId(device2.getId()).build()};
        device1.getSensors().addAll(Arrays.asList(sensorRepository.save(sensors[0]),
                sensorRepository.save(sensors[1])));
        device2.getSensors().add(sensorRepository.save(sensors[2]));

        for (Sensor sensor : sensors) {
            sensor.getCaptures().addAll(Arrays.asList(
                    captureRepository.save(Capture.builder()
                            .captureDate(new Date())
                            .captureTypeName(Samples.CAPTURE_TEST_TYPE)
                            .value(RandomStringUtils.randomNumeric(5))
                            .sensorId(sensor.getId()).build()),
                    captureRepository.save(Capture.builder()
                            .captureDate(new Date())
                            .captureTypeName(Samples.CAPTURE_TEST_TYPE)
                            .value(RandomStringUtils.randomNumeric(5))
                            .sensorId(sensor.getId()).build())
            ));
        }

        return solution;
    }

    /**
     * Clears all database data.
     */
    private void clearResources() {
        captureRepository.deleteAll();
        sensorRepository.deleteAll();
        deviceRepository.deleteAll();
        solutionRepository.deleteAll();
    }
}
