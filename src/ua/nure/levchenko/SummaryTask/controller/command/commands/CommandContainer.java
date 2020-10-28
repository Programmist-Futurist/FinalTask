package ua.nure.levchenko.SummaryTask.controller.command.commands;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.admin.films.CreateFilmCommand;
import ua.nure.levchenko.SummaryTask.controller.command.commands.admin.films.DeleteFilmCommand;
import ua.nure.levchenko.SummaryTask.controller.command.commands.admin.films.EditFilmCommand;
import ua.nure.levchenko.SummaryTask.controller.command.commands.admin.films.RedirectManageFilmsCommand;
import ua.nure.levchenko.SummaryTask.controller.command.commands.admin.schedule.ManageScheduleCommand;
import ua.nure.levchenko.SummaryTask.controller.command.commands.common.authorization.LoginCommand;
import ua.nure.levchenko.SummaryTask.controller.command.commands.common.authorization.LogoutCommand;
import ua.nure.levchenko.SummaryTask.controller.command.commands.common.authorization.SignUpCommand;
import ua.nure.levchenko.SummaryTask.controller.command.commands.common.cinema.CinemaCommand;
import ua.nure.levchenko.SummaryTask.controller.command.commands.common.cinema.authorized.MakeOrderCommand;
import ua.nure.levchenko.SummaryTask.controller.command.commands.common.cinema.common.BookPlaceCommand;
import ua.nure.levchenko.SummaryTask.controller.command.commands.common.cinema.common.LookHallCommand;
import ua.nure.levchenko.SummaryTask.controller.command.commands.common.cinema.common.LookScheduleCommand;
import ua.nure.levchenko.SummaryTask.controller.command.commands.common.cinema.common.sort.SortFilmsCommand;
import ua.nure.levchenko.SummaryTask.controller.command.commands.common.cinema.common.sort.SortScheduleCommand;
import ua.nure.levchenko.SummaryTask.controller.command.commands.common.settings.LanguageSettingsCommand;
import ua.nure.levchenko.SummaryTask.controller.command.commands.common.settings.SetLanguageCommand;
import ua.nure.levchenko.SummaryTask.controller.command.commands.user.EditProfileCommand;
import ua.nure.levchenko.SummaryTask.controller.command.commands.user.ProfileCommand;
import ua.nure.levchenko.SummaryTask.controller.command.commands.user.TicketsManageCommand;

import java.util.Map;
import java.util.TreeMap;

/**
 * Holder for all commands.<br/>
 *
 * @author K.Levchenko
 */
public class CommandContainer {

    private static final Logger LOG = Logger.getLogger(CommandContainer.class);

    private static final Map<String, Command> commands = new TreeMap<>();

    static {
        // common commands
        commands.put("logout", new LogoutCommand());
        commands.put("setLanguage", new SetLanguageCommand());
        commands.put("languageSettings", new LanguageSettingsCommand());
        commands.put("login", new LoginCommand());
        commands.put("signUp", new SignUpCommand());

        // cinema
        commands.put("cinema", new CinemaCommand());
        commands.put("lookSchedule", new LookScheduleCommand());
        commands.put("lookHall", new LookHallCommand());
        commands.put("makeOrder", new MakeOrderCommand());
        commands.put("bookPlace", new BookPlaceCommand());
        commands.put("sortFilms", new SortFilmsCommand());
        commands.put("sortSchedule", new SortScheduleCommand());


        // user
        commands.put("editProfile", new EditProfileCommand());
        commands.put("profile", new ProfileCommand());
        commands.put("ticketsManage", new TicketsManageCommand());


        // admin
        commands.put("createFilm", new CreateFilmCommand());
        commands.put("editFilm", new EditFilmCommand());
        commands.put("deleteFilm", new DeleteFilmCommand());
        commands.put("redirectFilms", new RedirectManageFilmsCommand());
        commands.put("manageSchedule", new ManageScheduleCommand());

        LOG.debug("Command container was successfully initialized");
        LOG.trace("Number of commands --> " + commands.size());
    }

    /**
     * Returns command object with the given name.
     *
     * @param commandName Name of the command.
     * @return Command object.
     */
    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            LOG.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }

        return commands.get(commandName);
    }

}