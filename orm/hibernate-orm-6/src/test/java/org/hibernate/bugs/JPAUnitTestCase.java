package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.hibernate.bugs.model.Category;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase {

    private EntityManagerFactory entityManagerFactory;

    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
    }

    @After
    public void destroy() {
        entityManagerFactory.close();
    }

    // Entities are auto-discovered, so just add them anywhere on class-path
    // Add your tests, using standard JUnit.
    @Test
    public void hhh123Test() throws Exception {
        createEntities();

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<Category> categories =
                entityManager.createNamedQuery("Category.selectAll", Category.class).getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        Assert.assertNull(categories.get(0).getParentCategory());
        Assert.assertEquals("A", categories.get(1).getParentCategory().getName());
        Assert.assertNull(categories.get(2).getParentCategory());
    }

    private void createEntities() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Category categoryA = new Category();
        categoryA.setName("A");
        entityManager.persist(categoryA);

        Category categoryB = new Category();
        categoryB.setName("B");
        categoryB.setParentCategory(categoryA);
        entityManager.persist(categoryB);

        Category categoryC = new Category();
        categoryC.setName("C");
        entityManager.persist(categoryC);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
