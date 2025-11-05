package org.hibernate.bugs;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class PersonData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(mappedBy = "personData")
    private Set<PersonDataOffice> personDataOfficeCollection;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<PersonDataOffice> getPersonDataOfficeCollection() {
        return personDataOfficeCollection;
    }

    public void setPersonDataOfficeCollection(Set<PersonDataOffice> personDataOfficeCollection) {
        this.personDataOfficeCollection = personDataOfficeCollection;
    }
}
