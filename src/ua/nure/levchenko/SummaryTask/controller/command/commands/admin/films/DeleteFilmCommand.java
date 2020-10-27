package ua.nure.levchenko.SummaryTask.controller.command.commands.admin.films;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Actions;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Attributes;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Parameters;
import ua.nure.levchenko.SummaryTask.controller.jspPath.Path;
import ua.nure.levchenko.SummaryTask.exception.AppException;
import ua.nure.levchenko.SummaryTask.model.entity.db.Film;
import ua.nure.levchenko.SummaryTask.model.services.FilmService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DeleteFilmCommand implements Command {
    private static final Logger LOG = Logger.getLogger(DeleteFilmCommand.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        // getting action parameter from the request
        String action = request.getParameter(Parameters.ACTION);
        LOG.trace("Request parameter: action --> " + action);

        String forward = Path.PAGE_MANAGE_FILM;
        FilmService filmService = new FilmService();
        if (Actions.GET_FILM_TO_DELETE.equals(action)) {
            // getting filmId parameter from the request
            int filmId = Integer.parseInt(request.getParameter(Parameters.FILM_ID_GET));
            LOG.trace("Request parameter: filmId --> " + filmId);
            // getting film from DB
            Film film = filmService.readFull(filmId);
            LOG.trace("Request parameter: film --> " + film.toString());
            // setting attribute films
            request.setAttribute(Attributes.FILM, film);
        } else if (Actions.DELETE_FILM_CONFIRM.equals(action)) {
            // getting filmId parameter from the request
            int filmId = Integer.parseInt(request.getParameter(Parameters.FILM_ID_DELETE));
            LOG.trace("Request parameter: action --> " + action);
            // deleting film from DB
            filmService.delete(filmId);
            // setting request attributes
            request.setAttribute(Attributes.INFO_MESSAGE, "Film successfully deleted.");
            // setting appScope attribute "films"
            List<Film> films = filmService.getAllFull();
            // setting films to application scope
            request.getServletContext().setAttribute(Attributes.FILMS, films);
            // set film attribute on current page equals to null
            request.setAttribute(Attributes.FILM, null);
        }
        request.setAttribute(Attributes.FILM_ACTIONS, 3);
        LOG.debug("Command finishes");
        return forward;
    }


}
