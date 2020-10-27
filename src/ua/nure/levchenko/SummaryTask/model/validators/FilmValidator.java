package ua.nure.levchenko.SummaryTask.model.validators;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.model.entity.db.Dictionary;
import ua.nure.levchenko.SummaryTask.model.entity.db.Film;


/**
 * Validator to check the correctness and availability
 * of Film entity input fields.
 *
 * @author K.Levchenko
 */
public class FilmValidator implements Validator<Film> {
    private static final Logger LOG = Logger.getLogger(FilmValidator.class);

    /**
     * Method checking the correctness and availability
     * of Film entity input fields.
     * <p>
     * Method is general and checks only null or not null the fields are.
     *
     * @param film
     * @return
     */
    @Override
    public boolean validate(Film film) {
        try {
            LOG.debug("Validation starts");
            return film != null && film.getName() != null
                    && film.getDescription() != null;
        } finally {
            LOG.debug("Validation finished");
        }
    }


    /**
     * Method checking the correctness and availability
     * of names input fields.
     *
     * @param dictionary
     * @return
     */
    public boolean validateFilmName(Dictionary dictionary) {
        try {
            LOG.debug("Validation starts");
            return dictionary != null && dictionary.getEng() != null
                    && dictionary.getRus() != null && !dictionary.getRus().isEmpty()
                    && !dictionary.getEng().isEmpty();
        } finally {
            LOG.debug("Validation finished");
        }
    }


    /**
     * Method checking the correctness and availability
     * of Dictionary entity input fields especially for
     * Film.description field.
     *
     * @param dictionary
     * @return
     */
    public boolean validateFilmDescription(Dictionary dictionary) {
        try {
            LOG.debug("Validation starts");
            return dictionary != null && dictionary.getEng() != null && dictionary.getRus() != null
                    && !dictionary.getRus().isEmpty() && !dictionary.getEng().isEmpty();
        } finally {
            LOG.debug("Validation finished");
        }
    }
}
