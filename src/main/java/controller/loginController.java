package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.User;
import util.connectionFactory;
import view.LoginScreen;
import view.MainScreen;
import controller.registerController;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

public class loginController {

    private LoginScreen view;
    private Connection connection;
    private PreparedStatement statement;

    public loginController(LoginScreen view) {
        this.view = view;
    }

//    public boolean authenticateUserPassword(User autUser){
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        
//        entityManager.getTransaction().begin();
//        entityManager.
//        entityManager.getTransaction().commit();
//    }
    public boolean authenticateUserPassword(User autUser) throws SQLException {
        System.out.println(autUser);
        String sql = "SELECT * FROM users WHERE login = ? and password = ?";
        Connection connection = null;

        ResultSet resultSet = null;
        try {
            connection = connectionFactory.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, autUser.getUser()); //
            statement.setString(2, autUser.getPassword()); //

            resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro autentificar o usuario " + ex.getMessage(), ex);
        } finally {
            connectionFactory.closeConnection(connection, statement);
        }
    }

    public void authenticate() throws SQLException {

        String user = view.getjTextFieldUser().getText();
        String password = String.valueOf(view.getjPasswordField().getPassword());

        User autUser = new User(user, password);
        System.out.println(user + password);
        Connection connection = new connectionFactory().getConnection();
        registerController registerController = new registerController();
        connection = null;
        statement = null;

        boolean autTrue = authenticateUserPassword(autUser);

        if (autTrue) {
            MainScreen mainScreen = new MainScreen();
            mainScreen.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(view, "Invalid username or password");
        }
    }

}
