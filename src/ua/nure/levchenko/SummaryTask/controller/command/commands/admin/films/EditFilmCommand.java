package ua.nure.levchenko.SummaryTask.controller.command.commands.admin.films;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Actions;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Attributes;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Parameters;
import ua.nure.levchenko.SummaryTask.controller.command.webValidators.FilmWebValidator;
import ua.nure.levchenko.SummaryTask.controller.jspPath.Path;
import ua.nure.levchenko.SummaryTask.exception.AppException;
import ua.nure.levchenko.SummaryTask.exception.ServiceException;
import ua.nure.levchenko.SummaryTask.model.entity.db.Dictionary;
import ua.nure.levchenko.SummaryTask.model.entity.db.Film;
import ua.nure.levchenko.SummaryTask.model.entity.db.User;
import ua.nure.levchenko.SummaryTask.model.services.FilmService;
import ua.nure.levchenko.SummaryTask.model.utils.language.LanguageDefiner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Command to updated film entity in db
 * (For admin only)
 *
 * @author K.Levchenko
 */
public class EditFilmCommand implements Command {
    private static final Logger LOG = Logger.getLogger(EditFilmCommand.class);


    /**
     * This method validates all fields before
     * creating film in DB, and also updates it if
     * everything is ok, and returns error message if
     * smth goes wrong
     *
     * @param request
     * @param response
     * @return
     * @throws AppException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        // getting session
        HttpSession session = request.getSession();
        //getting user from session
        User user = (User) session.getAttribute(Attributes.USER);
        // define currentLang
        LanguageDefiner languageDefiner = new LanguageDefiner();
        ResourceBundle resourceBundle = languageDefiner.getBundle(user);

        // getting action parameter from the request
        String action = request.getParameter(Parameters.ACTION);
        LOG.trace("Request parameter: action --> " + action);

        String forward = Path.PAGE_MANAGE_FILM;
        FilmService filmService = new FilmService();
        try {
            if (Actions.GET_FILM_TO_EDIT.equals(action)) {
                // getting filmId parameter from the request
                int filmId = Integer.parseInt(request.getParameter(Parameters.FILM_ID_GET));
                LOG.trace("Request parameter: filmId --> " + filmId);
                // getting film from DB
                Film film = filmService.readFull(filmId);
                LOG.trace("Request parameter: film --> " + film.toString());
                // setting attribute filmMap
                request.setAttribute(Attributes.FILM, film);
            } else if (Actions.EDIT_FILM_CONFIRM.equals(action)) {
                // getting filmId parameter from the request
                int filmId = Integer.parseInt(request.getParameter(Parameters.FILM_ID_EDIT));
                LOG.trace("Request parameter: filmId --> " + filmId);
                String nameRu = request.getParameter(Parameters.FILM_NAME_RU);
                LOG.trace("Request parameter: nameRu --> " + nameRu);
                String nameEng = request.getParameter(Parameters.FILM_NAME_ENG);
                LOG.trace("Request parameter: nameEng --> " + nameEng);
                String descriptionRu = request.getParameter(Parameters.FILM_DESCRIPTION_RU);
                LOG.trace("Request parameter: descriptionRu --> " + descriptionRu);
                String descriptionEng = request.getParameter(Parameters.FILM_DESCRIPTION_ENG);
                LOG.trace("Request parameter: descriptionEng --> " + descriptionEng);
                // reading film
                Film currentFilm = filmService.readFull(filmId);
                // clone film
                Film film = currentFilm.clone();
                film = updatingFieldsForFilmObject(film, nameRu, nameEng, descriptionRu, descriptionEng);
                // validating film
                FilmWebValidator filmWebValidator = new FilmWebValidator();
                boolean isFilmValid = filmWebValidator.validateFilmOnUpdate(film, request, resourceBundle);
                if (isFilmValid) {
                    // update film in DB
                    filmService.update(film);
                    // setting request attributes
                    request.setAttribute(Attributes.INFO_MESSAGE,
                            resourceBundle.getString("create_film_command.film_updated"));
                    // setting appScope attribute "films"
                    List<Film> films = filmService.getAllFull();
                    // setting films to application scope
                    request.getServletContext().setAttribute(Attributes.FILMS, films);
                    // setting film attribute
                    request.setAttribute(Attributes.FILM, film);
                } else {
                    request.setAttribute(Attributes.FILM, currentFilm);
                }
            }
        } catch (ServiceException e) {
            LOG.error("Cannot update film in DB");
            throw new AppException(resourceBundle.getString("unknown_internal_error"), e);
        }
        request.setAttribute(Attributes.FILM_ACTIONS, 2);
        LOG.debug("Command finishes");
        return forward;
    }

    /**
     * This method creates Film object
     * fulfilled with input parameter values.
     * <p>
     * After this method, film object should be validated.
     *
     * @param nameRu
     * @param nameEng
     * @param descriptionRu
     * @param descriptionEng
     */
    private Film updatingFieldsForFilmObject(Film filmToEdit, String nameRu, String nameEng, String descriptionRu,
                                             String descriptionEng) {
        Dictionary dictionaryName = new Dictionary(nameEng, nameRu);
        Dictionary dictionaryDescription = new Dictionary(descriptionEng, descriptionRu);
        // setting film field values
        filmToEdit.setName(dictionaryName);
        filmToEdit.setDescription(dictionaryDescription);
        return filmToEdit;
    }


}
