package ua.nure.levchenko.SummaryTask.controller.command.commands.user;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Attributes;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Parameters;
import ua.nure.levchenko.SummaryTask.controller.jspPath.Path;
import ua.nure.levchenko.SummaryTask.exception.AppException;
import ua.nure.levchenko.SummaryTask.model.entity.db.Reservation;
import ua.nure.levchenko.SummaryTask.model.entity.db.User;
import ua.nure.levchenko.SummaryTask.model.services.ReservationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class TicketsManageCommand implements Command {
    private static final Logger LOG = Logger.getLogger(TicketsManageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        // getting current session
        HttpSession session = request.getSession();
        // getting current user
        User user = (User) session.getAttribute(Attributes.USER);

        // getting filmId parameter from the request
        int reservationId = Integer.parseInt(request.getParameter(Parameters.RESERVATION_ID));
        LOG.trace("Request parameter: reservationId --> " + reservationId);

        String forward = Path.PROFILE_PAGE;
        ReservationService reservationService = new ReservationService();

        if (reservationId != 0) {
            int userId = user.getId();
            // deleting ticket
            reservationService.delete(reservationId);
            // getting other tickets
            List<Reservation> reservations = reservationService.getAllFullByUser(userId);
            // setting attribute filmsSchedule
            request.setAttribute(Attributes.USER_TICKETS, reservations);
        }
        LOG.debug("Command ends");
        return forward;
    }
}
