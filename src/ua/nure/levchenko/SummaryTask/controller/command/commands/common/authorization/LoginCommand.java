package ua.nure.levchenko.SummaryTask.controller.command.commands.common.authorization;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Actions;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Attributes;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Parameters;
import ua.nure.levchenko.SummaryTask.controller.jspPath.Path;
import ua.nure.levchenko.SummaryTask.exception.AppException;
import ua.nure.levchenko.SummaryTask.exception.ServiceException;
import ua.nure.levchenko.SummaryTask.exception.constants.Messages;
import ua.nure.levchenko.SummaryTask.model.db.dao.UserDao;
import ua.nure.levchenko.SummaryTask.model.entity.db.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Login command.
 *
 * @author K.Levchenko
 */
public class LoginCommand implements Command {
    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");
        // setting default language
        ResourceBundle resourceBundle = ResourceBundle.getBundle
                ("resources", new Locale("en", "US"));

        //getting session
        HttpSession session = request.getSession();

        // getting action parameter from the request
        String action = request.getParameter(Parameters.ACTION);
        LOG.trace("Request parameter: action --> " + action);

        String forward = Path.PAGE_LOGIN;

        try {
            if (action != null && action.equals(Actions.LOGIN_BUTTON)) {
                // getting entered user values
                String login = request.getParameter(Parameters.LOGIN);
                String password = request.getParameter(Parameters.PASSWORD);

                if (login != null && password != null
                        && !login.isEmpty() && !password.isEmpty()) {
                    // getting user from DB if exists
                    UserDao userDao = new UserDao();
                    User user = userDao.read(login, password);
                    if (user != null) {
                        session.setAttribute(Attributes.USER, user);
                        forward = Path.PROFILE_PAGE;
                    } else {
                        request.setAttribute(Attributes.ERROR_MESSAGE,
                                resourceBundle.getString
                                        ("login_jsp.text.incorrect_login_or_password"));
                    }
                } else {
                    request.setAttribute(Attributes.ERROR_MESSAGE,
                            resourceBundle.getString
                                    ("login_jsp.text.empty_login_or_password"));
                }
            } else if (action != null && action.equals(Actions.SIGN_UP)) {
                forward = Path.PAGE_SING_UP;
            }
        } catch (ServiceException e) {
            LOG.error(Messages.ERR_CANNOT_LOG_USER_IN, e);
            throw new AppException(resourceBundle.getString("log_in_command.app_exception"), e);
        }
        return forward;
    }
}