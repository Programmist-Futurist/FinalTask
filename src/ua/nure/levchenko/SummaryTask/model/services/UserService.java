package ua.nure.levchenko.SummaryTask.model.services;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.exception.DBException;
import ua.nure.levchenko.SummaryTask.exception.ServiceException;
import ua.nure.levchenko.SummaryTask.exception.constants.Messages;
import ua.nure.levchenko.SummaryTask.model.db.dao.UserDao;
import ua.nure.levchenko.SummaryTask.model.entity.db.User;
import ua.nure.levchenko.SummaryTask.model.validators.UserValidator;

import java.util.List;


/**
 * Service to connect controller with Dao
 * and to make DataBase working operations more wider for DB entity User.
 *
 * @author K.Levchenko
 */
public class UserService implements Service<User, Integer> {
    private static final Logger LOG = Logger.getLogger(UserService.class);


    /**
     * Creates User entity in DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param user
     * @throws ServiceException
     */
    @Override
    public void create(User user) throws ServiceException {
        LOG.debug("Service create starts");
        try {
            UserValidator userValidator = new UserValidator();
            if (userValidator.validate(user)) {
                UserDao userDao = new UserDao();
                userDao.create(user);
            }
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_CREATE_USER_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_CREATE_USER_SERVICE, e);
        }
        LOG.debug("Service create finished");
    }

    /**
     * Reads User entity from DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param id
     * @throws ServiceException
     */
    @Override
    public User read(Integer id) throws ServiceException {
        LOG.debug("Service read starts");
        try {
            UserDao userDao = new UserDao();
            return userDao.read(id);
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_READ_USER_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_READ_USER_SERVICE, e);
        }
    }


    /**
     * Reads User entity from DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param login
     * @throws ServiceException
     */
    public User read(String login) throws ServiceException {
        LOG.debug("Service read starts");
        try {
            UserDao userDao = new UserDao();
            return userDao.read(login);
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_READ_USER_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_READ_USER_SERVICE, e);
        }
    }

    /**
     * Updates User entity in DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param user
     * @throws ServiceException
     */
    @Override
    public void update(User user) throws ServiceException {
        LOG.debug("Service update starts");
        try {
            UserValidator userValidator = new UserValidator();
            if (userValidator.validate(user)) {
                UserDao userDao = new UserDao();
                userDao.update(user);
            }
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_UPDATE_USER_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_USER_SERVICE, e);
        }
        LOG.debug("Service update finished");
    }

    /**
     * Deletes User entity from DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param id
     * @throws ServiceException
     */
    @Override
    public void delete(Integer id) throws ServiceException {
        LOG.debug("Service delete starts");
        try {
            UserDao userDao = new UserDao();
            userDao.delete(id);
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_DELETE_USER_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_DELETE_USER_SERVICE, e);
        }
        LOG.debug("Service delete finished");
    }

    /**
     * Reads all User entities from DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @throws ServiceException
     */
    @Override
    public List<User> getAll() throws ServiceException {
        LOG.debug("Service getAll starts");
        try {
            UserDao userDao = new UserDao();
            return userDao.getAll();
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_GET_ALL_USERS_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_GET_ALL_USERS_SERVICE, e);
        }
    }
}
