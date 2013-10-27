package mpi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TECHNOLOGIES")
public class Technology {

    @Id
    @Column(name="NAME")
    private String name;

    public Technology() {
    }
    
    public Technology(String name) {
        this();
        this.name = name;
    }

    public String name() {
        return name;
    }

}
