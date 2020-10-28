package ua.nure.levchenko.SummaryTask.controller.command.commands.user;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Attributes;
import ua.nure.levchenko.SummaryTask.controller.jspPath.Path;
import ua.nure.levchenko.SummaryTask.exception.AppException;
import ua.nure.levchenko.SummaryTask.model.entity.db.Reservation;
import ua.nure.levchenko.SummaryTask.model.entity.db.User;
import ua.nure.levchenko.SummaryTask.model.services.ReservationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ProfileCommand implements Command {
    private static final Logger LOG = Logger.getLogger(ProfileCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        // getting current session
        HttpSession session = request.getSession();
        // getting current user
        User user = (User) session.getAttribute(Attributes.USER);

        // getting user tickets
        ReservationService reservationService = new ReservationService();
        int userId = user.getId();
        List<Reservation> reservations = reservationService.getAllFullByUser(userId);
        // setting attribute filmsSchedule
        request.setAttribute(Attributes.USER_TICKETS, reservations);

        LOG.debug("Command finishes");
        return Path.PROFILE_PAGE;
    }
}
