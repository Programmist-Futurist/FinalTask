package ua.nure.levchenko.SummaryTask.controller.command.commands;

import ua.nure.levchenko.SummaryTask.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Main interface for the Command pattern implementation.
 *
 * @author K.Levchenko
 */
public interface Command {

    /**
     * Execution method for command.
     *
     * @return Address to go once the command is executed.
     */
    String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException;
}