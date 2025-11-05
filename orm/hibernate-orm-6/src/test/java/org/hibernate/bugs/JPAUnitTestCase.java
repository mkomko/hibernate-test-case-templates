package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
class JPAUnitTestCase {

    private EntityManagerFactory entityManagerFactory;
    private List<Person> personCache;

    @BeforeEach
    void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
        personCache = new ArrayList<>();
    }

    @AfterEach
    void destroy() {
        entityManagerFactory.close();
        personCache = null;
    }

    // Entities are auto-discovered, so just add them anywhere on class-path
    // Add your tests, using standard JUnit.
    @Test
    void hhh123Test() throws Exception {
        persistOfficeAndPersonEntities();
        selectPersonAndAddToCache();
        createAssignmentItemAndQuery();
    }

    private void persistOfficeAndPersonEntities() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Office office = new Office();
        office.setName("Office1");
        entityManager.persist(office);

        PersonData personData = new PersonData();
        personData.setPersonDataOfficeCollection(new HashSet<>());

        PersonDataOffice personDataOffice = new PersonDataOffice();
        personDataOffice.setOffice(office);
        personDataOffice.setPersonData(personData);

        personData.getPersonDataOfficeCollection().add(personDataOffice);
        entityManager.persist(personDataOffice);
        entityManager.persist(personData);

        Person person = new Person();
        person.setPersonData(personData);
        entityManager.persist(person);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private void selectPersonAndAddToCache() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Person person = entityManager.find(Person.class, 1);
        personCache.add(person);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private void createAssignmentItemAndQuery() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        // -----------------------------------------------------------------------------
        //  If the following query is executed, the LazyInitializationException is thrown below
        // -----------------------------------------------------------------------------
        entityManager.createQuery("SELECT p FROM Person p", Person.class).getResultList();

        AssignmentItem assignmentItem = new AssignmentItem();
        Person cachedPerson = personCache.get(0);
        assignmentItem.setPerson(cachedPerson);
        entityManager.persist(assignmentItem);

        List<AssignmentItem> assignmentItems = entityManager.createQuery("SELECT a FROM AssignmentItem a LEFT JOIN FETCH a.person p LEFT JOIN FETCH p.personData", AssignmentItem.class).getResultList();

        // -----------------------------------------------------------------------------
        //  The following line throws the LazyInitializationException
        // -----------------------------------------------------------------------------
        System.out.println("Size: " + assignmentItems.get(0).getPerson().getPersonData().getPersonDataOfficeCollection().size());

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
