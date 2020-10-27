package ua.nure.levchenko.SummaryTask.model.services;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.exception.DBException;
import ua.nure.levchenko.SummaryTask.exception.ServiceException;
import ua.nure.levchenko.SummaryTask.exception.constants.Messages;
import ua.nure.levchenko.SummaryTask.model.db.dao.ScheduleDao;
import ua.nure.levchenko.SummaryTask.model.entity.db.Film;
import ua.nure.levchenko.SummaryTask.model.entity.db.Hall;
import ua.nure.levchenko.SummaryTask.model.entity.db.ScheduleEntity;
import ua.nure.levchenko.SummaryTask.model.validators.ScheduleValidator;

import java.util.List;


/**
 * Service to connect controller with Dao
 * and to make DataBase working operations more wider for DB entity Schedule.
 *
 * @author K.Levchenko
 */
public class ScheduleService implements Service<ScheduleEntity, Integer> {
    private static final Logger LOG = Logger.getLogger(ScheduleService.class);

    /**
     * Creates ScheduleEntity entity in DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param scheduleEntity
     * @throws ServiceException
     */
    @Override
    public void create(ScheduleEntity scheduleEntity) throws ServiceException {
        LOG.debug("Service create starts");
        try {
            ScheduleValidator scheduleValidator = new ScheduleValidator();
            if (scheduleValidator.validate(scheduleEntity)) {
                ScheduleDao scheduleDao = new ScheduleDao();
                scheduleDao.create(scheduleEntity);
            }
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_CREATE_SCHEDULE_ENTITY_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_CREATE_SCHEDULE_ENTITY_SERVICE, e);
        }
        LOG.debug("Service create finished");
    }

    /**
     * Reads ScheduleEntity entity from DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param id
     * @throws ServiceException
     */
    @Override
    public ScheduleEntity read(Integer id) throws ServiceException {
        LOG.debug("Service read starts");
        try {
            ScheduleDao scheduleDao = new ScheduleDao();
            return scheduleDao.read(id);
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_READ_SCHEDULE_ENTITY_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_READ_SCHEDULE_ENTITY_SERVICE, e);
        }
    }

    /**
     * Updates ScheduleEntity entity in DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param scheduleEntity
     * @throws ServiceException
     */
    @Override
    public void update(ScheduleEntity scheduleEntity) throws ServiceException {
        LOG.debug("Service update starts");
        try {
            ScheduleValidator scheduleValidator = new ScheduleValidator();
            if (scheduleValidator.validate(scheduleEntity)) {
                ScheduleDao scheduleDao = new ScheduleDao();
                scheduleDao.update(scheduleEntity);
            }
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_UPDATE_SCHEDULE_ENTITY_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_SCHEDULE_ENTITY_SERVICE, e);
        }
        LOG.debug("Service update finished");
    }

    /**
     * Deletes ScheduleEntity entity from DataBase
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
            ScheduleDao scheduleDao = new ScheduleDao();
            scheduleDao.delete(id);
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_DELETE_SCHEDULE_ENTITY_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_DELETE_SCHEDULE_ENTITY_SERVICE, e);
        }
        LOG.debug("Service delete finished");
    }

    /**
     * Reads all Schedule entities from DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @throws ServiceException
     */
    @Override
    public List<ScheduleEntity> getAll() throws ServiceException {
        LOG.debug("Service getAll starts");
        try {
            ScheduleDao scheduleDao = new ScheduleDao();
            return scheduleDao.getAll();
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_GET_ALL_SCHEDULE_ENTITY_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_GET_ALL_SCHEDULE_ENTITY_SERVICE, e);
        }
    }


    /**
     * Reads all Schedule entities from DataBase
     * by particular film.
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param filmId
     * @throws ServiceException
     */
    public List<ScheduleEntity> getAllByFilm(int filmId) throws ServiceException {
        LOG.debug("Service getAll starts");
        try {
            ScheduleDao scheduleDao = new ScheduleDao();
            return scheduleDao.getAllByFilm(filmId);
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_GET_ALL_SCHEDULE_ENTITY_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_GET_ALL_SCHEDULE_ENTITY_SERVICE, e);
        }
    }


    /**
     * Reads all Schedule entities from DataBase
     * by particular film fulfilled by dependent entities.
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param filmId
     * @throws ServiceException
     */
    public List<ScheduleEntity> getAllFullByFilm(int filmId) throws ServiceException {
        LOG.debug("Service getAll starts");
        try {
            ScheduleDao scheduleDao = new ScheduleDao();
            FilmService filmService = new FilmService();
            HallService hallService = new HallService();
            List<ScheduleEntity> schedules = scheduleDao.getAllByFilm(filmId);
            for (ScheduleEntity scheduleEntity : schedules) {
                // set film entities
                Film film = filmService.readFull(scheduleEntity.getFilmId());
                scheduleEntity.setFilm(film);
                // set hall entities
                Hall hall = hallService.readFull(scheduleEntity.getHallId());
                scheduleEntity.setHall(hall);
            }
            return schedules;
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_GET_ALL_SCHEDULE_ENTITY_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_GET_ALL_SCHEDULE_ENTITY_SERVICE, e);
        }
    }


    /**
     * Reads ScheduleEntity entity from DataBase
     * and also includes all dependencies of ScheduleEntity
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param id
     * @throws ServiceException
     */
    public ScheduleEntity readFull(Integer id) throws ServiceException {
        LOG.debug("Service read starts");
        try {
            // get schedule
            ScheduleDao scheduleDao = new ScheduleDao();
            ScheduleEntity scheduleEntity = scheduleDao.read(id);
            // set film entities
            FilmService filmService = new FilmService();
            Film film = filmService.readFull(scheduleEntity.getFilmId());
            scheduleEntity.setFilm(film);
            // set hall entities
            HallService hallService = new HallService();
            Hall hall = hallService.readFull(scheduleEntity.getHallId());
            scheduleEntity.setHall(hall);
            return scheduleEntity;
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_READ_FILM_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_READ_FILM_SERVICE, e);
        }
    }


    /**
     * Reads all Schedule entities from DataBase
     * and also includes all DB dependencies of Schedule.
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @throws ServiceException
     */
    public List<ScheduleEntity> getAllFull() throws ServiceException {
        LOG.debug("Service getAll starts");
        try {
            ScheduleDao scheduleDao = new ScheduleDao();
            FilmService filmService = new FilmService();
            HallService hallService = new HallService();
            List<ScheduleEntity> schedules = scheduleDao.getAll();
            for (ScheduleEntity scheduleEntity : schedules) {
                // set film entities
                Film film = filmService.readFull(scheduleEntity.getFilmId());
                scheduleEntity.setFilm(film);
                // set hall entities
                Hall hall = hallService.readFull(scheduleEntity.getHallId());
                scheduleEntity.setHall(hall);
            }
            return schedules;
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_GET_ALL_FILM_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_GET_ALL_FILM_SERVICE, e);
        }
    }
}
