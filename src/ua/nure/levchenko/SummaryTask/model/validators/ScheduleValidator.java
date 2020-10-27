package ua.nure.levchenko.SummaryTask.model.validators;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.model.entity.db.ScheduleEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


/**
 * Validator to check the correctness and availability
 * of Schedule entity input fields.
 *
 * @author K.Levchenko
 */
public class ScheduleValidator implements Validator<ScheduleEntity> {
    private static final Logger LOG = Logger.getLogger(ScheduleValidator.class);


    /**
     * Method checking the correctness and availability
     * of ScheduleEntity entity input fields.
     * <p>
     * Method is general and checks only null or not null the fields are.
     *
     * @param scheduleEntity
     * @return
     */
    @Override
    public boolean validate(ScheduleEntity scheduleEntity) {
        try {
            LOG.debug("Validation starts");
            return scheduleEntity != null && scheduleEntity.getFilmId() != 0
                    && scheduleEntity.getHallId() != 0 && scheduleEntity.getTimeStart() != null
                    && validateScheduleTime(scheduleEntity.getTimeStart());
        } finally {
            LOG.debug("Validation finished");
        }
    }


    /**
     * Method checking the correctness of Schedule entity time
     * <p>
     * From 9:00 to 22:00
     *
     * @param date time when th film is started
     * @return true - if validation passed,
     * false - when validation did not passed
     */
    private boolean validateScheduleTime(Date date) {
        try {
            LOG.debug("Validation starts");
            LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
            int hour = localDateTime.getHour();
            return hour > 9 && hour < 22;
        } finally {
            LOG.debug("Validation finished");
        }
    }
}
