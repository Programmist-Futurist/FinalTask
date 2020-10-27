package ua.nure.levchenko.SummaryTask.controller.command.commands.common.cinema.unauthorized;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SortFilmsCommand implements Command {
    private static final Logger LOG = Logger.getLogger(SortFilmsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        return null;
    }
}
