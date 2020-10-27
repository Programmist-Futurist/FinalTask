package ua.nure.levchenko.SummaryTask.model.validators;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.model.entity.db.User;


/**
 * Validator to check the correctness and availability
 * of User entity input fields.
 *
 * @author K.Levchenko
 */
public class UserValidator implements Validator<User> {
    private static final Logger LOG = Logger.getLogger(UserValidator.class);

    /**
     * Method checking the correctness and availability
     * of User entity input fields.
     * <p>
     * Method is general and checks only null or not null the fields are.
     *
     * @param user
     * @return
     */
    @Override
    public boolean validate(User user) {
        LOG.debug("Validation starts");
        try {
            if (user != null) {
                String name = user.getName();
                String email = user.getEmail();
                String phone = user.getPhone();
                String login = user.getLogin();

                if (name != null && login != null && user.getPassword() != null
                        && user.getLanguage() != null) {
                    boolean res = login.matches("((.+@.+\\..+)|(\\+?((\\s?\\d+)($)?)+$))")
                            && name.matches("[^.!@?#\"$%&:;() *+,/=\\[\\\\\\]^_{|}<>\\u0400-\\u04FF]+");
                    if (email != null && phone != null
                            && !email.isEmpty() && !phone.isEmpty() && res) {
                        return email.matches(".+@.+\\..+")
                                && phone.matches("\\+?((\\s?\\d+)($)?)+$");
                    } else {
                        return res;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } finally {
            LOG.debug("Validation finished");
        }
    }


    /**
     * Method checking the correctness and availability
     * of User.login input field.
     *
     * @param login login of some user
     * @return
     */
    public boolean validateLogin(String login) {
        try {
            LOG.debug("Validation starts");
            return login != null && login.matches("((.+@.+?\\..+$)|(\\+?((\\s?\\d+)($)?)+$))");
        } finally {
            LOG.debug("Validation finished");
        }
    }


    /**
     * Method checking the correctness and availability
     * of User.email input field.
     *
     * @param email email of some user
     * @return
     */
    public boolean validateEmail(String email) {
        try {
            LOG.debug("Validation starts");
            return email != null && email.matches(".+@.+\\..+");
        } finally {
            LOG.debug("Validation finished");
        }
    }


    /**
     * Method checking the correctness and availability
     * of User.phone input field.
     *
     * @param phone phone of some user
     * @return
     */
    public boolean validatePhone(String phone) {
        try {
            LOG.debug("Validation starts");
            return phone != null && phone.matches("\\+?((\\s?\\d+)($)?)+$");
        } finally {
            LOG.debug("Validation finished");
        }
    }


    /**
     * Method checking the correctness and availability
     * of User.name input field.
     *
     * @param name name of some user
     * @return
     */
    public boolean validateName(String name) {
        try {
            LOG.debug("Validation starts");
            return name != null && name.toLowerCase().matches("[^.!@?#\"$%&:;() *+,/=\\[\\\\\\]^_{|}<>\\u0400-\\u04FF]+");
        } finally {
            LOG.debug("Validation finished");
        }
    }


}
