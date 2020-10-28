package ua.nure.levchenko.SummaryTask.controller.filter;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Attributes;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Parameters;
import ua.nure.levchenko.SummaryTask.controller.jspPath.Path;
import ua.nure.levchenko.SummaryTask.model.entity.db.User;
import ua.nure.levchenko.SummaryTask.model.entity.enums.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Security filter.
 *
 * @author K.Levchenko
 */

public class CommandAccessFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(CommandAccessFilter.class);

    // path
    private static final ResourceBundle RESOURCE_BUNDLE;

    static {
        RESOURCE_BUNDLE = ResourceBundle.getBundle("resources", new Locale("en", "US"));
    }

    // commands access
    private List<String> outOfControl;
    private List<String> common;

    private List<String> adminCommands;
    private List<String> authorizedCommands;
    private String message;
    private String forward;

    public void destroy() {
        LOG.debug("Filter destruction starts");
        // do nothing
        LOG.debug("Filter destruction finished");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        LOG.debug("Filter starts");

        if (accessAllowed(request)) {
            LOG.debug("Filter finished");
            chain.doFilter(request, response);
        } else {
            request.setAttribute(Attributes.ERROR_MESSAGE, message);
            LOG.error("Set the request attribute: infoMessage --> " + message);

            request.getRequestDispatcher(forward)
                    .forward(request, response);
        }
    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String commandName = request.getParameter(Parameters.COMMAND);
        LOG.trace("Command: " + commandName);
        if (commandName == null || commandName.isEmpty()) {
            message = RESOURCE_BUNDLE.getString("filter.empty_command_exception");
            forward = Path.PAGE_ERROR_PAGE;
            return false;
        }

        if (outOfControl.contains(commandName)) {
            return true;
        }

        HttpSession session = httpRequest.getSession();
        if (session.getAttribute("user") != null) {

            User user = (User) session.getAttribute("user");
            Role role = user.getRole();

            if (role.equals(Role.ADMIN)) {
                if (adminCommands.contains(commandName)) {
                    return true;
                }
            } else if (role.equals(Role.USER)) {
                if (authorizedCommands.contains(commandName)) {
                    return true;
                }
            }

            if (common.contains(commandName)) {
                return true;
            }

            message = RESOURCE_BUNDLE.getString("filter.unknown_command_exception");
            forward = Path.PAGE_ERROR_PAGE;
        } else {
            Locale.getDefault();
            message = RESOURCE_BUNDLE.getString("filter.did_not_log_in");
            forward = Path.PAGE_LOGIN;
        }

        return false;
    }

    public void init(FilterConfig fConfig) {
        LOG.debug("Filter initialization starts");

        // roles
        adminCommands = asList(fConfig.getInitParameter("admin"));
        LOG.trace("Admin commands --> " + adminCommands);
        authorizedCommands = asList(fConfig.getInitParameter("user"));
        LOG.trace("Teacher commands --> " + authorizedCommands);

        outOfControl = asList(fConfig.getInitParameter("out-of-control"));
        LOG.trace("Out of control commands --> " + outOfControl);
        common = asList(fConfig.getInitParameter("common"));
        LOG.trace("Common commands --> " + common);

        LOG.debug("Filter initialization finished");
    }

    /**
     * Extracts parameter values from string.
     *
     * @param str parameter values string.
     * @return list of parameter values.
     */
    private List<String> asList(String str) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }

}