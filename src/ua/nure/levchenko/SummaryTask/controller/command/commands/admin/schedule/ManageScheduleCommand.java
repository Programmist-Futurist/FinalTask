package ua.nure.levchenko.SummaryTask.controller.command.commands.admin.schedule;

import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Manage schedule command.
 *
 * @author K.Levchenko
 */
public class ManageScheduleCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        return null;
    }
}
