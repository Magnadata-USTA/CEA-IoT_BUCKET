package co.edu.usta.telco.iot.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Felipe on 27/09/2016.
 */
@Document
public class Capture implements Serializable, Persistable<String> {

    @Id
    private String id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date captureDate;
    @CreatedDate
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date saveDate;
    private String value;

    private String sensorId;

    private String captureTypeName;

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

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getCaptureTypeName() {
        return captureTypeName;
    }

    public void setCaptureTypeName(String captureTypeName) {
        this.captureTypeName = captureTypeName;
    }
}
