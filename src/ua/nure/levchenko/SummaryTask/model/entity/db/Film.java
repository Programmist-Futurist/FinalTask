package ua.nure.levchenko.SummaryTask.model.entity.db;

import java.io.File;
import java.util.Objects;

/**
 * Film entity.
 *
 * @author K.Levchenko
 */
public class Film extends Entity {
    private static final long serialVersionUID = 2217006913914395415L;
    // db fields
    private int nameId;
    private int descriptionId;
    private File image;
    // dependent Table entities
    private Dictionary name;
    private Dictionary description;

    public Film(int id, int nameId, int descriptionId, File image, Dictionary name, Dictionary description) {
        super(id);
        this.nameId = nameId;
        this.descriptionId = descriptionId;
        this.image = image;
        this.name = name;
        this.description = description;
    }

    public Film(int nameId, int descriptionId, File image, Dictionary name, Dictionary description) {
        this.nameId = nameId;
        this.descriptionId = descriptionId;
        this.image = image;
        this.name = name;
        this.description = description;
    }

    public Film(int nameId, int descriptionId, File image) {
        this.nameId = nameId;
        this.descriptionId = descriptionId;
        this.image = image;
    }

    public Film() {
    }

    public int getNameId() {
        return nameId;
    }

    public void setNameId(int nameId) {
        this.nameId = nameId;
    }

    public int getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(int descriptionId) {
        this.descriptionId = descriptionId;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return nameId == film.nameId &&
                descriptionId == film.descriptionId &&
                Objects.equals(image, film.image);
    }

    public Dictionary getName() {
        return name;
    }

    public void setName(Dictionary name) {
        this.name = name;
    }

    public Dictionary getDescription() {
        return description;
    }

    public void setDescription(Dictionary description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameId, descriptionId, image);
    }

    @Override
    public String toString() {
        return "Film{" +
                "nameId=" + nameId +
                ", descriptionId=" + descriptionId +
                ", image=" + image +
                ", name=" + name +
                ", description=" + description +
                '}';
    }

    @Override
    public Film clone() {
        return new Film(this.getId(), this.nameId, this.descriptionId, this.image, this.name, this.description);
    }
}
