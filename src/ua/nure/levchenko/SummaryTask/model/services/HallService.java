package ua.nure.levchenko.SummaryTask.model.services;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.exception.DBException;
import ua.nure.levchenko.SummaryTask.exception.ServiceException;
import ua.nure.levchenko.SummaryTask.exception.constants.Messages;
import ua.nure.levchenko.SummaryTask.model.db.dao.DictionaryDao;
import ua.nure.levchenko.SummaryTask.model.db.dao.HallDao;
import ua.nure.levchenko.SummaryTask.model.entity.db.Dictionary;
import ua.nure.levchenko.SummaryTask.model.entity.db.Hall;
import ua.nure.levchenko.SummaryTask.model.validators.HallValidator;

import java.util.List;

/**
 * Service to connect controller with Dao
 * and to make DataBase working operations more wider for DB entity Hall.
 *
 * @author K.Levchenko
 */
public class HallService implements Service<Hall, Integer> {
    private static final Logger LOG = Logger.getLogger(HallService.class);


    /**
     * Creates Hall entity in DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param hall
     * @throws ServiceException
     */
    @Override
    public void create(Hall hall) throws ServiceException {
        LOG.debug("Service create starts");
        try {
            HallValidator hallValidator = new HallValidator();
            if (hallValidator.validate(hall)) {
                Dictionary dictionaryName = hall.getName();
                // creating dictionary in DB
                DictionaryDao dictionaryDao = new DictionaryDao();
                dictionaryDao.create(dictionaryName);
                // creating hall
                HallDao hallDao = new HallDao();
                hallDao.create(hall);
            }
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_CREATE_HALL_SERVICE, e);
            throw new ServiceException(Messages.ERR_CANNOT_CREATE_HALL_SERVICE, e);
        }
        LOG.debug("Service create finished");
    }

    /**
     * Reads Hall entity from DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param id
     * @throws ServiceException
     */
    @Override
    public Hall read(Integer id) throws ServiceException {
        LOG.debug("Service read starts");
        try {
            HallDao hallDao = new HallDao();
            return hallDao.read(id);
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_READ_HALL_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_READ_HALL_SERVICE, e);
        }
    }

    /**
     * Updates Hall entity in DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param hall
     * @throws ServiceException
     */
    @Override
    public void update(Hall hall) throws ServiceException {
        LOG.debug("Service update starts");
        try {
            HallValidator hallValidator = new HallValidator();
            if (hallValidator.validate(hall)) {
                Dictionary dictionaryName = hall.getName();
                // creating dictionary in DB
                DictionaryDao dictionaryDao = new DictionaryDao();
                dictionaryDao.update(dictionaryName);
                // creating hall
                HallDao hallDao = new HallDao();
                hallDao.update(hall);
            }
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_UPDATE_HALL_SERVICE, e);
            throw new ServiceException(Messages.ERR_CANNOT_UPDATE_HALL_SERVICE, e);
        } finally {
            LOG.debug("Service update finished");
        }
    }

    /**
     * Deletes Hall entity from DataBase
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
            HallDao hallDao = new HallDao();
            hallDao.delete(id);
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_DELETE_HALL_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_DELETE_HALL_SERVICE, e);
        }
        LOG.debug("Service delete finished");
    }

    /**
     * Reads all Hall entities from DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @throws ServiceException
     */
    @Override
    public List<Hall> getAll() throws ServiceException {
        LOG.debug("Service getAll starts");
        try {
            HallDao hallDao = new HallDao();
            return hallDao.getAll();
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_GET_ALL_HALLS_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_GET_ALL_HALLS_SERVICE, e);
        }
    }

    /**
     * Reads Hall entity from DataBase
     * and also includes all dependencies of hall
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param id
     * @throws ServiceException
     */
    public Hall readFull(Integer id) throws ServiceException {
        LOG.debug("Service read starts");
        try {
            // get film
            HallDao hallDao = new HallDao();
            Hall hall = hallDao.read(id);
            // get dictionary entities
            DictionaryService dictionaryService = new DictionaryService();
            Dictionary dictionaryName = dictionaryService.read(hall.getNameId());
            // setting fields
            hall.setName(dictionaryName);
            return hall;
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_READ_FILM_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_READ_FILM_SERVICE, e);
        }
    }


    /**
     * Reads all Halls entities from DataBase
     * and also includes all DB dependencies of hall.
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @throws ServiceException
     */
    public List<Hall> getAllFull() throws ServiceException {
        LOG.debug("Service getAll starts");
        try {
            HallDao hallDao = new HallDao();
            DictionaryService dictionaryService = new DictionaryService();
            List<Hall> halls = hallDao.getAll();
            for (Hall hall : halls) {
                // get dictionary entities
                Dictionary dictionaryName = dictionaryService.read(hall.getNameId());
                // setting fields
                hall.setName(dictionaryName);
            }
            return halls;
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_GET_ALL_FILM_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_GET_ALL_FILM_SERVICE, e);
        }
    }
}
