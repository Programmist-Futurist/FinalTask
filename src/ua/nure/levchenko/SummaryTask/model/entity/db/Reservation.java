package ua.nure.levchenko.SummaryTask.model.entity.db;

import java.util.Objects;


/**
 * Reservation entity.
 *
 * @author K.Levchenko
 */
public class Reservation extends Entity {
    private static final long serialVersionUID = 6614791353941325391L;

    // db entities
    private int scheduleId;
    private int userId;
    private int placeNumber;
    // dependent Table entities
    private User user;
    private ScheduleEntity scheduleEntity;

    public Reservation(int id, int scheduleId, int userId, int placeNumber) {
        super(id);
        this.scheduleId = scheduleId;
        this.userId = userId;
        this.placeNumber = placeNumber;
    }


    public Reservation(int scheduleId, int userId, int placeNumber) {
        this.scheduleId = scheduleId;
        this.userId = userId;
        this.placeNumber = placeNumber;
    }

    public Reservation() {
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return scheduleId == that.scheduleId &&
                userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId, userId);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "scheduleId=" + scheduleId +
                ", userId=" + userId +
                '}';
    }

    public ScheduleEntity getScheduleEntity() {
        return scheduleEntity;
    }

    public void setScheduleEntity(ScheduleEntity scheduleEntity) {
        this.scheduleEntity = scheduleEntity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }
}
