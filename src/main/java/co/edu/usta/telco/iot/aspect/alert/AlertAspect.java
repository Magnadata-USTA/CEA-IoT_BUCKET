package co.edu.usta.telco.iot.aspect.alert;

import co.edu.usta.telco.iot.data.model.Capture;
import co.edu.usta.telco.iot.data.repository.AlertRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

/**
 */
@Aspect
public class AlertAspect {
    @Autowired
    private AlertRepository alertRepository;

    @After("execution(* co.edu.usta.telco.iot.data.repository.CaptureRepository.save(..))")
    public void afterCreatedCapture(JoinPoint joinPoint) {
        Capture capture = (Capture) joinPoint.getArgs()[0];
        alertRepository.findBySensorId(capture.getSensorId());
        // query all the configured alerts . (POST for the moment)
        // execute the current alerts
    }
}
