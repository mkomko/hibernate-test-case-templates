package org.hibernate.bugs;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class PhaseDescription {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "description")
    @Fetch(value = FetchMode.SELECT)
    private Phase phase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
