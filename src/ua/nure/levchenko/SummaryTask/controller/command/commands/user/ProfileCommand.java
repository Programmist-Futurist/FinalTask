package ua.nure.levchenko.SummaryTask.controller.command.commands.user;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Actions;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Parameters;
import ua.nure.levchenko.SummaryTask.controller.jspPath.Path;
import ua.nure.levchenko.SummaryTask.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileCommand implements Command {
    private static final Logger LOG = Logger.getLogger(ProfileCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        // getting action parameter from the request
        String action = request.getParameter(Parameters.ACTION);
        LOG.trace("Request parameter: action --> " + action);

        String forward = Path.PROFILE_PAGE;
        if (Actions.EDIT_PROFILE.equals(action)) {
            forward = Path.PAGE_SETTINGS;
        }
        LOG.debug("Command finishes");
        return forward;
    }
}
