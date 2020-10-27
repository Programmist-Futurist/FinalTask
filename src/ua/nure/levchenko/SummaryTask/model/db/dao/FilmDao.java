package ua.nure.levchenko.SummaryTask.model.db.dao;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.exception.DBException;
import ua.nure.levchenko.SummaryTask.exception.constants.Messages;
import ua.nure.levchenko.SummaryTask.model.db.DBManager;
import ua.nure.levchenko.SummaryTask.model.db.constants.Fields;
import ua.nure.levchenko.SummaryTask.model.db.constants.SQLCommands;
import ua.nure.levchenko.SummaryTask.model.entity.db.Film;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that implements methods
 * for working with DB for Film entity.
 *
 * @author K.Levchenko
 */
public class FilmDao implements DAO<Film, Integer> {
    private static final Logger LOG = Logger.getLogger(FilmDao.class);

    /**
     * Creates the new film entity in dataBase.
     *
     * @param film object of Film
     */
    @Override
    public void create(Film film) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_CREATE_FILM, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, film.getNameId());
            pstmt.setInt(2, film.getDescriptionId());
//            pstmt.setString(3, film.getImage().getAbsolutePath());
            pstmt.setString(3, "");
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    film.setId((int) generatedKeys.getLong(1));
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
     * Extracts the film entity from dataBase.
     *
     * @param id the name of the film that must be extracted
     * @return object of the Film
     */
    @Override
    synchronized public Film read(Integer id) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(SQLCommands.SQL_READ_FILM_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next())
                return extractDictionary(rs);
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_READ_FILM, e);
            throw new DBException(Messages.ERR_CANNOT_READ_FILM, e);
        }
        return null;
    }

    /**
     * Updates the film entity in dataBase.
     *
     * @param film object of Film
     */
    @Override
    public void update(Film film) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_UPDATE_FILM);
            pstmt.setInt(1, film.getNameId());
            pstmt.setInt(2, film.getDescriptionId());
//            pstmt.setString(3, film.getImage().getAbsolutePath());
            pstmt.setString(3, "");
            pstmt.setInt(4, film.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_UPDATE_DICTIONARY, e);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_DICTIONARY, e);
        }
    }

    /**
     * Deletes the film entity from dataBase.
     *
     * @param id id of object of Film
     */
    @Override
    public void delete(Integer id) throws DBException {
        DBManager dbManager = DBManager.getInstance();
        try (Connection con = dbManager.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_DELETE_FILM_BY_ID);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_DELETE_FILM, ex);
            throw new DBException(Messages.ERR_CANNOT_DELETE_FILM, ex);
        }
    }


    /**
     * Returns all journal entities.
     *
     * @return List of journal entities.
     */
    @Override
    synchronized public List<Film> getAll() throws DBException {
        DBManager dbManager = DBManager.getInstance();
        List<Film> dictionaryList = new ArrayList<>();
        Connection con = null;
        try {
            con = dbManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQLCommands.SQL_GET_FILMS_ORDER_BY_ID);
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
            LOG.error(Messages.ERR_CANNOT_GET_ALL_FILM, ex);
            throw new DBException(Messages.ERR_CANNOT_GET_ALL_FILM, ex);
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


    private Film extractDictionary(ResultSet rs) throws DBException {
        try {
            Film film = new Film();
            film.setId(rs.getInt(Fields.ID));
            film.setNameId(rs.getInt(Fields.FILM_NAME_ID));
            film.setDescriptionId(rs.getInt(Fields.FILM_DESCRIPTION_ID));
            film.setImage(new File(rs.getString(Fields.FILM_IMAGE)));
            return film;
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_FILM, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_FILM, e);
        }
    }
}
