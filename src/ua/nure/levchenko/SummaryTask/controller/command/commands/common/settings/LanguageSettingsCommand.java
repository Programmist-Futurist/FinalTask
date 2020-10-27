package ua.nure.levchenko.SummaryTask.controller.command.commands.common.settings;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.jspPath.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LanguageSettingsCommand implements Command {
    private static final Logger LOG = Logger.getLogger(LanguageSettingsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("Command starts");
        LOG.debug("Command ends");
        return Path.PAGE_LANGUAGE_SETTINGS;
    }
}
