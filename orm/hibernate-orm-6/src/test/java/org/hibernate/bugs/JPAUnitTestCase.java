package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
class JPAUnitTestCase
{
    private EntityManagerFactory entityManagerFactory;
    public static ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();

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
        List<DirectoryObject> directoryObjects = new ArrayList<>();
        List<DirectoryObjectAssignment> directoryObjectAssignments = new ArrayList<>();

        // -----------------------------------------------------------------------------
        //  Create 120 directory objects with a data object
        // -----------------------------------------------------------------------------
        for (int i = 1; i <= 120; i++)
        {
            DirectoryObject object = new DirectoryObject();
            DirectoryObjectData data = new DirectoryObjectData();
            object.setData(data);
            directoryObjects.add(object);
        }

        // -----------------------------------------------------------------------------
        //  Create assignments between some of those data objects
        // -----------------------------------------------------------------------------
        for (int i = 1; i <= 5; i++)
        {
            for (int j = (5 + (i * 2)); j <= ((5 + (i * 2)) + 2); j++)
            {
                DirectoryObjectAssignment assignment = new DirectoryObjectAssignment();
                assignment.setParent(directoryObjects.get(i));
                assignment.setChild(directoryObjects.get(j));
                directoryObjectAssignments.add(assignment);
            }
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManagerThreadLocal.set(entityManager);

        // -----------------------------------------------------------------------------
        //  Persist the directory objects and their data objects
        // -----------------------------------------------------------------------------
        for (DirectoryObject directoryObject : directoryObjects)
        {
            entityManager.persist(directoryObject);
            DirectoryObjectData data = directoryObject.getData();
            data.setObject(directoryObject);
            entityManager.persist(data);
        }

        // -----------------------------------------------------------------------------
        //  It would work when flushing here:
        // -----------------------------------------------------------------------------
        //entityManager.flush();

        // -----------------------------------------------------------------------------
        //  Persist the assignments. Every persist calls the DirectoryObjectAssignmentEntityListener.
        // -----------------------------------------------------------------------------
        for (DirectoryObjectAssignment directoryObjectAssignment : directoryObjectAssignments)
        {
            entityManager.persist(directoryObjectAssignment);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerThreadLocal.remove();

        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManagerThreadLocal.set(entityManager);

        // -----------------------------------------------------------------------------
        //  Every directory object should have their data object set,
        //  but it's not the case
        // -----------------------------------------------------------------------------
        List<DirectoryObject> directoryObjectsFromDB = entityManager.createNamedQuery("DirectoryObject.selectAll", DirectoryObject.class).getResultList();
        for (DirectoryObject directoryObject : directoryObjectsFromDB)
        {
            assertThat(directoryObject.getData()).isNotNull();
        }

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerThreadLocal.remove();
    }
}
