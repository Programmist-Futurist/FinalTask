package ua.nure.levchenko.SummaryTask.model.utils.language;

import ua.nure.levchenko.SummaryTask.model.entity.db.User;
import ua.nure.levchenko.SummaryTask.model.entity.enums.Language;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageDefiner {

    public ResourceBundle getBundle(User user) {
        if (user != null && user.getLanguage() != null) {
            if (user.getLanguage().equals(Language.ENG)) {
                return ResourceBundle.getBundle
                        ("resources", new Locale("en", "US"));
            } else if (user.getLanguage().equals(Language.RU)) {
                return ResourceBundle.getBundle
                        ("resources", new Locale("ru", "RU"));
            }
        } else {
            return ResourceBundle.getBundle
                    ("resources", new Locale("en", "US"));
        }
        return null;
    }
}
