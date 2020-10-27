package ua.nure.levchenko.SummaryTask.model.services;

import ua.nure.levchenko.SummaryTask.exception.ServiceException;

import java.util.List;

/**
 * Interface for Services'
 * operations above DB entities.
 *
 * @author K.Levchenko
 */
public interface Service<Entity, Key> {
    void create(Entity entity) throws ServiceException;

    Entity read(Key id) throws ServiceException;

    void update(Entity entity) throws ServiceException;

    void delete(Key id) throws ServiceException;

    List<Entity> getAll() throws ServiceException;

}
