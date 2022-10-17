package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Tasks;
import util.connectionFactory;

public class tasksController {

    private final EntityManager entityManager = null;

    public void save(Tasks task) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(task);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    public void update(Tasks task) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.merge(task);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }
    
    public Tasks removeById(Integer id){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Tasks task = entityManager.find(Tasks.class, id);
            entityManager.getTransaction().begin();
            entityManager.remove(task.getId());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e);         
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return null;
    }

    public List<Tasks> getAll(int id_Project) {
        String sql = "SELECT * FROM tasks WHERE id_project = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        //Lista de tarefas que será devolvida quando a chamada do método acontecer
        List<Tasks> tasks = new ArrayList<>();

        try {
            //Crianção da conexão
            connection = connectionFactory.getConnection();
            statement = connection.prepareStatement(sql);

            //Setando o valor que corresponde ao filtro de busca
            statement.setInt(1, id_Project);

            //Valor retornado pela execução da query
            resultSet = statement.executeQuery();

            //Enquanto houverem valores a serem percorridos no meu resultSet
            while (resultSet.next()) {
                Tasks task = new Tasks();
                task.setId(resultSet.getInt("id"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setStatus(resultSet.getBoolean("status"));
                task.setNote(resultSet.getString("note"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));
                task.setId_project(resultSet.getInt("id_project"));

                tasks.add(task);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar a tarefa <metodo getAll> ");
        } finally {
            connectionFactory.closeConnection(connection, statement, resultSet);
        }
        //Lista de tarefas que foi criada e carrega do banco de dados
        return tasks;

    }
}
