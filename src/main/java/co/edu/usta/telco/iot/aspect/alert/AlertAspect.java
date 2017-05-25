package co.edu.usta.telco.iot.aspect.alert;

import co.edu.usta.telco.iot.config.MailSenderImpl;
import co.edu.usta.telco.iot.data.model.Alert;
import co.edu.usta.telco.iot.data.model.Capture;
import co.edu.usta.telco.iot.data.repository.AlertRepository;
import co.edu.usta.telco.iot.exception.BusinessException;
import co.edu.usta.telco.iot.rule.RuleDataType;
import co.edu.usta.telco.iot.rule.RuleUtil;
import co.edu.usta.telco.iot.service.UserService;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

/**
 */
@Aspect
public class AlertAspect {
    Logger LOGGER = LoggerFactory.getLogger(AlertAspect.class);

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MailSenderImpl mailSender;

    @After("execution(* co.edu.usta.telco.iot.data.repository.CaptureRepository.save(..))")
    public void afterCreatedCapture(JoinPoint joinPoint) {
        Capture capture = (Capture) joinPoint.getArgs()[0];
        // ******* query all the configured alerts . (POST for the moment)
        List<Alert> alertList = alertRepository.findBySensorId(capture.getSensorId());

        for (Alert alert : alertList) {
            String condition = alert.getConditions().get(0);
            String actionValue = alert.getActionValue();
            String actionContent = alert.getActionContent();

            // alert.getRuleDataType()***

            if ( ! evaluateCondition(condition, null)) { /// **** null
                LOGGER.debug("The condition evaluated to false : " + condition);
                return;
            }
            if (actionContent.contains("$BODY")) {
                actionContent = actionContent.replace("$BODY", capture.getValue());
            }

            // ****** execute the current alerts
            switch (alert.getAlertType()) {
                case POST:
                    //post to url
                    HttpClient client = HttpClientBuilder.create().build();

                    // TODO: add headers
                    HttpPost post = new HttpPost(actionValue);
                    post.setEntity(EntityBuilder.create().setText(actionContent).build());
                    executePost(client, post);
                    // TODO: test post
                    break;
                case EMAIL:
                    String email = userService.getLoggedUser();
                    sendMail(alert, actionContent, email);
                    break;
            }

        }

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
