package org.hibernate.bugs;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.ArrayList;
import java.util.List;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IntermediateEntity
{
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "intermediateEntity", cascade = CascadeType.ALL)
    private List<ChildEntity> childEntities = new ArrayList<>();

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

    public List<ChildEntity> getChildEntities()
    {
        return childEntities;
    }

    public void setChildEntities(List<ChildEntity> childEntities)
    {
        this.childEntities = childEntities;
    }
}
