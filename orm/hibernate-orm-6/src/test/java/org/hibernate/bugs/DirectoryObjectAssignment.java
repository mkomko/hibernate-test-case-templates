package org.hibernate.bugs;

import jakarta.persistence.*;

@Entity
@Table(name = "DIRECTORY_OBJECT_ASSIGNMENT")
@EntityListeners({DirectoryObjectAssignmentEntityListener.class})
public class DirectoryObjectAssignment
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PARENT_DIRECTORY_OBJECT_ID")
    private DirectoryObject parent;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CHILD_DIRECTORY_OBJECT_ID")
    private DirectoryObject child;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public DirectoryObject getParent()
    {
        return parent;
    }

    public void setParent(DirectoryObject parent)
    {
        this.parent = parent;
    }

    public DirectoryObject getChild()
    {
        return child;
    }

    public void setChild(DirectoryObject child)
    {
        this.child = child;
    }
}
