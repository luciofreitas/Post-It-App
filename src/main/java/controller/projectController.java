package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Project;
import util.connectionFactory;

public class projectController {

    private final EntityManager entityManager = null;

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

    public List<Project> removeProject(Integer id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Project project = entityManager.find(Project.class, id);
            entityManager.getTransaction().begin();
            entityManager.remove(project);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            System.err.println(e); 
            entityManager.getTransaction().rollback();
        }finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return null;
    }
//    public void removeProject(int id) {
//
//        String sql = "DELETE FROM project WHERE id = ?";
//        Connection connection = null;
//        PreparedStatement statement = null;
//
//        try {
//            connection = connectionFactory.getConnection();
//            statement = connection.prepareStatement(sql);
//            statement.setInt(1, id);
//            statement.execute();
//        } catch (Exception ex) {
//            throw new RuntimeException("Erro ao remover o projeto " + ex.getMessage(), ex);
//        } finally {
//            connectionFactory.closeConnection(connection, statement);
//        }
//    }

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
