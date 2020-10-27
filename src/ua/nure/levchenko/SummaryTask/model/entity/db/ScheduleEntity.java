package ua.nure.levchenko.SummaryTask.model.entity.db;

import java.util.Date;
import java.util.Objects;


/**
 * Schedule entity.
 *
 * @author K.Levchenko
 */
public class ScheduleEntity extends Entity {
    private static final long serialVersionUID = -3586660638824607114L;
    // db entities
    private int filmId;
    private int hallId;
    private Date timeStart;
    // dependent Table entities
    private Film film;
    private Hall hall;

    public ScheduleEntity(int filmId, int hallId, Date timeStart) {
        this.filmId = filmId;
        this.hallId = hallId;
        this.timeStart = timeStart;
    }

    public ScheduleEntity() {
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleEntity that = (ScheduleEntity) o;
        return filmId == that.filmId &&
                hallId == that.hallId &&
                Objects.equals(timeStart, that.timeStart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId, hallId, timeStart);
    }

    @Override
    public String toString() {
        return "ScheduleEntity{" +
                "filmId=" + filmId +
                ", hallId=" + hallId +
                ", timeStart=" + timeStart +
                ", film=" + film +
                ", hall=" + hall +
                '}';
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }
}
