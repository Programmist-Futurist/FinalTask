package ua.nure.levchenko.SummaryTask.controller.command.commands.common.cinema.common.sort;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Actions;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Attributes;
import ua.nure.levchenko.SummaryTask.controller.command.consts.ParamValues;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Parameters;
import ua.nure.levchenko.SummaryTask.controller.jspPath.Path;
import ua.nure.levchenko.SummaryTask.exception.AppException;
import ua.nure.levchenko.SummaryTask.model.entity.db.ScheduleEntity;
import ua.nure.levchenko.SummaryTask.model.entity.db.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Stream;


/**
 * Command for sorting schedule of some
 * particular film
 *
 * @author K.Levchenko
 */
public class SortScheduleCommand implements Command {
    private static final Logger LOG = Logger.getLogger(SortScheduleCommand.class);


    /**
     * The method executes commands to sort
     * schedule of some film by time and by free places
     * in the cinema hall
     *
     * @param request
     * @param response
     * @return
     * @throws AppException if some unexpected error occurred
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        // getting session
        HttpSession session = request.getSession();
        //getting current user from session
        User user = (User) session.getAttribute(Attributes.USER);

        // getting action attribute from request
        String action = request.getParameter(Parameters.ACTION);
        LOG.trace("Parameter required: action -->" + action);

        // getting attributes from context
        List<ScheduleEntity> filmSchedules = (List<ScheduleEntity>)
                session.getAttribute(Attributes.FILM_SCHEDULES);
        LOG.trace("Attribute required: filmSchedules -->" + filmSchedules);
        Map<Integer, Integer> filmFreePlaces = (Map<Integer, Integer>)
                session.getAttribute(Attributes.FILM_SCHEDULE_FREE_PLACES);
        LOG.trace("Parameter required: filmFreePlaces -->" + filmFreePlaces);

        String forward = Path.PAGE_CINEMA;
        if (Actions.SORT_SCHEDULE_TIME.equals(action)) {
            String sortTimeType = request.getParameter(Parameters.SORT_TIME_TYPE);
            LOG.trace("Parameter required: sortTimeType -->" + sortTimeType);
            if (sortTimeType.equals(ParamValues.TIME_MIN_MAX)) {
                //executing sort
                filmSchedules = sortByTimeMinMax(filmSchedules);
                // setting attribute
                session.setAttribute(Attributes.FILM_SCHEDULES, filmSchedules);
            } else if (sortTimeType.equals(ParamValues.TIME_MAX_MIN)) {
                //executing sort
                filmSchedules = sortByTimeMaxMin(filmSchedules);
                // setting attribute
                session.setAttribute(Attributes.FILM_SCHEDULES, filmSchedules);
            }
        } else if (Actions.SORT_SCHEDULE_PLACES.equals(action)) {
            // creating Map<freePlacesAmount, ScheduleEntity>
            Map<ScheduleEntity, Integer> schedulesMap = new HashMap<>();
            for (ScheduleEntity scheduleEntity : filmSchedules) {
                int amountOfFreePlaces = filmFreePlaces.get(scheduleEntity.getId());
                schedulesMap.put(scheduleEntity, amountOfFreePlaces);
            }
            // getting parameter from request
            String sortFreePlacesType = request.getParameter(Parameters.SORT_FREE_PLACES_TYPE);
            LOG.trace("Parameter required: sortFreePlacesType -->" + sortFreePlacesType);

            if (sortFreePlacesType.equals(ParamValues.PLACES_MIN_MAX)) {
                //executing sort
                filmSchedules = sortByFreePlacesAmountMinMax(schedulesMap);
                // setting attribute
                session.setAttribute(Attributes.FILM_SCHEDULES, filmSchedules);
            } else if (sortFreePlacesType.equals(ParamValues.PLACES_MAX_MIN)) {
                //executing sort
                filmSchedules = sortByFreePlacesAmountMaxMin(schedulesMap);
                // setting attribute
                session.setAttribute(Attributes.FILM_SCHEDULES, filmSchedules);
            }
        }
        return forward;
    }


    /**
     * Sorts schedule entities by film session starts
     * from the latest film to the nearest.
     *
     * @param filmSchedule list of schedule entities of some film
     * @return sorted list of needed film schedule names
     */
    private List<ScheduleEntity> sortByTimeMaxMin(List<ScheduleEntity> filmSchedule) {
        List<ScheduleEntity> result = new ArrayList<>();
        Stream<ScheduleEntity> st = filmSchedule.stream();

        //sorting
        st.sorted((o1, o2) -> o2.getTimeStart().compareTo(o1.getTimeStart()))
                .forEach(result::add);

        return result;
    }


    /**
     * Sorts schedule entities by film session starts time
     * from the nearest film to the latest
     *
     * @param filmSchedule list of schedule entities of some film
     * @return sorted list of needed film schedule names
     */
    private List<ScheduleEntity> sortByTimeMinMax(List<ScheduleEntity> filmSchedule) {
        List<ScheduleEntity> result = new ArrayList<>();
        Stream<ScheduleEntity> st = filmSchedule.stream();

        //sorting
        st.sorted(Comparator.comparing(ScheduleEntity::getTimeStart))
                .forEach(result::add);

        return result;
    }


    /**
     * Sorts schedule entities by film session
     * free places from the less amount of free places
     * to higher amount
     *
     * @param filmScheduleMap list of schedule entities of some film
     * @return sorted list of needed film schedule names
     */
    private List<ScheduleEntity> sortByFreePlacesAmountMinMax(Map<ScheduleEntity, Integer> filmScheduleMap) {
        List<ScheduleEntity> result = new ArrayList<>();
        Stream<Map.Entry<ScheduleEntity, Integer>> st = filmScheduleMap.entrySet().stream();

        // sorting
        st.sorted(Comparator.comparingInt(Map.Entry::getValue))
                .forEach(o -> result.add(o.getKey()));

        return result;
    }


    /**
     * Sorts schedule entities by film session
     * free places from the higher amount of free places
     * to less amount
     *
     * @param filmScheduleMap list of schedule entities of some film
     * @return sorted list of needed film schedule names
     */
    private List<ScheduleEntity> sortByFreePlacesAmountMaxMin(Map<ScheduleEntity, Integer> filmScheduleMap) {
        List<ScheduleEntity> result = new ArrayList<>();
        Stream<Map.Entry<ScheduleEntity, Integer>> st = filmScheduleMap.entrySet().stream();

        // sorting
        st.sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .forEach(o -> result.add(o.getKey()));

        return result;
    }
}
