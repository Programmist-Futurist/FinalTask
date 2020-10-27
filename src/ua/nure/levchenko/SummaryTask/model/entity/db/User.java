package ua.nure.levchenko.SummaryTask.model.entity.db;

import ua.nure.levchenko.SummaryTask.model.entity.enums.Language;
import ua.nure.levchenko.SummaryTask.model.entity.enums.Role;

import java.util.Objects;

/**
 * User entity.
 *
 * @author K.Levchenko
 */
public class User extends Entity implements Cloneable {
    private static final long serialVersionUID = 8429947985516446844L;

    private String name;
    private String login;
    private String password;
    private Role role;
    private String email;
    private String phone;
    private Language language;
    private boolean logged;

    public User(int id, String name, String login, String password, Role role, String email, String phone, Language language, boolean logged) {
        super(id);
        this.name = name;
        this.login = login;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.language = language;
        this.logged = logged;
    }

    public User(String name, String login, String password, Role role, String email, String phone, boolean logged, Language language) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.logged = logged;
        this.language = language;
    }

    public User(String name, String login, String password, Role role, String email, String phone, Language language) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.language = language;
    }

    public User(String name, String login, String password, String email, String phone) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public User(String name, String login, String password, Role role, Language language) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.role = role;
        this.language = language;
    }

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getLogin().equals(user.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin());
    }

    @Override
    public String toString() {
        return "User{" +
                "nameId=" + name +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", logged=" + logged +
                ", language=" + language +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public User clone() {
        return new User(super.getId(), this.name, this.login, this.password,
                this.role, this.email, this.phone, this.language, this.logged);
    }
}
