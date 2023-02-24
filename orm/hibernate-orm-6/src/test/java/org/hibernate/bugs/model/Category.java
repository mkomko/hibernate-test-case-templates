package org.hibernate.bugs.model;

import jakarta.persistence.*;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

@NamedQueries(
        {
                @NamedQuery(name = "Category.selectAll",
                        query = "   SELECT c" +
                                "     FROM Category c" +
                                "    ORDER BY c.name")
        })
@Entity
@BatchSize(size = 500)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Category {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn
    @Fetch(value = FetchMode.SELECT)
    private Category parentCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }
}
