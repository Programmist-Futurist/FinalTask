package ua.nure.levchenko.SummaryTask.model.validators;

/**
 * Interface of Validators'
 * operations above DB entities.
 *
 * @author K.Levchenko
 */
public interface Validator<Entity> {
    boolean validate(Entity entity);
}
