package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import util.connectionFactory;
import view.RegisterScreen;

/**
 *
 * @author Lucio
 */
public class registerController {

    private Connection connection;

    public void save(User user) throws SQLException {
        String sql = "INSERT INTO users(login, "
                + "email, "
                + "password) VALUES (?, ?, ?)";
        
        PreparedStatement statement = null;
        try {
            connection = connectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUser());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(RegisterScreen.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connectionFactory.closeConnection(connection, statement);

        }

    }

    

}
