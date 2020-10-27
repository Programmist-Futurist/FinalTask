package ua.nure.levchenko.SummaryTask.model.entity.enums;

public enum Language {
    RU, ENG;

    public static int getIntValue(String lang) {
        int langAmount = Language.values().length;
        for (int i = 0; i < langAmount; i++) {
            if (lang.equals(Role.values()[i].getName())) {
                return i + 1;
            }
        }
        return -1;
    }

    public static Language get(int index) {
        switch (index) {
            case 1:
                return Language.RU;
            case 2:
                return Language.ENG;
        }
        return null;
    }

    public int getIntKey() {
        int langAmount = Language.values().length;
        for (int i = 0; i < langAmount; i++) {
            if (this.equals(Language.values()[i])) {
                return i + 1;
            }
        }
        return -1;
    }
}
