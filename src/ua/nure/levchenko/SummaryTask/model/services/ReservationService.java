package ua.nure.levchenko.SummaryTask.model.services;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.exception.DBException;
import ua.nure.levchenko.SummaryTask.exception.ServiceException;
import ua.nure.levchenko.SummaryTask.exception.constants.Messages;
import ua.nure.levchenko.SummaryTask.model.db.dao.ReservationDao;
import ua.nure.levchenko.SummaryTask.model.entity.db.Reservation;
import ua.nure.levchenko.SummaryTask.model.entity.db.ScheduleEntity;
import ua.nure.levchenko.SummaryTask.model.entity.db.User;
import ua.nure.levchenko.SummaryTask.model.validators.ReservationValidator;

import java.util.List;


/**
 * Service to connect controller with Dao
 * and to make DataBase working operations more wider for DB entity Reservation.
 *
 * @author K.Levchenko
 */
public class ReservationService implements Service<Reservation, Integer> {
    private static final Logger LOG = Logger.getLogger(ReservationService.class);

    /**
     * Creates Reservation entity in DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param reservation
     * @throws ServiceException
     */
    @Override
    public void create(Reservation reservation) throws ServiceException {
        LOG.debug("Service create starts");
        try {
            ReservationValidator reservationValidator = new ReservationValidator();
            if (reservationValidator.validate(reservation)) {
                ReservationDao reservationDao = new ReservationDao();
                reservationDao.create(reservation);
            }
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_CREATE_RESERVATION_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_CREATE_RESERVATION_SERVICE, e);
        }
        LOG.debug("Service create finished");
    }

    /**
     * Reads Reservation entity from DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param id
     * @throws ServiceException
     */
    @Override
    public Reservation read(Integer id) throws ServiceException {
        LOG.debug("Service read starts");
        try {
            ReservationDao reservationDao = new ReservationDao();
            return reservationDao.read(id);
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_READ_RESERVATION_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_READ_RESERVATION_SERVICE, e);
        }
    }

    /**
     * Updates Reservation entity in DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param reservation
     * @throws ServiceException
     */
    @Override
    public void update(Reservation reservation) throws ServiceException {
        LOG.debug("Service update starts");
        try {
            ReservationValidator reservationValidator = new ReservationValidator();
            if (reservationValidator.validate(reservation)) {
                ReservationDao reservationDao = new ReservationDao();
                reservationDao.update(reservation);
            }
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_UPDATE_RESERVATION_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_RESERVATION_SERVICE, e);
        }
        LOG.debug("Service update finishes");
    }

    /**
     * Deletes Reservation entity from DataBase
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
            ReservationDao reservationDao = new ReservationDao();
            reservationDao.delete(id);
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_DELETE_RESERVATION_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_DELETE_RESERVATION_SERVICE, e);
        }
        LOG.debug("Service delete finished");
    }

    /**
     * Reads all Reservation entities from DataBase
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @throws ServiceException
     */
    @Override
    public List<Reservation> getAll() throws ServiceException {
        LOG.debug("Service getAll starts");
        try {
            ReservationDao reservationDao = new ReservationDao();
            return reservationDao.getAll();
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_GET_ALL_RESERVATION_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_GET_ALL_RESERVATION_SERVICE, e);
        }
    }


    /**
     * Reads Reservation entity from DataBase
     * and also includes all dependencies of reservation
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @param id
     * @throws ServiceException
     */
    public Reservation readFull(Integer id) throws ServiceException {
        LOG.debug("Service read starts");
        try {
            // get film
            ReservationDao reservationDao = new ReservationDao();
            Reservation reservation = reservationDao.read(id);
            // get Schedule entities
            ScheduleService scheduleService = new ScheduleService();
            ScheduleEntity scheduleEntity = scheduleService.readFull(reservation.getScheduleId());
            // setting fields
            reservation.setScheduleEntity(scheduleEntity);
            // get User entities
            UserService userService = new UserService();
            User user = userService.read(reservation.getUserId());
            reservation.setUser(user);
            return reservation;
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_READ_FILM_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_READ_FILM_SERVICE, e);
        }
    }


    /**
     * Reads all Reservation entities from DataBase
     * and also includes all DB dependencies of reservation.
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @throws ServiceException
     */
    public List<Reservation> getAllFull() throws ServiceException {
        LOG.debug("Service getAll starts");
        try {
            ReservationDao reservationDao = new ReservationDao();
            ScheduleService scheduleService = new ScheduleService();
            UserService userService = new UserService();
            List<Reservation> reservations = reservationDao.getAll();
            for (Reservation reservation : reservations) {
                // get Schedule entities
                ScheduleEntity scheduleEntity = scheduleService.readFull(reservation.getScheduleId());
                // setting fields
                reservation.setScheduleEntity(scheduleEntity);
                // get User entities
                User user = userService.read(reservation.getUserId());
                reservation.setUser(user);
            }
            return reservations;
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_GET_ALL_FILM_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_GET_ALL_FILM_SERVICE, e);
        }
    }


    /**
     * Reads all Reservation entities from DataBase
     * and also includes all DB dependencies of reservation.
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @throws ServiceException
     */
    public List<Reservation> getAllFullBySchedule(int scheduleId) throws ServiceException {
        LOG.debug("Service getAllFullBySchedule starts");
        try {
            ReservationDao reservationDao = new ReservationDao();
            ScheduleService scheduleService = new ScheduleService();
            UserService userService = new UserService();
            List<Reservation> reservations = reservationDao.getAllBySchedule(scheduleId);
            for (Reservation reservation : reservations) {
                // get Schedule entities
                ScheduleEntity scheduleEntity = scheduleService.readFull(reservation.getScheduleId());
                // setting fields
                reservation.setScheduleEntity(scheduleEntity);
                // get User entities
                User user = userService.read(reservation.getUserId());
                reservation.setUser(user);
            }
            return reservations;
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_GET_ALL_FILM_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_GET_ALL_FILM_SERVICE, e);
        } finally {
            LOG.debug("Service getAllFullBySchedule ends");
        }
    }


    /**
     * Reads all Reservation entities from DataBase
     * and also includes all DB dependencies of reservation.
     * Method have the same main function as in the Dao,
     * but it is available to be expanded with additional functionality.
     *
     * @throws ServiceException
     */
    public List<Reservation> getAllFullByUser(int userId) throws ServiceException {
        LOG.debug("Service getAll starts");
        try {
            ReservationDao reservationDao = new ReservationDao();
            ScheduleService scheduleService = new ScheduleService();
            UserService userService = new UserService();
            List<Reservation> reservations = reservationDao.getAllByUser(userId);
            for (Reservation reservation : reservations) {
                // get Schedule entities
                ScheduleEntity scheduleEntity = scheduleService.readFull(reservation.getScheduleId());
                // setting fields
                reservation.setScheduleEntity(scheduleEntity);
                // get User entities
                User user = userService.read(reservation.getUserId());
                reservation.setUser(user);
            }
            return reservations;
        } catch (DBException e) {
            LOG.error(Messages.ERR_CANNOT_GET_ALL_FILM_SERVICE, e);
            throw new DBException(Messages.ERR_CANNOT_GET_ALL_FILM_SERVICE, e);
        }
    }
}
