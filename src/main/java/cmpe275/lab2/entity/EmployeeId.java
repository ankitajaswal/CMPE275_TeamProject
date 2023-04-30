package cmpe275.lab2.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Embeddable;
import javax.persistence.MapsId;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.List;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeId implements Serializable {

    @Column(name = "id")
    private long id;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "employer_id", referencedColumnName = "id", nullable = false)
    private Employer employer;
}
