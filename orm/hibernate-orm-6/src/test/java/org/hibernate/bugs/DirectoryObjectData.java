package org.hibernate.bugs;

import jakarta.persistence.*;

@Entity
@Table(name = "DIRECTORY_OBJECT_DATA")
public class DirectoryObjectData
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "DIRECTORY_OBJECT_ID")
    private DirectoryObject object;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public DirectoryObject getObject()
    {
        return object;
    }

    public void setObject(DirectoryObject object)
    {
        this.object = object;
    }
}
