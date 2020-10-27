package ua.nure.levchenko.SummaryTask.model.db.constants;

/**
 * Class of constant SQL commands.
 *
 * @author K.Levchenko
 */
public class SQLCommands {

    // dictionary
    public static final String SQL_CREATE_DICTIONARY =
            "INSERT INTO dictionary(eng, rus) VALUES (?,?)";

    public static final String SQL_READ_DICTIONARY_BY_ID =
            "SELECT * FROM dictionary WHERE id=(?)";

    public static final String SQL_UPDATE_DICTIONARY =
            "UPDATE dictionary SET eng=(?), rus=(?) WHERE id=(?)";

    public static final String SQL_DELETE_DICTIONARY_BY_ID =
            "DELETE from dictionary where id=(?)";

    public static final String SQL_GET_DICTIONARY_ORDER_BY_ID =
            "select * from dictionary order by id";


    // film
    public static final String SQL_CREATE_FILM =
            "INSERT INTO films(nameId, descriptionId, image) VALUES (?,?,?)";

    public static final String SQL_READ_FILM_BY_ID =
            "SELECT * FROM films WHERE id=(?)";

    public static final String SQL_UPDATE_FILM =
            "UPDATE films SET nameId=(?), descriptionId=(?), image=(?) WHERE id=(?)";

    public static final String SQL_DELETE_FILM_BY_ID =
            "DELETE from films where id=(?)";

    public static final String SQL_GET_FILMS_ORDER_BY_ID =
            "select * from films order by id";


    // hall
    public static final String SQL_CREATE_HALL =
            "INSERT INTO halls(nameId, placesAmount) VALUES (?,?)";

    public static final String SQL_READ_HALL_BY_ID =
            "SELECT * FROM halls WHERE id=(?)";

    public static final String SQL_UPDATE_HALL =
            "UPDATE halls SET nameId=(?), placesAmount=(?) WHERE id=(?)";

    public static final String SQL_DELETE_HALL_BY_ID =
            "DELETE from halls where id=(?)";

    public static final String SQL_GET_HALLS_ORDER_BY_ID =
            "select * from halls order by id";


    // Reservation
    public static final String SQL_CREATE_RESERVATION =
            "INSERT INTO reservations(scheduleId, userId, placeNumber) VALUES (?,?,?)";

    public static final String SQL_READ_RESERVATION_BY_ID =
            "SELECT * FROM reservations WHERE id=(?)";

    public static final String SQL_UPDATE_RESERVATION =
            "UPDATE reservations SET scheduleId=(?), userId=(?), placeNumber=(?) WHERE id=(?)";

    public static final String SQL_DELETE_RESERVATION_BY_ID =
            "DELETE from reservations where id=(?)";

    public static final String SQL_GET_RESERVATION_ORDER_BY_ID =
            "select * from reservations order by id";

    public static final String SQL_GET_RESERVATION_BY_SCHEDULE =
            "select * from reservations where scheduleId=(?) order by id";

    public static final String SQL_GET_RESERVATION_BY_USER =
            "select * from reservations where userId=(?) order by id";

    // Schedule entity
    public static final String SQL_CREATE_SCHEDULE_ENTITY =
            "INSERT INTO schedule(filmId, hallId, timeStart) VALUES (?,?,?)";

    public static final String SQL_READ_SCHEDULE_ENTITY_BY_ID =
            "SELECT * FROM schedule WHERE id=(?)";

    public static final String SQL_UPDATE_SCHEDULE_ENTITY =
            "UPDATE schedule SET filmId=(?), hallId=(?), timeStart=(?) WHERE id=(?)";

    public static final String SQL_DELETE_SCHEDULE_ENTITY_BY_ID =
            "DELETE from schedule where id=(?)";

    public static final String SQL_GET_SCHEDULE_ORDER_BY_ID =
            "select * from schedule order by id";

    public static final String SQL_GET_FILM_SCHEDULE =
            "select * from schedule where filmId=(?) order by timeStart";


    // user
    public static final String SQL_CREATE_USER =
            "INSERT INTO users(name, login, password, role, " +
                    "language, email, phone, logged) VALUES (?,?,?,?,?,?,?,?)";

    public static final String SQL_UPDATE_USER =
            "UPDATE users SET name=(?), login=(?), password=(?), " +
                    "role=(?), language=(?), email=(?), phone=(?)," +
                    " logged=(?) WHERE id=(?)";

    public static final String SQL_READ_USER_BY_ID =
            "select * from users where id=(?)";

    public static final String SQL_READ_USER_BY_LOGIN =
            "select * from users where login=(?)";

    public static final String SQL_READ_USER_BY_LOGIN_AND_PASSWORD =
            "select * from users where login=(?) and password=(?)";


    public static final String SQL_DELETE_USER_BY_ID =
            "DELETE from users where id=(?)";

    public static final String SQL_GET_USERS_ORDER_BY_ID =
            "select * from users order by id";

}
