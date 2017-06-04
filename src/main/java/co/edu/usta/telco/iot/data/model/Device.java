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
 * Device entity. 
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
public class Device implements Serializable {

    /** Class serial version. */
    private static final long serialVersionUID = 4891427062000408050L;

    @Id
    private String id;

    private String name;

    private String solutionId;

    @Transient
    private final List<Sensor> sensors = new ArrayList();
}
