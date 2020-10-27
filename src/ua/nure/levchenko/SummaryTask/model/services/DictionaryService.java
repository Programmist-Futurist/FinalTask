package ua.nure.levchenko.SummaryTask.model.services;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.exception.DBException;
import ua.nure.levchenko.SummaryTask.exception.ServiceException;
import ua.nure.levchenko.SummaryTask.exception.constants.Messages;
import ua.nure.levchenko.SummaryTask.model.db.dao.DictionaryDao;
import ua.nure.levchenko.SummaryTask.model.entity.db.Dictionary;
import ua.nure.levchenko.SummaryTask.model.validators.DictionaryValidator;

import java.util.List;


/**
 * Service to connect controller with Dao
 * and to make DataBase working operations more wider for DB entity Dictionary.
 *
 * @author K.Levchenko
 */
public class DictionaryService implements Service<Dictionary, Integer> {
    private static final Logger LOG = Logger.getLogger(DictionaryService.class);


    /**
     * Creates Dictionary entity in DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param dictionary
     * @throws ServiceException
     */
    @Override
    public void create(Dictionary dictionary) throws ServiceException {
        LOG.debug("Service create starts");
        try {
            DictionaryValidator dictionaryValidator = new DictionaryValidator();
            if (dictionaryValidator.validate(dictionary)) {
                DictionaryDao dictionaryDao = new DictionaryDao();
                dictionaryDao.create(dictionary);
            }
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_CREATE_DICTIONARY_SERVICE, e);
            throw new ServiceException(Messages.ERR_CANNOT_CREATE_DICTIONARY_SERVICE, e);
        }
        LOG.debug("Service create finished");
    }

    /**
     * Reads Dictionary entity from DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param id
     * @throws ServiceException
     */
    @Override
    public Dictionary read(Integer id) throws ServiceException {
        LOG.debug("Service read starts");
        try {
            DictionaryDao dictionaryDao = new DictionaryDao();
            return dictionaryDao.read(id);
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_READ_DICTIONARY_SERVICE, e);
            throw new ServiceException(Messages.ERR_CANNOT_READ_DICTIONARY_SERVICE, e);
        }

    }

    /**
     * Updates Dictionary entity in DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param dictionary
     * @throws ServiceException
     */
    @Override
    public void update(Dictionary dictionary) throws ServiceException {
        LOG.debug("Service update starts");
        try {
            DictionaryValidator dictionaryValidator = new DictionaryValidator();
            if (dictionaryValidator.validate(dictionary)) {
                DictionaryDao dictionaryDao = new DictionaryDao();
                dictionaryDao.update(dictionary);
            }
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_CREATE_DICTIONARY_SERVICE, e);
            throw new ServiceException(Messages.ERR_CANNOT_CREATE_DICTIONARY_SERVICE, e);
        }
        LOG.debug("Service update finished");
    }

    /**
     * Deletes Dictionary entity from DataBase
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
            DictionaryDao dictionaryDao = new DictionaryDao();
            dictionaryDao.delete(id);
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_DELETE_DICTIONARY_SERVICE, e);
            throw new ServiceException(Messages.ERR_CANNOT_DELETE_DICTIONARY_SERVICE, e);
        }
        LOG.debug("Service delete finished");
    }

    /**
     * Reads all Dictionary entities from DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @throws ServiceException
     */
    @Override
    public List<Dictionary> getAll() throws ServiceException {
        LOG.debug("Service getAll starts");
        try {
            DictionaryDao dictionaryDao = new DictionaryDao();
            return dictionaryDao.getAll();
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_GET_FULL_DICTIONARY_SERVICE, e);
            throw new ServiceException(Messages.ERR_CANNOT_GET_FULL_DICTIONARY_SERVICE, e);
        }
    }
}
