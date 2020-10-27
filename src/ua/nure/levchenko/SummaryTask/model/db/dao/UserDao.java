package ua.nure.levchenko.SummaryTask.model.db.dao;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.exception.DBException;
import ua.nure.levchenko.SummaryTask.exception.constants.Messages;
import ua.nure.levchenko.SummaryTask.model.db.DBManager;
import ua.nure.levchenko.SummaryTask.model.db.constants.Fields;
import ua.nure.levchenko.SummaryTask.model.db.constants.SQLCommands;
import ua.nure.levchenko.SummaryTask.model.entity.db.User;
import ua.nure.levchenko.SummaryTask.model.entity.enums.Language;
import ua.nure.levchenko.SummaryTask.model.entity.enums.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that implements methods
 * for working with DB for User entity.
 *
 * @author K.Levchenko
 */
public class UserDao implements DAO<User, Integer> {
    private static final Logger LOG = Logger.getLogger(UserDao.class);

    /**
     * Creates the user entity in dataBase.
     *
     * @param user object of User
     */
    @Override
    public void create(User user) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getLogin());
            pstmt.setString(3, user.getPassword());
            pstmt.setInt(4, user.getRole().getIntKey());
            pstmt.setInt(5, user.getLanguage().getIntKey());
            pstmt.setString(6, user.getEmail());
            pstmt.setString(7, user.getPhone());
            pstmt.setBoolean(8, user.isLogged());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId((int) generatedKeys.getLong(1));
                } else {
                    LOG.error(Messages.ERR_CANNOT_CREATE_DICTIONARY);
                    throw new DBException("Creating user failed, no ID obtained.", new SQLException());
                }
            }
            con.commit();
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_CREATE_USER, e);
            throw new DBException(Messages.ERR_CANNOT_CREATE_USER, e);
        }
    }

    /**
     * Extracts the user entity from dataBase.
     *
     * @param id the id of particular User
     *           by which method must extract the User entity from dataBase
     * @return User entity from dataBase
     */
    @Override
    public User read(Integer id) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(SQLCommands.SQL_READ_USER_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next())
                return extractUser(rs);
            con.commit();
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_READ_USER, e);
            throw new DBException(Messages.ERR_CANNOT_READ_USER, e);
        }
        return null;
    }

    /**
     * Updates the user entity in dataBase.
     *
     * @param user object of User
     */
    @Override
    public void update(User user) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_UPDATE_USER);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getLogin());
            pstmt.setString(3, user.getPassword());
            pstmt.setInt(4, user.getRole().getIntKey());
            pstmt.setInt(5, user.getLanguage().getIntKey());
            pstmt.setString(6, user.getEmail());
            pstmt.setString(7, user.getPhone());
            pstmt.setBoolean(8, user.isLogged());
            pstmt.setInt(9, user.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_UPDATE_USER, e);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, e);
        }
    }

    /**
     * Deletes the user entity from dataBase.
     *
     * @param id id of object of User
     */
    @Override
    public void delete(Integer id) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_DELETE_USER_BY_ID);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_DELETE_USER, ex);
            throw new DBException(Messages.ERR_CANNOT_DELETE_USER, ex);
        }
    }

    /**
     * Returns all users.
     *
     * @return List of user entities.
     */
    @Override
    public List<User> getAll() throws DBException {
        DBManager dbManager = DBManager.getInstance();
        List<User> listOfUsers = new ArrayList<>();
        Connection con = null;
        try {
            con = dbManager.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQLCommands.SQL_GET_USERS_ORDER_BY_ID);
            while (rs.next()) {
                listOfUsers.add(extractUser(rs));
            }
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException e) {
                LOG.error(Messages.ERR_ROLLBACK, ex);
            }
            LOG.error(Messages.ERR_CANNOT_GET_ALL_USERS, ex);
            throw new DBException(Messages.ERR_CANNOT_GET_ALL_USERS, ex);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException exception) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, exception);
            }
        }
        return listOfUsers;
    }


    /**
     * Extracts the user entity from dataBase.
     *
     * @param login the login of particular User
     *              by which method must extract the User entity from dataBase
     * @return User entity from dataBase
     */
    public User read(String login) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(SQLCommands.SQL_READ_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next())
                return extractUser(rs);
            con.commit();
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_READ_USER, e);
            throw new DBException(Messages.ERR_CANNOT_READ_USER, e);
        }
        return null;
    }


    /**
     * Extracts the user entity from dataBase.
     *
     * @param login the login of particular User
     *              by which method must extract the User entity from dataBase
     * @return User entity from dataBase
     */
    public User read(String login, String password) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(SQLCommands.SQL_READ_USER_BY_LOGIN_AND_PASSWORD);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next())
                return extractUser(rs);
            con.commit();
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_READ_USER, e);
            throw new DBException(Messages.ERR_CANNOT_READ_USER, e);
        }
        return null;
    }

    private User extractUser(ResultSet rs) throws DBException {
        User user = new User();
        try {
            user.setId(rs.getInt(Fields.ID));
            user.setName(rs.getString(Fields.USER_NAME));
            user.setLogin(rs.getString(Fields.USER_LOGIN));
            user.setPassword(rs.getString(Fields.USER_PASSWORD));
            user.setRole(Role.valueOf(rs.getString(Fields.USER_ROLE)));
            user.setLogged(rs.getBoolean(Fields.USER_LOGGED));
            user.setEmail(rs.getString(Fields.USER_EMAIL));
            user.setPhone(rs.getString(Fields.USER_PHONE));
            user.setLanguage(Language.valueOf(rs.getString(Fields.USER_LANGUAGE)));
            return user;
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER, e);
        }
    }

}
