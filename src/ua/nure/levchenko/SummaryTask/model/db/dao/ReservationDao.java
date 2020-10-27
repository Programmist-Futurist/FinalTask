package ua.nure.levchenko.SummaryTask.model.db.dao;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.exception.DBException;
import ua.nure.levchenko.SummaryTask.exception.constants.Messages;
import ua.nure.levchenko.SummaryTask.model.db.DBManager;
import ua.nure.levchenko.SummaryTask.model.db.constants.Fields;
import ua.nure.levchenko.SummaryTask.model.db.constants.SQLCommands;
import ua.nure.levchenko.SummaryTask.model.entity.db.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that implements methods
 * for working with DB for Reservation entity.
 *
 * @author K.Levchenko
 */
public class ReservationDao implements DAO<Reservation, Integer> {
    private static final Logger LOG = Logger.getLogger(HallDao.class);

    /**
     * Creates the new reservation entity in dataBase.
     *
     * @param reservation object of Reservation
     */
    public void create(Reservation reservation) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_CREATE_RESERVATION, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, reservation.getScheduleId());
            pstmt.setInt(2, reservation.getUserId());
            pstmt.setInt(3, reservation.getPlaceNumber());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reservation.setId((int) generatedKeys.getLong(1));
                } else {
                    LOG.error(Messages.ERR_CANNOT_CREATE_DICTIONARY);
                    throw new DBException("Creating user failed, no ID obtained.", new SQLException());
                }
            }
            con.commit();
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_CREATE_RESERVATION, e);
            throw new DBException(Messages.ERR_CANNOT_CREATE_RESERVATION, e);
        }
    }

    /**
     * Extracts the reservation entity from dataBase.
     *
     * @param id the name of the reservation that must be extracted
     * @return object of the Reservation
     */
    @Override
    synchronized public Reservation read(Integer id) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(SQLCommands.SQL_READ_RESERVATION_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next())
                return extractDictionary(rs);
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_READ_RESERVATION, e);
            throw new DBException(Messages.ERR_CANNOT_READ_RESERVATION, e);
        }
        return null;
    }

    /**
     * Updates the reservation entity in dataBase.
     *
     * @param reservation object of Reservation
     */
    @Override
    public void update(Reservation reservation) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_UPDATE_RESERVATION);
            pstmt.setInt(1, reservation.getScheduleId());
            pstmt.setInt(2, reservation.getUserId());
            pstmt.setInt(3, reservation.getPlaceNumber());
            pstmt.setInt(4, reservation.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_UPDATE_RESERVATION, e);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_RESERVATION, e);
        }
    }

    /**
     * Deletes the reservation entity from dataBase.
     *
     * @param id id of object of Reservation
     */
    @Override
    public void delete(Integer id) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_DELETE_RESERVATION_BY_ID);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_DELETE_RESERVATION, ex);
            throw new DBException(Messages.ERR_CANNOT_DELETE_RESERVATION, ex);
        }
    }


    /**
     * Returns all reservation entities.
     *
     * @return List of reservation entities.
     */
    @Override
    synchronized public List<Reservation> getAll() throws DBException {
        DBManager dbManager = DBManager.getInstance();
        List<Reservation> dictionaryList = new ArrayList<>();
        Connection con = null;
        try {
            con = dbManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_GET_RESERVATION_ORDER_BY_ID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dictionaryList.add(extractDictionary(rs));
            }
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException e) {
                LOG.error(Messages.ERR_ROLLBACK, e);
            }
            LOG.error(Messages.ERR_CANNOT_GET_ALL_RESERVATION, ex);
            throw new DBException(Messages.ERR_CANNOT_GET_ALL_RESERVATION, ex);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException exception) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, exception);
            }
        }
        return dictionaryList;
    }


    /**
     * Returns all reservation entities by schedule.
     *
     * @param scheduleId
     * @return List of reservation entities.
     */
    synchronized public List<Reservation> getAllBySchedule(int scheduleId) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        List<Reservation> dictionaryList = new ArrayList<>();
        Connection con = null;
        try {
            con = dbManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_GET_RESERVATION_BY_SCHEDULE);
            pstmt.setInt(1, scheduleId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dictionaryList.add(extractDictionary(rs));
            }
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException e) {
                LOG.error(Messages.ERR_ROLLBACK, e);
            }
            LOG.error(Messages.ERR_CANNOT_GET_ALL_RESERVATION, ex);
            throw new DBException(Messages.ERR_CANNOT_GET_ALL_RESERVATION, ex);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException exception) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, exception);
            }
        }
        return dictionaryList;
    }


    /**
     * Returns all reservation entities by user.
     *
     * @param userId
     * @return List of reservation entities.
     */
    synchronized public List<Reservation> getAllByUser(int userId) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        List<Reservation> dictionaryList = new ArrayList<>();
        Connection con = null;
        try {
            con = dbManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_GET_RESERVATION_BY_USER);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dictionaryList.add(extractDictionary(rs));
            }
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException e) {
                LOG.error(Messages.ERR_ROLLBACK, e);
            }
            LOG.error(Messages.ERR_CANNOT_GET_ALL_RESERVATION, ex);
            throw new DBException(Messages.ERR_CANNOT_GET_ALL_RESERVATION, ex);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException exception) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, exception);
            }
        }
        return dictionaryList;
    }


    private Reservation extractDictionary(ResultSet rs) throws DBException {
        try {
            Reservation reservation = new Reservation();
            reservation.setId(rs.getInt(Fields.ID));
            reservation.setScheduleId(rs.getInt(Fields.RESERVATION_SCHEDULE_ID));
            reservation.setUserId(rs.getInt(Fields.RESERVATION_USER_ID));
            reservation.setPlaceNumber(rs.getInt(Fields.RESERVATION_PLACE_NUMBER));
            return reservation;
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_RESERVATION, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_RESERVATION, e);
        }
    }
}
