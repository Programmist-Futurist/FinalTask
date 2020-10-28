package ua.nure.levchenko.SummaryTask.controller.command.commands.admin.films;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Actions;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Attributes;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Parameters;
import ua.nure.levchenko.SummaryTask.controller.command.webValidators.FilmWebValidator;
import ua.nure.levchenko.SummaryTask.controller.jspPath.Path;
import ua.nure.levchenko.SummaryTask.exception.AppException;
import ua.nure.levchenko.SummaryTask.exception.ServiceException;
import ua.nure.levchenko.SummaryTask.model.entity.db.Dictionary;
import ua.nure.levchenko.SummaryTask.model.entity.db.Film;
import ua.nure.levchenko.SummaryTask.model.entity.db.User;
import ua.nure.levchenko.SummaryTask.model.services.FilmService;
import ua.nure.levchenko.SummaryTask.model.utils.language.LanguageDefiner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Command to create film entity in db
 * (For admin only)
 *
 * @author K.Levchenko
 */
public class CreateFilmCommand implements Command {
    private static final Logger LOG = Logger.getLogger(DeleteFilmCommand.class);

    /**
     * This method validates all fields before
     * creating film in DB, and also creates it if
     * everything is ok, and returns error message if
     * smth goes wrong
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

        // getting action parameter from the request
        String action = request.getParameter(Parameters.ACTION);
        LOG.trace("Request parameter: action --> " + action);

        String forward = Path.PAGE_MANAGE_FILM;
        FilmService filmService = new FilmService();
        try {
            if (Actions.CREATE_FILM_CONFIRM.equals(action)) {
                String nameRu = request.getParameter(Parameters.FILM_NAME_RU);
                LOG.trace("Request parameter: nameRu --> " + nameRu);
                String nameEng = request.getParameter(Parameters.FILM_NAME_ENG);
                LOG.trace("Request parameter: nameEng --> " + nameEng);
                String descriptionRu = request.getParameter(Parameters.FILM_DESCRIPTION_RU);
                LOG.trace("Request parameter: descriptionRu --> " + descriptionRu);
                String descriptionEng = request.getParameter(Parameters.FILM_DESCRIPTION_ENG);
                LOG.trace("Request parameter: descriptionEng --> " + descriptionEng);
//                Part image = request.getPart(Parameters.FILM_IMAGE);
//                LOG.trace("Request parameter: image --> " + image);

                // creating film
                Film film = settingFieldsForFilmObject(nameRu, nameEng,
                        descriptionRu, descriptionEng, null);
                // validating film
                FilmWebValidator filmWebValidator = new FilmWebValidator();
                boolean isFilmValid = filmWebValidator.validateFilmOnCreate(film, request, resourceBundle);
                if (isFilmValid) {
                    // update film in DB
                    filmService.create(film);
                    // setting request attributes
                    request.setAttribute(Attributes.INFO_MESSAGE,
                            resourceBundle.getString("create_film_command.film_created"));
                    // setting appScope attribute "films"
                    List<Film> films = filmService.getAllFull();
                    // setting films to application scope
                    request.getServletContext().setAttribute(Attributes.FILMS, films);
                }
            }
        } catch (ServiceException | IOException e) {
            LOG.error("Can not create film in DB!");
            throw new AppException(resourceBundle.
                    getString("sign_up_command.user_with_such_login_has_already_exist"), e);
        }
        request.setAttribute(Attributes.FILM_ACTIONS, 1);
        LOG.debug("Command finishes");
        return forward;
    }


    /**
     * This method generates unique string value
     * for naming image for film with unique name
     *
     * @return unique string value
     */
    private String generateString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }


    /**
     * This method downloads image to
     * the resource folder from Part object
     * that could be obtained from the request.
     *
     * @param filePart
     * @return downloaded file (image)
     * @throws IOException
     */
    private File downloadImage(Part filePart) throws IOException {
        String fileName = generateString();
        String filePath = "/home/user/Desktop/FinalTAsk/TestTask/src/aFiles/".concat(fileName).concat(".jpg");
        InputStream is = filePart.getInputStream();

        ReadableByteChannel readableByteChannel = Channels.newChannel(is);
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        fileOutputStream.getChannel()
                .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        return new File(filePath);
    }


    /**
     * This method creates Film object
     * fulfilled with input parameter values.
     * <p>
     * After this method, film object should be validated.
     *
     * @param nameRu
     * @param nameEng
     * @param descriptionRu
     * @param descriptionEng
     * @param image
     * @return fulfilled Film object
     * @throws IOException
     */
    private Film settingFieldsForFilmObject(String nameRu, String nameEng, String descriptionRu,
                                            String descriptionEng, Part image) throws IOException {
        Film film = new Film();
        Dictionary dictionaryName = new Dictionary(nameEng, nameRu);
        Dictionary dictionaryDescription = new Dictionary(descriptionEng, descriptionRu);
        // downloading image
        File photo = null;
        if (image != null) {
            photo = downloadImage(image);
        }
        film.setImage(photo);
        // setting film field values
        film.setName(dictionaryName);
        film.setDescription(dictionaryDescription);
        return film;
    }


}
