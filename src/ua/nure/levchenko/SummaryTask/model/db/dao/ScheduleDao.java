package ua.nure.levchenko.SummaryTask.model.db.dao;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.exception.DBException;
import ua.nure.levchenko.SummaryTask.exception.constants.Messages;
import ua.nure.levchenko.SummaryTask.model.db.DBManager;
import ua.nure.levchenko.SummaryTask.model.db.constants.Fields;
import ua.nure.levchenko.SummaryTask.model.db.constants.SQLCommands;
import ua.nure.levchenko.SummaryTask.model.entity.db.ScheduleEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Class that implements methods
 * for working with DB for Schedule entity.
 *
 * @author K.Levchenko
 */
public class ScheduleDao implements DAO<ScheduleEntity, Integer> {
    private static final Logger LOG = Logger.getLogger(ScheduleDao.class);

    /**
     * Creates the new scheduleEntity in dataBase.
     *
     * @param scheduleEntity object of ScheduleEntity
     */
    public void create(ScheduleEntity scheduleEntity) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_CREATE_SCHEDULE_ENTITY, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, scheduleEntity.getFilmId());
            pstmt.setInt(2, scheduleEntity.getHallId());
            pstmt.setTimestamp(3, (Timestamp) scheduleEntity.getTimeStart());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    scheduleEntity.setId((int) generatedKeys.getLong(1));
                } else {
                    LOG.error(Messages.ERR_CANNOT_CREATE_DICTIONARY);
                    throw new DBException("Creating user failed, no ID obtained.", new SQLException());
                }
            }
            con.commit();
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_CREATE_SCHEDULE_ENTITY, e);
            throw new DBException(Messages.ERR_CANNOT_CREATE_SCHEDULE_ENTITY, e);
        }
    }

    /**
     * Extracts the scheduleEntity entity from dataBase.
     *
     * @param id the name of the scheduleEntity that must be extracted
     * @return object of the ScheduleEntity
     */
    @Override
    synchronized public ScheduleEntity read(Integer id) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(SQLCommands.SQL_READ_SCHEDULE_ENTITY_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next())
                return extractDictionary(rs);
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_READ_SCHEDULE_ENTITY, e);
            throw new DBException(Messages.ERR_CANNOT_READ_SCHEDULE_ENTITY, e);
        }
        return null;
    }

    /**
     * Updates the scheduleEntity entity in dataBase.
     *
     * @param scheduleEntity object of ScheduleEntity
     */
    @Override
    public void update(ScheduleEntity scheduleEntity) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_UPDATE_SCHEDULE_ENTITY);
            pstmt.setInt(1, scheduleEntity.getFilmId());
            pstmt.setInt(2, scheduleEntity.getHallId());
            pstmt.setTimestamp(3, (Timestamp) scheduleEntity.getTimeStart());
            pstmt.setInt(4, scheduleEntity.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_UPDATE_SCHEDULE_ENTITY, e);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_SCHEDULE_ENTITY, e);
        }
    }

    /**
     * Deletes the scheduleEntity entity from dataBase.
     *
     * @param id id of object of ScheduleEntity
     */
    @Override
    public void delete(Integer id) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_DELETE_SCHEDULE_ENTITY_BY_ID);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_DELETE_SCHEDULE_ENTITY, ex);
            throw new DBException(Messages.ERR_CANNOT_DELETE_SCHEDULE_ENTITY, ex);
        }
    }


    /**
     * Returns all schedule entities.
     *
     * @return List of schedule entities.
     */
    @Override
    synchronized public List<ScheduleEntity> getAll() throws DBException {
        DBManager dbManager = DBManager.getInstance();
        List<ScheduleEntity> dictionaryList = new ArrayList<>();
        Connection con = null;
        try {
            con = dbManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_GET_SCHEDULE_ORDER_BY_ID);
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
            LOG.error(Messages.ERR_CANNOT_GET_ALL_SCHEDULE_ENTITY, ex);
            throw new DBException(Messages.ERR_CANNOT_GET_ALL_SCHEDULE_ENTITY, ex);
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
     * Returns all schedule entities by film.
     *
     * @param filmId
     * @return List of schedule entities.
     */
    synchronized public List<ScheduleEntity> getAllByFilm(int filmId) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        List<ScheduleEntity> dictionaryList = new ArrayList<>();
        Connection con = null;
        try {
            con = dbManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_GET_FILM_SCHEDULE);
            pstmt.setInt(1, filmId);
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
            LOG.error(Messages.ERR_CANNOT_GET_FILM_SCHEDULE_ENTITIES, ex);
            throw new DBException(Messages.ERR_CANNOT_GET_FILM_SCHEDULE_ENTITIES, ex);
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


    private ScheduleEntity extractDictionary(ResultSet rs) throws DBException {
        try {
            ScheduleEntity scheduleEntity = new ScheduleEntity();
            scheduleEntity.setId(rs.getInt(Fields.ID));
            scheduleEntity.setFilmId(rs.getInt(Fields.SCHEDULE_FILM_ID));
            scheduleEntity.setHallId(rs.getInt(Fields.SCHEDULE_HALL_ID));
            long timeMillis = rs.getTimestamp(Fields.SCHEDULE_TIME_START).getTime();
            scheduleEntity.setTimeStart(new Date(timeMillis - 7_200_000));
            return scheduleEntity;
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_SCHEDULE_ENTITY, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_SCHEDULE_ENTITY, e);
        }
    }
}
