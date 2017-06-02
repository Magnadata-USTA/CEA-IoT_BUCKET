package co.edu.usta.telco.iot.aspect.alert;

import co.edu.usta.telco.iot.config.MailSenderImpl;
import co.edu.usta.telco.iot.data.model.Alert;
import co.edu.usta.telco.iot.data.model.Capture;
import co.edu.usta.telco.iot.data.model.Sensor;
import co.edu.usta.telco.iot.data.repository.AlertRepository;
import co.edu.usta.telco.iot.data.repository.SensorRepository;
import co.edu.usta.telco.iot.exception.BusinessException;
import co.edu.usta.telco.iot.rule.RuleDataType;
import co.edu.usta.telco.iot.rule.RuleUtil;
import co.edu.usta.telco.iot.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 */
//@Aspect
@Service
public class AlertAspectService {
    Logger LOGGER = LoggerFactory.getLogger(AlertAspectService.class);

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MailSenderImpl mailSender;

    @Pointcut("execution(public * .*.CaptureRepository+.*(..))")
    public void beanRepo() {}

    @After("beanRepo()")
    public void afterCreatedCapture(JoinPoint joinPoint) {
        LOGGER.info("**** Executing alert for: " + joinPoint);
        Capture capture = (Capture) joinPoint.getArgs()[0];
        afterCreatedCapture(capture);
    }

    public void afterCreatedCapture(Capture capture) {
        LOGGER.info("**** Executing capture: " + capture);
        List<Alert> alertList = alertRepository.findBySensorId(capture.getSensorId());

        for (Alert alert : alertList) {
            String condition = alert.getConditions().get(0);
            String actionValue = alert.getActionValue();
            String actionContent = alert.getActionContent();

            // Replace in action value
            if (StringUtils.isNotBlank(actionValue) && actionValue.contains("{{sensorName}}")) {
                Sensor sensor = sensorRepository.findOne(capture.getSensorId());
                String sensorName = encodeUrl(sensor.getName());
                actionValue = actionValue.replace("{{sensorName}}", sensorName);
            }

            if (StringUtils.isNotBlank(actionValue) && actionValue.contains("{{BODY}}")) {
                String captureValue = encodeUrl(capture.getValue());
                actionValue = actionValue.replace("{{BODY}}", captureValue);
            }

            // Replace in action content
            if (StringUtils.isNotBlank(actionContent) && actionContent.contains("{{BODY}}")) {
                String captureValue = encodeUrl(capture.getValue());
                actionContent = actionContent.replace("{{BODY}}", captureValue);
            }

            // Replace in condition
            if (StringUtils.isNotBlank(condition) && condition.contains("{{BODY}}")) {
                String captureValue = encodeUrl(capture.getValue());
                condition = condition.replace("{{BODY}}", captureValue);
            }

            if ( ! evaluateCondition(condition, RuleDataType.NUMBER)) { /// TODO: support other datatypes than number
                LOGGER.debug("The condition evaluated to false : " + condition);
                return;
            }

            switch (alert.getAlertType()) {
                case POST:
                    //post to url
                    HttpClient client = HttpClientBuilder.create().build();
                    // TODO: add headers
                    HttpPost post = new HttpPost(actionValue);
                    LOGGER.info("*** posting to URL " + actionValue);
                    post.setEntity(EntityBuilder.create().setText(StringUtils.isEmpty(actionContent)?"DUMMY":actionContent).build()); // TODO: allow empty post without dummy
                    executePost(client, post);
                    break;
                case EMAIL:
                    String email = userService.getLoggedUser();
                    sendMail(alert, actionContent, email);
                    break;
            }

        }
    }

    private String encodeUrl(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Error encoding url parameters {}", e);
        }
        return "";
    }

    private boolean evaluateCondition(String condition, RuleDataType dataType) {
        return RuleUtil.evaluate(condition, dataType);
    }

    private void sendMail(Alert alert, String actionContent, String email){
        try {
            mailSender.send(email,"Alert active" + alert.getName(), actionContent);
        } catch (BusinessException exception) {
            LOGGER.error("Error executing post {}", exception);
        }
    }

    private void executePost(HttpClient client, HttpPost post) {
        try {
            client.execute(post);
        } catch (IOException exception) {
            LOGGER.error("Error executing post {}", exception);
        }
    }
}
