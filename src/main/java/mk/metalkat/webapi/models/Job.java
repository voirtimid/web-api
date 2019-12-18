package mk.metalkat.webapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Job {

    @Id
    @Column(name = "job_id")
    private Long jobId;

    private String name;

    @OneToMany(mappedBy = "job")
    private List<Task> tasks;
}
