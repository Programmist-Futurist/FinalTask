package ua.nure.levchenko.SummaryTask.controller.command.commands.common.settings;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.jspPath.Path;
import ua.nure.levchenko.SummaryTask.exception.AppException;
import ua.nure.levchenko.SummaryTask.model.db.dao.UserDao;
import ua.nure.levchenko.SummaryTask.model.entity.db.User;
import ua.nure.levchenko.SummaryTask.model.entity.enums.Language;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Setting needed language command.
 *
 * @author K.Levchenko
 */
public class SetLanguageCommand implements Command {
    private static final Logger LOG = Logger.getLogger(SetLanguageCommand.class);


    /**
     * Main method of the setting
     * needed language command
     *
     * @param request
     * @param response
     * @return
     * @throws AppException if some unexpected error occurred
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        //getting session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String locale = request.getParameter("locale");
        LOG.trace("Parameter required: locale -->" + locale);

        UserDao userDao = new UserDao();
        if ("ru".equals(locale)) {
            // language defining
            user.setLanguage(Language.RU);
            userDao.update(user);

            LOG.trace("Language was set --> " + Language.RU);
        } else {
            // dictionary for entities
            user.setLanguage(Language.ENG);
            userDao.update(user);

            LOG.trace("Language was set --> " + Language.ENG);
        }

        // updating user in session
        session.setAttribute("user", user);
        LOG.trace("Session updated");

        LOG.debug("Command finished");
        return Path.PAGE_CHANGE_LOCALE;
    }
}
