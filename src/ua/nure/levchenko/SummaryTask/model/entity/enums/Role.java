package ua.nure.levchenko.SummaryTask.model.entity.enums;

/**
 * Enum of User roles.
 *
 * @author K.Levchenko
 */
public enum Role {
    // the order should be the same as in the database
    ADMIN, USER;

    private int id;
    private int nameId;

    public static int getIntValue(String role) {
        int roleAmount = Role.values().length;
        for (int i = 0; i < roleAmount; i++) {
            if (role.equals(Role.values()[i].getName())) {
                return i + 1;
            }
        }
        return -1;
    }

    public static Role get(int index) {
        switch (index) {
            case 1:
                return Role.ADMIN;
            case 2:
                return Role.USER;
        }
        return null;
    }

    public String getName() {
        return name().toLowerCase();
    }

    public int getIntKey() {
        int roleAmount = Role.values().length;
        for (int i = 0; i < roleAmount; i++) {
            if (this.equals(Role.values()[i])) {
                return i + 1;
            }
        }
        return -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNameId() {
        return nameId;
    }

    public void setNameId(int nameId) {
        this.nameId = nameId;
    }
}
