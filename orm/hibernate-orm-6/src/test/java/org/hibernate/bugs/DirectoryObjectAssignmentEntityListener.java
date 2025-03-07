package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;

public class DirectoryObjectAssignmentEntityListener
{
    @PostPersist
    @PostUpdate
    @PostRemove
    public void onEntityChange(Object entity)
    {
        EntityManager entityManager = JPAUnitTestCase.entityManagerThreadLocal.get();
        entityManager.createNamedQuery("Rule.selectAll", Rule.class).getResultList();
    }
}
