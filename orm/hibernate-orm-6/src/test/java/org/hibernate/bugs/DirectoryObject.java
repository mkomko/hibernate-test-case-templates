package org.hibernate.bugs;

import jakarta.persistence.*;

@Entity
@Table(name = "DIRECTORY_OBJECT")
@NamedQueries(
        @NamedQuery(name = "DirectoryObject.selectAll",
                query = "SELECT dirObject FROM DirectoryObject dirObject"))
public class DirectoryObject
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "DIRECTORY_OBJECT_DATA_ID")
    private DirectoryObjectData data;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public DirectoryObjectData getData()
    {
        return data;
    }

    public void setData(DirectoryObjectData data)
    {
        this.data = data;
    }
}
