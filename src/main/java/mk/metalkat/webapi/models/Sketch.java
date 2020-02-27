package mk.metalkat.webapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "sketches")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Sketch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sketchId;

    @NotNull
    private String sketchName;

    private String companyName;

    private String companyInfo;

    private String imageFilename;

    private String technologyFilename;

    private String myTechnologyFilename;

    private String measuringListFilename;

    private String myMeasuringListFilename;

    private String gcodeFilename;

    private String usedTools;

    private Integer minutesForPiece;

}
