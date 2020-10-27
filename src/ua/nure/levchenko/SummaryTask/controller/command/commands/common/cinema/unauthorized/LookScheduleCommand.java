package ua.nure.levchenko.SummaryTask.controller.command.commands.common.cinema.unauthorized;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Actions;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Attributes;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Parameters;
import ua.nure.levchenko.SummaryTask.controller.jspPath.Path;
import ua.nure.levchenko.SummaryTask.exception.AppException;
import ua.nure.levchenko.SummaryTask.model.entity.db.Film;
import ua.nure.levchenko.SummaryTask.model.entity.db.ScheduleEntity;
import ua.nure.levchenko.SummaryTask.model.services.FilmService;
import ua.nure.levchenko.SummaryTask.model.services.ScheduleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class LookScheduleCommand implements Command {
    private static final Logger LOG = Logger.getLogger(LookScheduleCommand.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        // getting session
        HttpSession session = request.getSession();

        String action = request.getParameter(Parameters.ACTION);
        LOG.trace("Parameter required: action -->" + action);

        String forward = Path.PAGE_CINEMA;
        ScheduleService scheduleService = new ScheduleService();
        FilmService filmService = new FilmService();
        if (action.equals(Actions.GET_FILM_SCHEDULE)) {
            // getting filmId parameter from the request
            int filmId = Integer.parseInt(request.getParameter(Parameters.FILM_ID));
            LOG.trace("Request parameter: filmId --> " + filmId);
            // getting schedules from DB
            List<ScheduleEntity> filmSchedules = scheduleService.getAllFullByFilm(filmId);
            LOG.trace("Request parameter set: filmSchedules --> " + filmSchedules.toString());

            // setting attribute filmsSchedule
            request.setAttribute(Attributes.FILM_SCHEDULES, filmSchedules);
            // getting current film
            Film film = filmService.readFull(filmId);
            request.setAttribute(Attributes.FILM, film);
        }
        LOG.debug("Command ends");
        return forward;
    }
}
