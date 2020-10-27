package ua.nure.levchenko.SummaryTask.model.entity.db;

/**
 * Dictionary entity.
 *
 * @author K.Levchenko
 */
public class Dictionary extends Entity {
    private static final long serialVersionUID = -1757409997615731642L;
    private String eng;
    private String rus;

    public Dictionary(int id, String eng, String rus) {
        super(id);
        this.eng = eng;
        this.rus = rus;
    }


    public Dictionary(String eng, String rus) {
        this.eng = eng;
        this.rus = rus;
    }

    public Dictionary() {
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getRus() {
        return rus;
    }

    public void setRus(String rus) {
        this.rus = rus;
    }


    @Override
    public String toString() {
        return "Dictionary{" +
                "eng='" + eng + '\'' +
                ", rus='" + rus + '\'' +
                '}';
    }
}