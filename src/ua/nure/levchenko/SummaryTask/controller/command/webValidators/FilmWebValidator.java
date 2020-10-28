package ua.nure.levchenko.SummaryTask.controller.command.webValidators;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Attributes;
import ua.nure.levchenko.SummaryTask.exception.ServiceException;
import ua.nure.levchenko.SummaryTask.model.entity.db.Dictionary;
import ua.nure.levchenko.SummaryTask.model.entity.db.Film;
import ua.nure.levchenko.SummaryTask.model.services.DictionaryService;
import ua.nure.levchenko.SummaryTask.model.validators.FilmValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

public class FilmWebValidator {
    private static final Logger LOG = Logger.getLogger(FilmWebValidator.class);


    /**
     * Method checking the correctness of
     * new input values for film on update operation
     * <p>
     * This method checks not null values.
     * Skips null values if it is possible.
     * Checks if required fields are not null.
     *
     * @param film
     * @param request
     * @return
     * @throws ServiceException if service classes throw such
     */
    public boolean validateFilmOnUpdate(Film film, HttpServletRequest request, ResourceBundle resourceBundle) throws ServiceException {
        DictionaryService dictionaryService = new DictionaryService();
        try {
            LOG.trace("Validation starts");
            // validates description
            Dictionary dictionaryDescription = film.getDescription();
            boolean isDescriptionValid = validateFilmDescription(dictionaryDescription, request, resourceBundle);
            // validates name
            Dictionary dictionaryName = film.getName();
            boolean isNameValid = validateFilmName(dictionaryName, request, resourceBundle);
//            // validates image path
//            boolean isImagePathValid = validateFilmImage(film.getImage().toString(), request);

            return isNameValid && isDescriptionValid;
        } finally {
            LOG.trace("Validation finished");
        }
    }


    /**
     * Method checking the correctness of
     * new input values for film on create operation
     * <p>
     * This method checks not null values.
     * Skips null values if it is possible.
     * Checks if required fields are not null.
     *
     * @param film
     * @param request
     * @return
     * @throws ServiceException if service classes throw such
     */
    public boolean validateFilmOnCreate(Film film, HttpServletRequest request, ResourceBundle resourceBundle) throws ServiceException {
        DictionaryService dictionaryService = new DictionaryService();
        try {
            LOG.trace("Validation starts");
            // validates description
            Dictionary dictionaryDescription = film.getDescription();
            boolean isDescriptionValid = validateFilmDescription(dictionaryDescription, request, resourceBundle);
            // validates name
            Dictionary dictionaryName = film.getName();
            boolean isNameValid = validateFilmName(dictionaryName, request, resourceBundle);
//            // validates image path
//            boolean isImagePathValid = validateFilmImage(film.getImage().toString(), request);

            return isNameValid && isDescriptionValid;
        } finally {
            LOG.trace("Validation finished");
        }
    }

    /**
     * Checks the correctness and is it
     * null or not of film name values
     * <p>
     * if something goes wrong in request
     * will be writing error message param
     *
     * @param name
     * @param request
     * @return
     */
    private boolean validateFilmName(Dictionary name, HttpServletRequest request, ResourceBundle resourceBundle) {
        if (name != null) {
            String rusName = name.getRus();
            String engName = name.getEng();
            if (rusName != null && !rusName.isEmpty()
                    && engName != null && !engName.isEmpty()) {
                FilmValidator filmValidator = new FilmValidator();
                boolean isValid = filmValidator.validateFilmName(name);
                if (isValid) {
                    return true;
                } else {
                    request.setAttribute(Attributes.ERROR_MESSAGE, resourceBundle.getString("web_film_validator.name_validation_error"));
                    return false;
                }
            }
        }
        request.setAttribute(Attributes.ERROR_MESSAGE, resourceBundle.getString("web_film_validator.empty_name"));
        return false;
    }

    /**
     * Checks the correctness and is it
     * null or not of film description values
     * <p>
     * if something goes wrong in request
     * will be writing error message param
     *
     * @param description
     * @param request
     * @return
     */
    private boolean validateFilmDescription(Dictionary description, HttpServletRequest request, ResourceBundle resourceBundle) {
        System.out.println(description);
        if (description != null) {
            String rusDescription = description.getRus();
            String engDescription = description.getEng();
            if (rusDescription != null && engDescription != null
                    && !rusDescription.isEmpty() && !engDescription.isEmpty()) {
                FilmValidator filmValidator = new FilmValidator();
                boolean isValid = filmValidator.validateFilmDescription(description);
                if (isValid) {
                    return true;
                } else {
                    request.setAttribute(Attributes.ERROR_MESSAGE, resourceBundle.getString("web_film_validator.description_validation_error"));
                    return false;
                }
            } else {
                request.setAttribute(Attributes.ERROR_MESSAGE, resourceBundle.getString("web_film_validator.empty_description"))
                ;
                return false;
            }
        }
        request.setAttribute(Attributes.ERROR_MESSAGE, resourceBundle.getString("web_film_validator.empty_description"));
        return false;
    }


    /**
     * Checks the correctness and is it
     * null or not of film image value
     * <p>
     * if something goes wrong in request
     * will be writing error message param
     *
     * @param imagePath
     * @param request
     * @return
     */
    private boolean validateFilmImage(String imagePath, HttpServletRequest request, ResourceBundle resourceBundle) {
        if (imagePath != null && !imagePath.isEmpty()) {
            return true;
        } else {
            request.setAttribute(Attributes.ERROR_MESSAGE, resourceBundle.getString("web_film_validator.empty_image"));
            return false;
        }
    }
}
