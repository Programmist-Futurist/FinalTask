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
import java.util.ArrayList;
import java.util.List;

public class CinemaCommand implements Command {
    private static final Logger LOG = Logger.getLogger(CinemaCommand.class);

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
        List<Integer> placesToOrder = new ArrayList<>();
        session.setAttribute(Attributes.PLACES_TO_ORDER, null);

        FilmService filmService = new FilmService();
        // getting all schedules from DB
        List<Film> films = filmService.getAllFull();
        LOG.trace("Request parameter set: films --> " + films.toString());
        // setting attribute filmsSchedule in application scope
        request.setAttribute(Attributes.FILMS, films);

        LOG.debug("Command ends");
        return Path.PAGE_CINEMA;
    }
}
