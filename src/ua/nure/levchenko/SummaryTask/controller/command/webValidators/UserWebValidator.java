package ua.nure.levchenko.SummaryTask.controller.command.webValidators;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Attributes;
import ua.nure.levchenko.SummaryTask.exception.ValidationException;
import ua.nure.levchenko.SummaryTask.model.entity.db.User;
import ua.nure.levchenko.SummaryTask.model.validators.UserValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

public class UserWebValidator {
    private static final Logger LOG = Logger.getLogger(UserWebValidator.class);


    /**
     * Method checking the correctness of
     * new input values for user.
     * <p>
     * This method updated not null values.
     * Skips null values if it is possible.
     * Checks if required fields are not null.
     *
     * @param request     current request
     * @param newUser     new User values that you want to set
     * @param currentUser current User values
     * @param oldLogin
     * @param oldPassword
     * @return
     */
    public boolean validateUserOnUpdate(String oldLogin, String oldPassword, User newUser, User currentUser, HttpServletRequest request, ResourceBundle resourceBundle) {
        try {
            LOG.trace("Validation starts");
            boolean isLoginValid = validateLoginOnUpdate(oldLogin, currentUser, newUser, request, resourceBundle);
            boolean isPasswordValid = validatePasswordOnUpdate(oldPassword, currentUser, newUser, request, resourceBundle);
            boolean isNameValid = validateName(newUser, request, resourceBundle);
            boolean isEmailValid = validateEmail(newUser, request, resourceBundle);
            boolean isPhoneValid = validatePhone(newUser, request, resourceBundle);

            return isLoginValid && isPasswordValid && isNameValid && isEmailValid && isPhoneValid;
        } finally {
            LOG.trace("Validation finished");
        }
    }

    /**
     * Method checking the correctness of
     * new input values for user.
     * <p>
     * This method checks not null values.
     * Skips null values if it is possible.
     * Checks if required fields are not null.
     *
     * @param request current request
     * @param newUser new User values that you want to set
     * @return
     */
    public boolean validateUserOnCreate(User newUser, HttpServletRequest request, ResourceBundle resourceBundle) {
        try {
            LOG.trace("Validation starts");
            boolean isLoginValid = validateLoginOnCreate(newUser, request, resourceBundle);
            boolean isPasswordValid = validatePasswordOnCreate(newUser, request, resourceBundle);
            boolean isNameValid = validateName(newUser, request, resourceBundle);
            boolean isEmailValid = validateEmail(newUser, request, resourceBundle);
            boolean isPhoneValid = validatePhone(newUser, request, resourceBundle);

            return isLoginValid && isPasswordValid && isNameValid && isEmailValid && isPhoneValid;
        } finally {
            LOG.trace("Validation finished");
        }
    }


    /**
     * Method sets user login if input login
     * is not null and not empty
     * <p>
     * if something goes wrong in request
     * will be writing error message param
     *
     * @param user
     * @param request
     * @throws ValidationException if login is null or empty
     */
    private boolean validateLoginOnCreate(User user, HttpServletRequest request, ResourceBundle resourceBundle) {
        String login = user.getLogin();
        if (login != null && !login.isEmpty()) {
            // check if login is valid
            UserValidator userValidator = new UserValidator();
            boolean isValid = userValidator.validateLogin(login);
            if (isValid) {
                return true;
            } else {
                request.setAttribute(Attributes.ERROR_MESSAGE, resourceBundle.getString("web_user_validator.not_valid_login"));
                return false;
            }
        } else {
            request.setAttribute(Attributes.ERROR_MESSAGE, resourceBundle.getString("web_user_validator.login_is_empty"));
            return false;
        }
    }


    /**
     * Method validates user password
     * on create operation.
     * <p>
     * if something goes wrong in request
     * will be writing error message param
     *
     * @param user
     * @param request
     * @return
     */
    private boolean validatePasswordOnCreate(User user, HttpServletRequest request, ResourceBundle resourceBundle) {
        String password = user.getPassword();
        if (password != null && !password.isEmpty()) {
            return true;
        } else {
            request.setAttribute(Attributes.ERROR_MESSAGE,
                    resourceBundle.getString("web_user_validator.password_is_empty"));
            return false;
        }
    }


    /**
     * Method checks user name validation
     * <p>
     * If something goes wrong in request
     * will be writing error message param
     *
     * @param user
     * @param request
     */
    private boolean validateName(User user, HttpServletRequest request, ResourceBundle resourceBundle) {
        String name = user.getName();
        if (name != null && !name.isEmpty()) {
            UserValidator userValidator = new UserValidator();
            boolean isValid = userValidator.validateName(name);
            if (isValid) {
                return true;
            } else {
                request.setAttribute(Attributes.ERROR_MESSAGE,
                        resourceBundle.getString("web_user_validator.name_is_not_valid"));
                return false;
            }
        } else {
            request.setAttribute(Attributes.ERROR_MESSAGE,
                    resourceBundle.getString("web_user_validator.name_is_empty"));
            return false;
        }
    }


    /**
     * Sets User.email if input string "email" is not null
     * and not empty
     * <p>
     * If something goes wrong in request
     * will be writing error message param
     *
     * @param user
     * @param request
     */
    private boolean validateEmail(User user, HttpServletRequest request, ResourceBundle resourceBundle) {
        String email = user.getEmail();
        UserValidator userValidator = new UserValidator();
        boolean isValid = userValidator.validateEmail(email);
        if (email != null && !email.isEmpty()) {
            if (isValid) {
                return true;
            } else {
                request.setAttribute(Attributes.ERROR_MESSAGE,
                        resourceBundle.getString("web_user_validator.email_is_not_valid"));
                return false;
            }
        } else {
            return true;
        }
    }


    /**
     * Sets User.phone if input string "email" is not null
     * and not empty
     * <p>
     * If something goes wrong in request
     * will be writing error message param
     *
     * @param newUser
     * @param request
     */
    private boolean validatePhone(User newUser, HttpServletRequest request, ResourceBundle resourceBundle) {
        String phone = newUser.getPhone();
        UserValidator userValidator = new UserValidator();
        boolean isValid = userValidator.validatePhone(phone);
        if (phone != null && !phone.isEmpty()) {
            if (isValid) {
                return true;
            } else {
                request.setAttribute(Attributes.ERROR_MESSAGE,
                        resourceBundle.getString("web_user_validator.phone_is_not_valid"));
                return false;
            }
        } else {
            return true;
        }
    }


    /**
     * Method updated user login if newLogin is not null
     * <p>
     * If something goes wrong in request
     * will be writing error message param
     *
     * @param oldLogin
     * @param currentUser
     * @param newUser
     * @param request
     */
    private boolean validateLoginOnUpdate(String oldLogin, User currentUser, User newUser, HttpServletRequest request, ResourceBundle resourceBundle) {
        String newLogin = newUser.getLogin();
        String currentLogin = currentUser.getLogin();
        if (oldLogin != null && !oldLogin.isEmpty()
                && currentLogin != null && !currentLogin.isEmpty()
                && newLogin != null && !newLogin.isEmpty()) {
            // check if login is valid
            UserValidator userValidator = new UserValidator();
            boolean isValid = userValidator.validateLogin(newLogin);
            if (currentLogin.equals(oldLogin) && isValid) {
                return true;
            } else {
                request.setAttribute(Attributes.ERROR_MESSAGE,
                        resourceBundle.getString("web_user_validator.old_login_is_not_correct"));
                return false;
            }
        } else {
            return true;
        }
    }


    /**
     * Method updated user password if newPassword is not null
     * <p>
     * If something goes wrong in request
     * will be writing error message param
     *
     * @param oldPass
     * @param newUser
     * @param currentUser
     * @param request
     * @return
     * @throws ValidationException if old password is not the same as
     *                             current password
     */
    private boolean validatePasswordOnUpdate(String oldPass, User currentUser,
                                             User newUser, HttpServletRequest request, ResourceBundle resourceBundle) {
        String newPass = newUser.getPassword();
        String userCurrentPassword = currentUser.getPassword();
        if (oldPass != null && !oldPass.isEmpty()
                && newPass != null && !newPass.isEmpty()
                && userCurrentPassword != null && !userCurrentPassword.isEmpty()) {

            if (userCurrentPassword.equals(oldPass)) {
                return true;
            } else {
                request.setAttribute(Attributes.ERROR_MESSAGE,
                        resourceBundle.getString("web_user_validator.old_password_is_not_correct"));
                return false;
            }
        } else {
            return true;
        }
    }

}
