package co.edu.usta.telco.iot.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Capture entity. 
 * 
 * @author Felipe on 27/09/2016.
 * @version 1.0
 * @since 1.0
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode(of = "id")
@Document
public class Capture implements Serializable, Persistable<String> {

    /** Class serial version. */
    private static final long serialVersionUID = -6381978135490634695L;

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

    @JsonIgnore
    @Override
    public boolean isNew() {
        return true;
    }
}
