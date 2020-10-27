package ua.nure.levchenko.SummaryTask.model.services;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.exception.DBException;
import ua.nure.levchenko.SummaryTask.exception.ServiceException;
import ua.nure.levchenko.SummaryTask.exception.constants.Messages;
import ua.nure.levchenko.SummaryTask.model.db.dao.DictionaryDao;
import ua.nure.levchenko.SummaryTask.model.db.dao.FilmDao;
import ua.nure.levchenko.SummaryTask.model.entity.db.Dictionary;
import ua.nure.levchenko.SummaryTask.model.entity.db.Film;
import ua.nure.levchenko.SummaryTask.model.validators.FilmValidator;

import java.util.List;


/**
 * Service to connect controller with Dao
 * and to make DataBase working operations more wider for DB entity Film.
 *
 * @author K.Levchenko
 */
public class FilmService implements Service<Film, Integer> {
    private static final Logger LOG = Logger.getLogger(FilmService.class);


    /**
     * Creates Film entity in DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param film
     * @throws ServiceException
     */
    @Override
    public void create(Film film) throws ServiceException {
        LOG.debug("Service create starts");
        try {
            FilmValidator filmValidator = new FilmValidator();
            if (filmValidator.validate(film)) {
                Dictionary dictionaryName = film.getName();
                Dictionary dictionaryDescription = film.getDescription();
                // creating dictionaries in DB
                DictionaryDao dictionaryDao = new DictionaryDao();
                dictionaryDao.create(dictionaryName);
                dictionaryDao.create(dictionaryDescription);
                // create Film
                FilmDao filmDao = new FilmDao();
                film.setNameId(dictionaryName.getId());
                film.setDescriptionId(dictionaryDescription.getId());
                filmDao.create(film);
            }
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_CREATE_FILM_SERVICE, e);
            throw new ServiceException(Messages.ERR_CANNOT_CREATE_FILM_SERVICE, e);
        }
        LOG.debug("Service create finished");
    }

    /**
     * Reads Film entity from DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param id
     * @throws ServiceException
     */
    @Override
    public Film read(Integer id) throws ServiceException {
        LOG.debug("Service read starts");
        try {
            FilmDao filmDao = new FilmDao();
            return filmDao.read(id);
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_READ_FILM_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_READ_FILM_SERVICE, e);
        }
    }

    /**
     * Updates Film entity in DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param film
     * @throws ServiceException
     */
    @Override
    public void update(Film film) throws ServiceException {
        LOG.debug("Service update starts");
        try {
            FilmValidator filmValidator = new FilmValidator();
            if (filmValidator.validate(film)) {
                Dictionary dictionaryName = film.getName();
                dictionaryName.setId(film.getNameId());
                Dictionary dictionaryDescription = film.getDescription();
                dictionaryDescription.setId(film.getDescriptionId());
                // updating dictionaries in DB
                DictionaryDao dictionaryDao = new DictionaryDao();
                dictionaryDao.update(dictionaryName);
                dictionaryDao.update(dictionaryDescription);
                // update Film
                FilmDao filmDao = new FilmDao();
                filmDao.update(film);
            }
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_UPDATE_FILM_SERVICE, e);
            throw new ServiceException(Messages.ERR_CANNOT_UPDATE_FILM_SERVICE, e);
        }
        LOG.debug("Service update finished");
    }

    /**
     * Deletes Film entity from DataBase
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
            FilmDao filmDao = new FilmDao();
            filmDao.delete(id);
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_DELETE_FILM_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_DELETE_FILM_SERVICE, e);
        }
        LOG.debug("Service delete finished");
    }

    /**
     * Reads all Film entities from DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @throws ServiceException
     */
    @Override
    public List<Film> getAll() throws ServiceException {
        LOG.debug("Service getAll starts");
        try {
            FilmDao filmDao = new FilmDao();
            return filmDao.getAll();
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_GET_ALL_FILM_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_GET_ALL_FILM_SERVICE, e);
        }
    }


    /**
     * Reads Film entity from DataBase
     * and also includes all dependencies of film
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param id
     * @throws ServiceException
     */
    public Film readFull(Integer id) throws ServiceException {
        LOG.debug("Service read starts");
        try {
            // get film
            FilmDao filmDao = new FilmDao();
            Film film = filmDao.read(id);
            // get dictionary entities
            DictionaryService dictionaryService = new DictionaryService();
            Dictionary dictionaryName = dictionaryService.read(film.getNameId());
            Dictionary dictionaryDescription = dictionaryService.read(film.getDescriptionId());
            // setting fields
            film.setName(dictionaryName);
            film.setDescription(dictionaryDescription);
            return film;
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_READ_FILM_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_READ_FILM_SERVICE, e);
        }
    }


    /**
     * Reads all Film entities from DataBase
     * and also includes all DB dependencies of film.
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @throws ServiceException
     */
    public List<Film> getAllFull() throws ServiceException {
        LOG.debug("Service getAll starts");
        try {
            FilmDao filmDao = new FilmDao();
            DictionaryService dictionaryService = new DictionaryService();
            List<Film> films = filmDao.getAll();
            for (Film film : films) {
                Dictionary dictionaryName = dictionaryService.read(film.getNameId());
                Dictionary dictionaryDescription = dictionaryService.read(film.getDescriptionId());
                film.setName(dictionaryName);
                film.setDescription(dictionaryDescription);
            }
            return films;
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_GET_ALL_FILM_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_GET_ALL_FILM_SERVICE, e);
        }
    }
}
