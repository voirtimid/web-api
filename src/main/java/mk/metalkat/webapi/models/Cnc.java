package mk.metalkat.webapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "cncs")
public class Cnc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cncId;

    private String fileName;
}