package ua.nure.levchenko.SummaryTask.controller.command.commands.common.authorization;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Attributes;
import ua.nure.levchenko.SummaryTask.controller.jspPath.Path;
import ua.nure.levchenko.SummaryTask.exception.AppException;
import ua.nure.levchenko.SummaryTask.exception.ServiceException;
import ua.nure.levchenko.SummaryTask.model.entity.db.User;
import ua.nure.levchenko.SummaryTask.model.entity.enums.Language;
import ua.nure.levchenko.SummaryTask.model.services.UserService;
import ua.nure.levchenko.SummaryTask.model.utils.language.LanguageDefiner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Log out command.
 *
 * @author K.Levchenko
 */
public class LogoutCommand implements Command {
    private static final Logger LOG = Logger.getLogger(LogoutCommand.class);

    /**
     * Invalidates session
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

        UserService userService = new UserService();

        try {
            // getting current user
            user.setLogged(false);
            user.setLanguage(Language.ENG);
            userService.update(user);
            //setting default locale
            Locale.getDefault();
            // deletes current user from session container and session
            session.invalidate();
        } catch (ServiceException e) {
            LOG.trace("Unexpected internal error.");
            throw new AppException(resourceBundle.getString("unknown_internal_error"), e);
        }

        LOG.debug("Command finished");
        return Path.PAGE_LOGIN;
    }
}
