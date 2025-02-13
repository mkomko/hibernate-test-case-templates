package org.hibernate.bugs;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@NamedQuery(name = "ParentEntity.select",
        query = "SELECT pe" +
                "  FROM ParentEntity pe" +
                " INNER JOIN FETCH pe.intermediateEntity ie" +
                "  LEFT JOIN FETCH ie.childEntities")
public class ParentEntity
{
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn
    @Fetch(value = FetchMode.SELECT)
    private IntermediateEntity intermediateEntity;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public IntermediateEntity getIntermediateEntity()
    {
        return intermediateEntity;
    }

    public void setIntermediateEntity(IntermediateEntity intermediateEntity)
    {
        this.intermediateEntity = intermediateEntity;
    }
}
