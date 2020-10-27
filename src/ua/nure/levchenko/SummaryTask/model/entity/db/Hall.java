package ua.nure.levchenko.SummaryTask.model.entity.db;

import java.util.Objects;


/**
 * Hall entity.
 *
 * @author K.Levchenko
 */
public class Hall extends Entity {
    private static final long serialVersionUID = 2661732816340335158L;

    // db fields
    private int nameId;
    private int placesAmount;
    // dependent Table entities
    private Dictionary name;

    public Hall(int nameId, int placesAmount, Dictionary name) {
        this.nameId = nameId;
        this.placesAmount = placesAmount;
        this.name = name;
    }

    public Hall(int nameId, int placesAmount) {
        this.nameId = nameId;
        this.placesAmount = placesAmount;
    }

    public Hall() {
    }

    public int getNameId() {
        return nameId;
    }

    public void setNameId(int nameId) {
        this.nameId = nameId;
    }

    public int getPlacesAmount() {
        return placesAmount;
    }

    public void setPlacesAmount(int placesAmount) {
        this.placesAmount = placesAmount;
    }

    public Dictionary getName() {
        return name;
    }

    public void setName(Dictionary name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hall hall = (Hall) o;
        return nameId == hall.nameId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameId);
    }

    @Override
    public String toString() {
        return "Hall{" +
                "nameId=" + nameId +
                ", placesAmount=" + placesAmount +
                ", name=" + name.toString() +
                '}';
    }
}
