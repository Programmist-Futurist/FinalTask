package ua.nure.levchenko.SummaryTask.model.db.dao;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.exception.DBException;
import ua.nure.levchenko.SummaryTask.exception.constants.Messages;
import ua.nure.levchenko.SummaryTask.model.db.DBManager;
import ua.nure.levchenko.SummaryTask.model.db.constants.Fields;
import ua.nure.levchenko.SummaryTask.model.db.constants.SQLCommands;
import ua.nure.levchenko.SummaryTask.model.entity.db.Dictionary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that implements methods
 * for working with DB for Dictionary entity.
 *
 * @author K.Levchenko
 */
public class DictionaryDao implements DAO<Dictionary, Integer> {
    private static final Logger LOG = Logger.getLogger(DictionaryDao.class);

    /**
     * Creates the new word entity in dataBase.
     *
     * @param dictionary object of Dictionary
     */
    public void create(Dictionary dictionary) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_CREATE_DICTIONARY, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, dictionary.getEng());
            pstmt.setString(2, dictionary.getRus());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    dictionary.setId((int) generatedKeys.getLong(1));
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
     * Extracts the dictionary entity from dataBase.
     *
     * @param id the name of the dictionary that must be extracted
     * @return object of the Dictionary
     */
    @Override
    synchronized public Dictionary read(Integer id) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(SQLCommands.SQL_READ_DICTIONARY_BY_ID);
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
     * Updates the dictionary entity in dataBase.
     *
     * @param dictionary object of Dictionary
     */
    @Override
    public void update(Dictionary dictionary) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_UPDATE_DICTIONARY);
            pstmt.setString(1, dictionary.getEng());
            pstmt.setString(2, dictionary.getRus());
            pstmt.setInt(3, dictionary.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_UPDATE_DICTIONARY, e);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_DICTIONARY, e);
        }
    }

    /**
     * Deletes the dictionary entity from dataBase.
     *
     * @param id id of object of Dictionary
     */
    @Override
    public void delete(Integer id) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_DELETE_DICTIONARY_BY_ID);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_DELETE_DICTIONARY, ex);
            throw new DBException(Messages.ERR_CANNOT_DELETE_DICTIONARY, ex);
        }
    }


    /**
     * Returns all journal entities.
     *
     * @return List of journal entities.
     */
    @Override
    synchronized public List<Dictionary> getAll() throws DBException {
        DBManager dbManager = DBManager.getInstance();
        List<Dictionary> dictionaryList = new ArrayList<>();
        Connection con = null;
        try {
            con = dbManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_GET_DICTIONARY_ORDER_BY_ID);
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
            LOG.error(Messages.ERR_CANNOT_GET_FULL_DICTIONARY, ex);
            throw new DBException(Messages.ERR_CANNOT_GET_FULL_DICTIONARY, ex);
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


    private Dictionary extractDictionary(ResultSet rs) throws DBException {
        try {
            Dictionary dictionary = new Dictionary();
            dictionary.setId(rs.getInt(Fields.ID));
            dictionary.setEng(rs.getString(Fields.DICTIONARY_ENG));
            dictionary.setRus(rs.getString(Fields.DICTIONARY_RUS));
            return dictionary;
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_DICTIONARY, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_DICTIONARY, e);
        }
    }

}
