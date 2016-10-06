package co.edu.usta.telco.iot.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.lang.annotation.Documented;
import java.util.Date;

/**
 * Created by Felipe on 27/09/2016.
 */
@Document
public class Thing implements Serializable, Persistable<String> {

    @Id
    private String id;
    private String deviceId;
    private Date captureDate;
    @CreatedDate
    private Date saveDate;
    private String value;

    public String getId() {
        return id;
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return true;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getCaptureDate() {
        return captureDate;
    }

    public void setCaptureDate(Date captureDate) {
        this.captureDate = captureDate;
    }

    public Date getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }
}
