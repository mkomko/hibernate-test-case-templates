package org.hibernate.bugs;

import jakarta.persistence.*;

@Entity
@Table(name = "RULE")
@NamedQueries(
        @NamedQuery(name = "Rule.selectAll",
                query = "SELECT rule FROM Rule rule"))
public class Rule
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
}
