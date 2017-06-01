package co.edu.usta.telco.iot.web.validation;

import co.edu.usta.telco.iot.data.model.Alert;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 */
@Component
public class AlertValidator implements Validator {

    public boolean supports(Class clazz) {
        return Alert.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors)
    {
        Alert alert = (Alert) target;

        if ( alert.getConditions() == null || alert.getConditions().isEmpty() ) {
            errors.rejectValue("conditions", "error.alert.conditionRequired", "The condition is required.");
        }

        boolean anyEmpty = alert.getConditions().stream().anyMatch(item -> item != null &&  item.length() > 0);
        if (anyEmpty) {
            errors.rejectValue("conditions", "error.alert.conditionNotValid", "The condition does not have a valid.");
        }

    }

}
