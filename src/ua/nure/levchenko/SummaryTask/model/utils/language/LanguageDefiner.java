package ua.nure.levchenko.SummaryTask.model.utils.language;

import ua.nure.levchenko.SummaryTask.model.entity.db.User;
import ua.nure.levchenko.SummaryTask.model.entity.enums.Language;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageDefiner {

    public ResourceBundle getBundle(User user) {
        Language language = user.getLanguage();

        if (language != null) {
            System.out.println("lang != null");
            if (language.equals(Language.ENG)) {
                System.out.println("lang == ENG1");
                return ResourceBundle.getBundle
                        ("resources", new Locale("en", "US"));
            } else if (language.equals(Language.RU)) {
                System.out.println("lang == ENG2");
                return ResourceBundle.getBundle
                        ("resources", new Locale("ru", "RU"));
            }
        } else {
            System.out.println("lang == ENG2");
            return ResourceBundle.getBundle
                    ("resources", new Locale("en", "US"));
        }
        return null;
    }
}
