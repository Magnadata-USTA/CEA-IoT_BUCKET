package co.edu.usta.telco.iot.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Sensor entity.
 *
 * @author Felipe on 27/09/2016.
 * @version 1.0
 * @since 1.0
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode(of = "id")
@Document
public class Sensor implements Serializable {

    /** Class serial version */
    private static final long serialVersionUID = -5008896890718678222L;

    @Id
    private String id;

    private String name;

    private String deviceId;

    @Transient
    private final List<Capture> captures = new ArrayList<>();
}
