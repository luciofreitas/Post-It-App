package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Tasks;
import util.connectionFactory;

public class tasksController {

    public void save(Tasks task) {

        String sql = "INSERT INTO tasks (id_project,"
                + "name,"
                + " description,"
                + " status,"
                + " note,"
                + " deadline,"
                + " createdAt,"
                + " updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getId_project());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isStatus());
            statement.setString(5, task.getNote());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setTimestamp(7, new java.sql.Timestamp(task.getCreatedAt().getTime()));
            statement.setTimestamp(8, new java.sql.Timestamp(task.getUpdatedAt().getTime()));

            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir a tarefa " + ex.getMessage(), ex);
        } finally {
            connectionFactory.closeConnection(connection, statement);
        }
    }

    public void update(Tasks task) {
        System.out.println(task.toString());
        String sql = "UPDATE tasks SET "
                + "name= ?, "
                + "description= ?, "
                + "status= ?, "
                + "note= ?, "
                + "deadline= ?, "
                + "createdAt= ?, "
                + "updatedAt= ?,"
                + "id_project= ? "
                + "WHERE id= ? ";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            //Estabelecendo a conexão com o banco de dados
            connection = connectionFactory.getConnection();

            //Preparando a query
            statement = connection.prepareStatement(sql);

            //Setando os valores do statement
            statement.setString(1, task.getName());
            statement.setString(2, task.getDescription());
            statement.setBoolean(3, task.isStatus());
            statement.setString(4, task.getNote());
            statement.setDate(5, new Date(task.getDeadline().getTime()));
            statement.setDate(6, new Date(task.getCreatedAt().getTime()));
            statement.setDate(7, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(8, task.getId_project());
            statement.setInt(9, task.getId());

            //Executando a query
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa " + ex.getMessage(), ex);
        } finally {
            connectionFactory.closeConnection(connection, statement);
        }
    }

    public void removeById(int taskId) {
        String sql = "DELETE FROM tasks WHERE id =?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            //Criação da conexão com o banco de dados
            connection = connectionFactory.getConnection();

            //Preparando a query
            statement = connection.prepareStatement(sql);

            //Setando os valores
            statement.setInt(1, taskId);

            //Executando a query
            statement.execute();
        } catch (SQLException ex) {
        } finally {
            connectionFactory.closeConnection(connection, statement);
        }

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
