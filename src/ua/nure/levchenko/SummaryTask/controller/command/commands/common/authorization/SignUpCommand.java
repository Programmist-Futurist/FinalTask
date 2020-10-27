package ua.nure.levchenko.SummaryTask.controller.command.commands.common.authorization;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Actions;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Attributes;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Parameters;
import ua.nure.levchenko.SummaryTask.controller.command.webValidators.UserWebValidator;
import ua.nure.levchenko.SummaryTask.controller.jspPath.Path;
import ua.nure.levchenko.SummaryTask.exception.AppException;
import ua.nure.levchenko.SummaryTask.exception.ServiceException;
import ua.nure.levchenko.SummaryTask.exception.constants.Messages;
import ua.nure.levchenko.SummaryTask.model.entity.db.User;
import ua.nure.levchenko.SummaryTask.model.entity.enums.Language;
import ua.nure.levchenko.SummaryTask.model.entity.enums.Role;
import ua.nure.levchenko.SummaryTask.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Sign Up command.
 *
 * @author K.Levchenko
 */
public class SignUpCommand implements Command {

    private static final long serialVersionUID = -8506576564315805157L;

    private static final Logger LOG = Logger.getLogger(SignUpCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");
        // setting default language
        ResourceBundle resourceBundle = ResourceBundle.getBundle
                ("resources", new Locale("en", "US"));

        //getting session
        HttpSession session = request.getSession();

        // getting action parameter from the request
        String action = request.getParameter(Parameters.ACTION);
        LOG.trace("Request parameter: action --> " + action);

        String forward = Path.PAGE_SING_UP;

        try {
            if (action.equals(Actions.LOGIN_BUTTON)) {
                forward = Path.PAGE_LOGIN;
            } else if (action.equals(Actions.SIGN_UP)) {
                // getting entered user values
                String login = request.getParameter(Parameters.LOGIN);
                LOG.trace("Request parameter: login --> " + login);
                String password = request.getParameter(Parameters.PASSWORD);
                LOG.trace("Request parameter: password --> " + password);
                String userName = request.getParameter(Parameters.USER_NAME);
                LOG.trace("Request parameter: userName --> " + userName);
                String userEmail = request.getParameter(Parameters.USER_EMAIL);
                LOG.trace("Request parameter: userEmail --> " + userEmail);
                String userPhone = request.getParameter(Parameters.USER_PHONE);
                LOG.trace("Request parameter: userPhone --> " + userPhone);

                // creating new user object
                User user = new User(userName, login, password, Role.USER, userEmail,
                        userPhone, true, Language.ENG);
                // validate user on create
                UserWebValidator userWebValidator = new UserWebValidator();
                boolean isUserValid = userWebValidator.validateUserOnCreate(user, request);
                if (isUserValid) {
                    // creating user in DB
                    UserService userService = new UserService();
                    User dbUser = userService.read(login);
                    if (dbUser == null) {
                        userService.create(user);
                        // setting session attribute "user"
                        session.setAttribute(Attributes.USER, user);
                        // go to profile page
                        forward = Path.PROFILE_PAGE;
                    } else {
                        request.setAttribute(Attributes.ERROR_MESSAGE, "User with such login has already exist");
                    }
                }
            }
        } catch (ServiceException e) {
            LOG.error(Messages.ERR_CANNOT_SIGN_USER_UP, e);
            throw new AppException(resourceBundle.getString("sign_up_command.app_exception"), e);
        }
        LOG.debug("Command finished");
        return forward;
    }
}
