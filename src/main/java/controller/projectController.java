package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
//    public void save(Project project) {
//        String sql = "INSERT INTO project (name, "
//                + " description, "
//                + " createdAt, "
//                + " updatedAt) VALUES (?, ?, ?, ?)";
//
//        Connection connection = null;
//        PreparedStatement statement = null;
//
//        try {
//            connection = connectionFactory.getConnection();
//            statement = connection.prepareStatement(sql);
//
//            statement.setString(1, project.getName());
//            statement.setString(2, project.getDescription());
//            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
//            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
//            statement.execute();
//        } catch (Exception ex) {
//            throw new RuntimeException("Erro ao inserir o projeto " + ex.getMessage(), ex);
//        } finally {
//            connectionFactory.closeConnection(connection, statement);
//        }
//    }

    public void update(Project project) {
        String sql = "UPDATE project SET"
                + "name = ?,"
                + "description = ?,"
                + "createdAt = ?,"
                + "updatedAt = ?,"
                + "WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao alterar o projeto " + ex.getMessage(), ex);
        } finally {
            connectionFactory.closeConnection(connection, statement);
        }
    }

    public void removeProject(int id) {

        String sql = "DELETE FROM project WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            System.out.println("aqui o id " + id);
            connection = connectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao remover o projeto " + ex.getMessage(), ex);
        } finally {
            connectionFactory.closeConnection(connection, statement);
        }
    }

    public void removeById(int id) {
        System.out.println("aquii " + id);
        String sql = "DELETE FROM project WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao remover o projeto " + ex.getMessage(), ex);
        } finally {
            connectionFactory.closeConnection(connection, statement);
        }
    }

    public List<Project> getAll() {
        String sql = "SELECT * FROM project";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Project> projects = new ArrayList<>();

        try {
            connection = connectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));

                projects.add(project);
            }

        } catch (Exception ex) {

            throw new RuntimeException("Vai ligar o banco de dados burrão ");
        }

        return projects;
    }
}
