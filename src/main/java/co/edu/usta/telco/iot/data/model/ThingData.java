package co.edu.usta.telco.iot.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Felipe on 27/09/2016.
 */
@Document
public class ThingData implements Serializable {
    public List<Thing> getData() {
        return data;
    }

    public void setData(List<Thing> data) {
        this.data = data;
    }

    private List<Thing> data;
}
