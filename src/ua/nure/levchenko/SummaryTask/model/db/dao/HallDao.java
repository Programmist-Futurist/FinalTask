package ua.nure.levchenko.SummaryTask.model.db.dao;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.exception.DBException;
import ua.nure.levchenko.SummaryTask.exception.constants.Messages;
import ua.nure.levchenko.SummaryTask.model.db.DBManager;
import ua.nure.levchenko.SummaryTask.model.db.constants.Fields;
import ua.nure.levchenko.SummaryTask.model.db.constants.SQLCommands;
import ua.nure.levchenko.SummaryTask.model.entity.db.Hall;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Class that implements methods
 * for working with DB for Hall entity.
 *
 * @author K.Levchenko
 */
public class HallDao implements DAO<Hall, Integer> {
    private static final Logger LOG = Logger.getLogger(HallDao.class);

    /**
     * Creates the new hall entity in dataBase.
     *
     * @param hall object of Hall
     */
    public void create(Hall hall) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_CREATE_HALL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, hall.getNameId());
            pstmt.setInt(2, hall.getPlacesAmount());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    hall.setId((int) generatedKeys.getLong(1));
                } else {
                    LOG.error(Messages.ERR_CANNOT_CREATE_DICTIONARY);
                    throw new DBException("Creating user failed, no ID obtained.", new SQLException());
                }
            }
            con.commit();
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_CREATE_DICTIONARY, e);
            throw new DBException(Messages.ERR_CANNOT_CREATE_DICTIONARY, e);
        }
    }

    /**
     * Extracts the hall entity from dataBase.
     *
     * @param id the name of the hall that must be extracted
     * @return object of the Hall
     */
    @Override
    synchronized public Hall read(Integer id) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(SQLCommands.SQL_READ_HALL_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next())
                return extractDictionary(rs);
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_READ_DICTIONARY, e);
            throw new DBException(Messages.ERR_CANNOT_READ_DICTIONARY, e);
        }
        return null;
    }

    /**
     * Updates the hall entity in dataBase.
     *
     * @param hall object of Hall
     */
    @Override
    public void update(Hall hall) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_UPDATE_HALL);
            pstmt.setInt(1, hall.getNameId());
            pstmt.setInt(2, hall.getPlacesAmount());
            pstmt.setInt(3, hall.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_UPDATE_HALL, e);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_HALL, e);
        }
    }

    /**
     * Deletes the hall entity from dataBase.
     *
     * @param id id of object of Hall
     */
    @Override
    public void delete(Integer id) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_DELETE_HALL_BY_ID);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_DELETE_HALL, ex);
            throw new DBException(Messages.ERR_CANNOT_DELETE_HALL, ex);
        }
    }


    /**
     * Returns all hall entities.
     *
     * @return List of hall entities.
     */
    @Override
    synchronized public List<Hall> getAll() throws DBException {
        DBManager dbManager = DBManager.getInstance();
        List<Hall> dictionaryList = new ArrayList<>();
        Connection con = null;
        try {
            con = dbManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_GET_HALLS_ORDER_BY_ID);
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
            LOG.error(Messages.ERR_CANNOT_GET_ALL_HALLS, ex);
            throw new DBException(Messages.ERR_CANNOT_GET_ALL_HALLS, ex);
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


    private Hall extractDictionary(ResultSet rs) throws DBException {
        try {
            Hall hall = new Hall();
            hall.setId(rs.getInt(Fields.ID));
            hall.setNameId(rs.getInt(Fields.HALL_NAME_ID));
            hall.setPlacesAmount(rs.getInt(Fields.HALL_PLACES_AMOUNT));
            return hall;
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_HALL, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_HALL, e);
        }
    }
}
