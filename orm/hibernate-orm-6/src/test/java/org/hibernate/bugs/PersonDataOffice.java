package org.hibernate.bugs;

import jakarta.persistence.*;

@Entity
public class PersonDataOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OFFICE_ID")
    private Office office;

    @ManyToOne
    @JoinColumn(name = "PERSON_DATA_ID")
    private PersonData personData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public PersonData getPersonData() {
        return personData;
    }

    public void setPersonData(PersonData personData) {
        this.personData = personData;
    }
}
