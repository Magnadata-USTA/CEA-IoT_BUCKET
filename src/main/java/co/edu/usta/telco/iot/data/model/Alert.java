package co.edu.usta.telco.iot.data.model;

import co.edu.usta.telco.iot.data.enumType.AlertType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 */
@Document
public class Alert implements Serializable {

    @Id
    private String id;

    private String name;

    private String value;

    private AlertType alertType;

}
