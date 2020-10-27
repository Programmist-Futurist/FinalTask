package ua.nure.levchenko.SummaryTask.model.validators;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.model.entity.db.Reservation;


/**
 * Validator to check the correctness and availability
 * of Reservation entity input fields.
 *
 * @author K.Levchenko
 */
public class ReservationValidator implements Validator<Reservation> {
    private static final Logger LOG = Logger.getLogger(ReservationValidator.class);


    /**
     * Method checking the correctness and availability
     * of Reservation entity input fields.
     * <p>
     * Method is general and checks only null or not null the fields are.
     *
     * @param reservation
     * @return
     */
    @Override
    public boolean validate(Reservation reservation) {
        try {
            LOG.debug("Validation starts");
            return reservation != null && reservation.getScheduleId() != 0
                    && reservation.getUserId() != 0;
        } finally {
            LOG.debug("Validation finished");
        }
    }
}
