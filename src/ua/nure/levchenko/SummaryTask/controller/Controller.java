package ua.nure.levchenko.SummaryTask.controller;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.command.commands.CommandContainer;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Attributes;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Parameters;
import ua.nure.levchenko.SummaryTask.controller.jspPath.Path;
import ua.nure.levchenko.SummaryTask.exception.AppException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Main servlet controller.
 *
 * @author K.Levchenko
 */

@WebServlet(name = "Controller", value = "/controller")
public class Controller extends HttpServlet {

    private static final long serialVersionUID = 2423353715955164816L;

    private static final Logger LOG = Logger.getLogger(Controller.class);

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    /**
     * Main method of this controller.
     */
    private void process(HttpServletRequest request,
                         HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("Controller starts");

        String forward = Path.PAGE_ERROR_PAGE;

        // extract command name from the request
        String commandName = request.getParameter(Parameters.COMMAND);
        LOG.trace("Request parameter: command --> " + commandName);

        // obtain command object by its name
        Command command = CommandContainer.get(commandName);
        LOG.trace("Obtained command --> " + command);

        try {
            forward = command.execute(request, response);
        } catch (AppException ex) {
            request.setAttribute(Attributes.ERROR_MESSAGE, ex.getMessage());
        }

        //getting session
        HttpSession session = request.getSession();

        // execute command and get forward address
        if (session != null) {
            session.setAttribute(Attributes.CURRENT_PAGE, forward);
        }

        LOG.trace("Forward address --> " + forward);

        LOG.debug("Controller finished, now go to forward address --> " + forward);

        // go to forward
        request.getRequestDispatcher(forward).forward(request, response);
    }
}