package ua.nure.levchenko.SummaryTask.controller.command.commands.common.cinema.common.sort;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Actions;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Attributes;
import ua.nure.levchenko.SummaryTask.controller.command.consts.ParamValues;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Parameters;
import ua.nure.levchenko.SummaryTask.controller.jspPath.Path;
import ua.nure.levchenko.SummaryTask.exception.AppException;
import ua.nure.levchenko.SummaryTask.model.entity.db.Film;
import ua.nure.levchenko.SummaryTask.model.entity.db.User;
import ua.nure.levchenko.SummaryTask.model.entity.enums.Language;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;


/**
 * Command for sorting films by its names
 *
 * @author K.Levchenko
 */
public class SortFilmsCommand implements Command {
    private static final Logger LOG = Logger.getLogger(SortFilmsCommand.class);


    /**
     * The method executes commands to sort
     * film list by name
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
        List<Film> films = (List<Film>)
                session.getAttribute(Attributes.FILMS);
        LOG.trace("Attribute required: filmSchedules -->" + films);

        String forward = Path.PAGE_CINEMA;
        if (Actions.SORT_FILMS.equals(action)) {
            String sortNameType = request.getParameter(Parameters.SORT_NAME_TYPE);
            LOG.trace("Parameter required: sortNameType -->" + sortNameType);
            if (sortNameType.equals(ParamValues.A_Z)) {
                //executing sort
                if (user != null) {
                    films = sortByNameAZ(films, user.getLanguage());
                } else {
                    films = sortByNameAZ(films, Language.ENG);
                }
                // setting attribute
                session.setAttribute(Attributes.FILMS, films);
            } else if (sortNameType.equals(ParamValues.Z_A)) {
                //executing sort
                if (user != null) {
                    films = sortByNameZA(films, user.getLanguage());
                } else {
                    films = sortByNameZA(films, Language.ENG);
                }
                // setting attribute
                session.setAttribute(Attributes.FILMS, films);
            }
        }
        return forward;
    }


    /**
     * Sorts film entities by name from A to Z
     *
     * @param films list of film entities
     * @return sorted list of needed film names
     */
    private List<Film> sortByNameAZ(List<Film> films, Language language) {
        List<Film> result = new ArrayList<>();
        Stream<Film> st = films.stream();

        //sorting
        if (language.equals(Language.RU))
            st.sorted(Comparator.comparing(o -> o.getName().getRus()))
                    .forEach(result::add);
        if (language.equals(Language.ENG))
            st.sorted(Comparator.comparing(o -> o.getName().getEng()))
                    .forEach(result::add);

        return result;
    }


    /**
     * Sorts film entities by name from Z to A
     *
     * @param films list of Films entities
     * @return sorted list of needed film names
     */
    private List<Film> sortByNameZA(List<Film> films, Language language) {
        List<Film> result = new ArrayList<>();
        Stream<Film> st = films.stream();

        //sorting
        if (language.equals(Language.RU))
            st.sorted((o1, o2) -> o2.getName().getRus().compareTo(o1.getName().getRus()))
                    .forEach(result::add);
        if (language.equals(Language.ENG))
            st.sorted((o1, o2) -> o2.getName().getEng().compareTo(o1.getName().getEng()))
                    .forEach(result::add);

        return result;
    }
}
