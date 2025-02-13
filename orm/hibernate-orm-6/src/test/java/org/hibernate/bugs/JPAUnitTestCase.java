package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
class JPAUnitTestCase
{
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void init()
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
    }

    @AfterEach
    void destroy()
    {
        entityManagerFactory.close();
    }

    // Entities are auto-discovered, so just add them anywhere on class-path
    // Add your tests, using standard JUnit.
    @Test
    void hhh123Test() throws Exception
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        IntermediateEntity intermediateEntity = new IntermediateEntity();
        intermediateEntity.setName("IntermediateEntity");
        entityManager.persist(intermediateEntity);

        ChildEntity childEntity = new ChildEntity();
        childEntity.setName("ChildEntity");
        childEntity.setIntermediateEntity(intermediateEntity);
        entityManager.persist(childEntity);

        ParentEntity parentEntity = new ParentEntity();
        parentEntity.setName("ParentEntity");
        parentEntity.setIntermediateEntity(intermediateEntity);
        entityManager.persist(parentEntity);

        entityManager.getTransaction().commit();
        entityManager.close();

        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.find(ParentEntity.class, parentEntity.getId());
        List<ParentEntity> parentEntities = entityManager.createNamedQuery("ParentEntity.select", ParentEntity.class).getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        //
        // The following produces a LazyInitializationException on 6.6 but not on 6.4
        //
        for (ParentEntity entity : parentEntities)
            System.out.println(entity.getIntermediateEntity().getChildEntities().get(0).getName());
    }
}
