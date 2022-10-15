package controller;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Project;

public class projectController {

    private EntityManager entityManager = null;

    public void save(Project project) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(project);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    public void update(Project project) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.merge(project);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    public Project removeProject(Integer id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Project project = entityManager.find(Project.class, id);
            entityManager.getTransaction().begin();
            entityManager.remove(project.getId());
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            System.err.println(e);
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return null;
    }
    public List<Project> getAll() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            Query query = entityManager.createQuery("from Project");
            return query.getResultList();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
