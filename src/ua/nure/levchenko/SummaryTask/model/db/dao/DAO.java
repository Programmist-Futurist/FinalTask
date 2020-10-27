package ua.nure.levchenko.SummaryTask.model.db.dao;

import ua.nure.levchenko.SummaryTask.exception.DBException;

import java.util.List;


/**
 * Interface of CRUD
 * operations for DB entities.
 *
 * @author K.Levchenko
 */
public interface DAO<Entity, Key> {
    void create(Entity entity) throws DBException;

    Entity read(Key key) throws DBException;

    void update(Entity entity) throws DBException;

    void delete(Key key) throws DBException;

    List<Entity> getAll() throws DBException;
}
