package ua.nure.levchenko.SummaryTask.controller.command.commands.common.cinema.common;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Actions;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Attributes;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Parameters;
import ua.nure.levchenko.SummaryTask.controller.jspPath.Path;
import ua.nure.levchenko.SummaryTask.exception.AppException;
import ua.nure.levchenko.SummaryTask.model.entity.db.Film;
import ua.nure.levchenko.SummaryTask.model.entity.db.Reservation;
import ua.nure.levchenko.SummaryTask.model.entity.db.ScheduleEntity;
import ua.nure.levchenko.SummaryTask.model.services.FilmService;
import ua.nure.levchenko.SummaryTask.model.services.ReservationService;
import ua.nure.levchenko.SummaryTask.model.services.ScheduleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Command that redirects you to the page
 * with the schedule of some film
 *
 * @author K.Levchenko
 */
public class LookScheduleCommand implements Command {
    private static final Logger LOG = Logger.getLogger(LookScheduleCommand.class);


    /**
     * Command redirects you to the cinema page
     * and setting needed arguments to the needed scopes
     * before going to the page. Actually this command
     * then will redirect you to the page where schedule
     * of some film will be displayed
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

        //getting action parameter
        String action = request.getParameter(Parameters.ACTION);
        LOG.trace("Parameter required: action -->" + action);

        // setting attributes
        session.setAttribute(Attributes.FILMS, null);

        String forward = Path.PAGE_CINEMA;
        ScheduleService scheduleService = new ScheduleService();
        FilmService filmService = new FilmService();
        ReservationService reservationService = new ReservationService();
        if (action.equals(Actions.GET_FILM_SCHEDULE)) {
            // getting filmId parameter from the request
            int filmId = Integer.parseInt(request.getParameter(Parameters.FILM_ID));
            LOG.trace("Request parameter: filmId --> " + filmId);
            // getting schedules from DB
            List<ScheduleEntity> filmSchedules = scheduleService.getAllFullByFilm(filmId);
            LOG.trace("Request parameter set: filmSchedules --> " + filmSchedules.toString());
            // define free places amount for current film session
            Map<Integer, Integer> filmFreePlaces = new LinkedHashMap<>();
            for (ScheduleEntity scheduleEntity : filmSchedules) {
                List<Reservation> scheduleReservationList
                        = reservationService.getAllBySchedule(scheduleEntity.getId());
                int fullPlacesAmount = scheduleEntity.getHall().getPlacesAmount();
                int reservedPlacesAmount = scheduleReservationList.size();
                int freePlaces = fullPlacesAmount - reservedPlacesAmount;
                filmFreePlaces.put(scheduleEntity.getId(), freePlaces);
            }

            // setting attribute filmsSchedule
            session.setAttribute(Attributes.FILM_SCHEDULES, filmSchedules);
            // setting attribute filmsSchedule
            session.setAttribute(Attributes.FILM_SCHEDULE_FREE_PLACES, filmFreePlaces);
            // getting current film
            Film film = filmService.readFull(filmId);
            request.setAttribute(Attributes.FILM, film);
        }
        LOG.debug("Command ends");
        return forward;
    }
}
