package co.edu.usta.telco.iot.service;

import static org.junit.Assert.*;

import co.edu.usta.telco.iot.data.model.Solution;
import co.edu.usta.telco.iot.test.AbstractIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Integration tests for {@link DataCleanerService}.
 *
 * @version 1.0
 * @since 1.0
 */
public class DataCleanerServiceIT extends AbstractIT {

    @Autowired
    private DataCleanerService dataCleanerService;

    /**
     * Method {@link DataCleanerService#deleteSolutionById}
     * <p>
     * With dependents child data.
     * <p>
     * Should clear all dependens repository data.
     */
    @Test
    public void deleteSolutionById_WithDependents_ShouldDeletesAllDependents() {
        Solution fullSolution = createFullSolution();

        dataCleanerService.deleteSolutionById(fullSolution.getId());

        assertEquals(0, captureRepository.count());
        assertEquals(0, sensorRepository.count());
        assertEquals(0, deviceRepository.count());
        assertEquals(0, solutionRepository.count());
    }
}
