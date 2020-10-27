package ua.nure.levchenko.SummaryTask.controller.command.consts;

public class Parameters {
    // login
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String ACTION = "action";
    public static final String CONFIRM = "confirm";

    // sign up
    public static final String USER_NAME = "userName";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_PHONE = "userPhone";

    // edit profile
    public static final String OLD_PASSWORD = "oldPassword";
    public static final String NEW_PASSWORD = "newPassword";
    public static final String OLD_LOGIN = "oldLogin";
    public static final String NEW_LOGIN = "newLogin";

    // edit film
    public static final String FILM_NAME_RU = "filmNameRu";
    public static final String FILM_NAME_ENG = "filmNameEn";
    public static final String FILM_DESCRIPTION_RU = "filmDescriptionRu";
    public static final String FILM_DESCRIPTION_ENG = "filmDescriptionEng";
    public static final String FILM_IMAGE = "image_uploads";
    // create film
//    public static final String NEW_LOGIN = "newLogin";

    // manage films
    public static final String FILM_ID_EDIT = "filmIdEdit";
    public static final String FILM_ID_DELETE = "filmIdDelete";
    public static final String FILM_ID_GET = "filmIdGet";


    // general
    public static final String COMMAND = "command";


    // cinema.jsp
    public static final String SCHEDULE_ID = "scheduleId";
    public static final String PLACE_ID = "placeId";
    public static final String FILM_ID = "filmId";

}
