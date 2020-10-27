package ua.nure.levchenko.SummaryTask.controller.command.commands.user;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Actions;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Attributes;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Parameters;
import ua.nure.levchenko.SummaryTask.controller.command.webValidators.UserWebValidator;
import ua.nure.levchenko.SummaryTask.controller.jspPath.Path;
import ua.nure.levchenko.SummaryTask.exception.AppException;
import ua.nure.levchenko.SummaryTask.exception.ServiceException;
import ua.nure.levchenko.SummaryTask.model.entity.db.User;
import ua.nure.levchenko.SummaryTask.model.services.UserService;
import ua.nure.levchenko.SummaryTask.model.utils.language.LanguageDefiner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;

public class EditProfileCommand implements Command {
    private static final Logger LOG = Logger.getLogger(EditProfileCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        //getting session
        HttpSession session = request.getSession();

        // getting action parameter from the request
        String action = request.getParameter(Parameters.ACTION);
        LOG.trace("Request parameter: action --> " + action);

        // getting current user
        User userFromSession = (User) session.getAttribute(Attributes.USER);
        User currentUser = userFromSession.clone();
        // defining language
        LanguageDefiner languageDefiner = new LanguageDefiner();
        ResourceBundle resourceBundle = languageDefiner.getBundle(currentUser);

        String forward = Path.PAGE_SETTINGS;
        try {
            if (Actions.EDIT_PROFILE_CONFIRM.equals(action)) {
                String oldLogin = request.getParameter(Parameters.OLD_LOGIN);
                LOG.trace("Request parameter: oldLogin --> " + oldLogin);
                String newLogin = request.getParameter(Parameters.NEW_LOGIN);
                LOG.trace("Request parameter: newLogin --> " + newLogin);
                String oldPassword = request.getParameter(Parameters.OLD_PASSWORD);
                LOG.trace("Request parameter: oldPassword --> " + oldPassword);
                String newPassword = request.getParameter(Parameters.NEW_PASSWORD);
                LOG.trace("Request parameter: newPassword --> " + newPassword);
                String userName = request.getParameter(Parameters.USER_NAME);
                LOG.trace("Request parameter: userName --> " + userName);
                String userEmail = request.getParameter(Parameters.USER_EMAIL);
                LOG.trace("Request parameter: userEmail --> " + userEmail);
                String userPhone = request.getParameter(Parameters.USER_PHONE);
                LOG.trace("Request parameter: userPhone --> " + userPhone);

                // setting new fields for user
                User newUser = currentUser.clone();
                settingFieldsToNewUserObject(newUser, oldLogin, oldPassword, newLogin, newPassword,
                        userName, userEmail, userPhone);
                // validating changed fields
                UserWebValidator userWebValidator = new UserWebValidator();
                boolean isUserValid = userWebValidator.validateUserOnUpdate(oldLogin,
                        oldPassword, newUser, currentUser, request);
                if (isUserValid) {
                    UserService userService = new UserService();
                    User dbUser = null;
                    if (oldLogin != null && !oldLogin.isEmpty()
                            && newLogin != null && !newLogin.isEmpty()) {
                        // updating user in DB
                        dbUser = userService.read(newUser.getLogin());
                    }
                    // check if somebody has such login as your new login
                    if (dbUser == null) {
                        // updating user in DB
                        userService.update(newUser);
                        // updating session attribute
                        session.setAttribute(Attributes.USER, newUser);
                        // setting request attribute
                        request.setAttribute(Attributes.INFO_MESSAGE, "Information updated successfully");
                    } else {
                        // setting request attribute
                        request.setAttribute(Attributes.ERROR_MESSAGE, "User with such login has already exist");
                    }
                }
            }
        } catch (ServiceException e) {
            LOG.error("cannot edit user info", e);
            throw new AppException(resourceBundle.getString("log_in_command.app_exception"), e);
        }
        LOG.debug("Command finishes");
        return forward;
    }

    /**
     * This method sets new values for user fields
     * from request to new user object.
     * <p>
     * It's like create a current object but with updated values
     * <p>
     * User object needs to be validated after this method
     *
     * @param newUser
     * @param newLogin
     * @param newPassword
     * @param name
     * @param email
     * @param phone
     */
    private void settingFieldsToNewUserObject(User newUser, String oldLogin, String oldPassword, String newLogin, String newPassword,
                                              String name, String email, String phone) {
        if (oldLogin != null && !oldLogin.isEmpty()
                && newLogin != null && !newLogin.isEmpty()) {
            newUser.setLogin(newLogin);
        }
        if (oldPassword != null && !oldPassword.isEmpty()
                && newPassword != null && !newPassword.isEmpty()) {
            newUser.setPassword(newPassword);
        }
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPhone(phone);
    }
}
