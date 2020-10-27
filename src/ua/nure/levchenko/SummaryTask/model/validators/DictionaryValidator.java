package ua.nure.levchenko.SummaryTask.model.validators;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.model.entity.db.Dictionary;


/**
 * Validator to check the correctness and availability
 * of Dictionary entity input fields.
 *
 * @author K.Levchenko
 */
public class DictionaryValidator implements Validator<Dictionary> {
    private static final Logger LOG = Logger.getLogger(DictionaryValidator.class);

    /**
     * Method checking the correctness and availability
     * of Dictionary entity input fields.
     * <p>
     * Method is general and checks only null or not null the fields are.
     *
     * @param dictionary
     * @return
     */
    @Override
    public boolean validate(Dictionary dictionary) {
        try {
            LOG.debug("Validation starts");
            return dictionary != null && dictionary.getEng() != null && dictionary.getRus() != null;
        } finally {
            LOG.debug("Validation finished");
        }
    }
}
