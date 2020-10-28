package ua.nure.levchenko.SummaryTask.controller.command.commands.common.cinema;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Attributes;
import ua.nure.levchenko.SummaryTask.controller.jspPath.Path;
import ua.nure.levchenko.SummaryTask.exception.AppException;
import ua.nure.levchenko.SummaryTask.model.entity.db.Film;
import ua.nure.levchenko.SummaryTask.model.services.FilmService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * Command that redirects you to the cinema page
 *
 * @author K.Levchenko
 */
public class CinemaCommand implements Command {
    private static final Logger LOG = Logger.getLogger(CinemaCommand.class);

    /**
     * Command redirects you to the cinema page
     * and setting needed arguments to the needed scopes
     * before going to the page
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
        // setting attribute currentScheduleEntity
        session.setAttribute(Attributes.CURRENT_SCHEDULE_ENTITY, null);
        // setting attribute currentHallPlaces
        session.setAttribute(Attributes.PLACES, null);
        // setting attribute placesToOrder
        session.setAttribute(Attributes.PLACES_TO_ORDER, null);
        // setting attribute filmsSchedule
        session.setAttribute(Attributes.FILM_SCHEDULES, null);
        // setting attribute filmsSchedule
        session.setAttribute(Attributes.FILM_SCHEDULE_FREE_PLACES, null);

        FilmService filmService = new FilmService();
        // getting all schedules from DB
        List<Film> films = filmService.getAllFull();
        LOG.trace("Request parameter set: films --> " + films.toString());
        // setting attribute filmsSchedule in application scope
        session.setAttribute(Attributes.FILMS, films);

        LOG.debug("Command ends");
        return Path.PAGE_CINEMA;
    }
}
