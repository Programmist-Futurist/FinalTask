package ua.nure.levchenko.SummaryTask.controller.command.commands.common.authorization;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.jspPath.Path;
import ua.nure.levchenko.SummaryTask.exception.AppException;
import ua.nure.levchenko.SummaryTask.exception.ServiceException;
import ua.nure.levchenko.SummaryTask.model.entity.db.User;
import ua.nure.levchenko.SummaryTask.model.entity.enums.Language;
import ua.nure.levchenko.SummaryTask.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;


/**
 * Log out command.
 *
 * @author K.Levchenko
 */
public class LogoutCommand implements Command {
    private static final Logger LOG = Logger.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        HttpSession session = request.getSession(false);
        UserService userService = new UserService();

        try {
            if (session != null) {
                // getting current user
                User user = (User) session.getAttribute("user");
                user.setLogged(false);
                user.setLanguage(Language.ENG);
                userService.update(user);
                //setting default locale
                Locale.getDefault();
                // deletes current user from session container and session
                session.invalidate();
            }
        } catch (ServiceException e) {
            LOG.trace("Unexpected internal error.");
            throw new AppException("Unexpected internal error.", e);
        }

        LOG.debug("Command finished");
        return Path.PAGE_LOGIN;
    }
}
