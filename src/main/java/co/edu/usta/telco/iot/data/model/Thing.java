package co.edu.usta.telco.iot.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Felipe on 27/09/2016.
 */
@Document
public class Thing implements Serializable, Persistable<String> {

    @Id
    private String id;

    private String name;

    @Transient
    private List<Capture> captures;

    public List<Capture> getCaptures() {
        return captures;
    }

    public void setCaptures(List<Capture> captures) {
        this.captures = captures;
    }

    public void addCapture(Capture capture) {
        this.captures.add(capture);
    }

    //@JsonFormat(pattern="yyyy-MM-dd hh:mm")
    //private Date captureDate;
    //@CreatedDate
    //@JsonFormat(pattern="yyyy-MM-dd hh:mm")
    //private Date saveDate;
    //private String value;

    @JsonIgnore
    @Override
    public boolean isNew() {
        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

/*
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
    */
}
