package ua.nure.levchenko.SummaryTask.model.db.constants;


/**
 * Field of entities' names in DB.
 *
 * @author K.Levchenko
 */
public class Fields {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String NAME_ID = "name_id";

    // Dictionary
    public static final String DICTIONARY_ENG = "eng";
    public static final String DICTIONARY_RUS = "rus";

    // User
    public static final String USER_NAME = "name";
    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";
    public static final String USER_ROLE = "role";
    public static final String USER_LOGGED = "logged";
    public static final String USER_EMAIL = "email";
    public static final String USER_PHONE = "phone";
    public static final String USER_LANGUAGE = "language";

    // Film
    public static final String FILM_NAME_ID = "nameId";
    public static final String FILM_DESCRIPTION_ID = "descriptionId";
    public static final String FILM_IMAGE = "image";


    // Hall
    public static final String HALL_NAME_ID = "nameId";
    public static final String HALL_PLACES_AMOUNT = "placesAmount";

    // Reservation
    public static final String RESERVATION_SCHEDULE_ID = "scheduleId";
    public static final String RESERVATION_USER_ID = "userId";
    public static final String RESERVATION_PLACE_NUMBER = "placeNumber";

    // ScheduleEntity
    public static final String SCHEDULE_FILM_ID = "filmId";
    public static final String SCHEDULE_HALL_ID = "hallId";
    public static final String SCHEDULE_TIME_START = "timeStart";

}
