package ua.nure.levchenko.SummaryTask.model.validators;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.model.entity.db.Dictionary;
import ua.nure.levchenko.SummaryTask.model.entity.db.Hall;


/**
 * Validator to check the correctness and availability
 * of Hall entity input fields.
 *
 * @author K.Levchenko
 */
public class HallValidator implements Validator<Hall> {
    private static final Logger LOG = Logger.getLogger(HallValidator.class);

    /**
     * Method checking the correctness and availability
     * of Hall entity input fields.
     * <p>
     * Method is general and checks only null or not null the fields are.
     *
     * @param hall
     * @return
     */
    @Override
    public boolean validate(Hall hall) {
        try {
            LOG.debug("Validation starts");
            return hall != null && hall.getName() != null
                    && hall.getPlacesAmount() != 0;
        } finally {
            LOG.debug("Validation finished");
        }
    }


    /**
     * Method checking the correctness and availability
     * of Dictionary entity input fields especially for
     * Hall.name field.
     *
     * @param dictionary
     * @return
     */
    public boolean validateHallName(Dictionary dictionary) {
        try {
            LOG.debug("Validation starts");
            if (dictionary != null && dictionary.getEng() != null && dictionary.getRus() != null) {
                String engWord = dictionary.getEng().toLowerCase();
                String ruWord = dictionary.getRus().toLowerCase();

                return engWord.matches("[a-z0-9_]+") && ruWord.matches("[а-яё0-9_]+");
            } else {
                return false;
            }
        } finally {
            LOG.debug("Validation finished");
        }
    }
}
