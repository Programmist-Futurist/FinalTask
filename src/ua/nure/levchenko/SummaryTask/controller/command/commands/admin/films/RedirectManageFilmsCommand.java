package ua.nure.levchenko.SummaryTask.controller.command.commands.admin.films;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.command.commands.common.authorization.LoginCommand;
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


/**
 * Command to redirect between create/update/delete
 * commands in UI
 * (For admin only)
 *
 * @author K.Levchenko
 */
public class RedirectManageFilmsCommand implements Command {
    private static final Logger LOG = Logger.getLogger(LoginCommand.class);


    /**
     * Method, redirects user to the different
     * pages, dependent on required parameters
     * (For admin only)
     *
     * @param request
     * @param response
     * @return
     * @throws AppException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        // getting action parameter from the request
        String action = request.getParameter(Parameters.ACTION);
        LOG.trace("Request parameter: action --> " + action);

        String forward = Path.PAGE_MANAGE_FILM;
        FilmService filmService = new FilmService();
        if (Actions.CREATE_FILM.equals(action)) {
            request.setAttribute(Attributes.FILM_ACTIONS, 1);
        } else if (Actions.EDIT_FILM.equals(action)) {
            // setting appScope attribute "films"
            List<Film> films = filmService.getAllFull();
            // setting films to application scope
            request.getServletContext().setAttribute(Attributes.FILMS, films);
            request.setAttribute(Attributes.FILM_ACTIONS, 2);
        } else if (Actions.DELETE_FILM.equals(action)) {
            // setting appScope attribute "films"
            List<Film> films = filmService.getAllFull();
            // setting films to application scope
            request.getServletContext().setAttribute(Attributes.FILMS, films);
            request.setAttribute(Attributes.FILM_ACTIONS, 3);
        }
        LOG.debug("Command finishes");
        return forward;
    }
}