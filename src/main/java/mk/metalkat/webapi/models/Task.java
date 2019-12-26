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
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @OneToOne
    private Machine machine;

    @ManyToMany(mappedBy = "tasks")
    private List<Employee> employees;
}
