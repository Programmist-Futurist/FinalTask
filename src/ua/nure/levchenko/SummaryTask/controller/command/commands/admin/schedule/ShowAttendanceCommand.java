package ua.nure.levchenko.SummaryTask.controller.command.commands.admin.schedule;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Attributes;
import ua.nure.levchenko.SummaryTask.controller.jspPath.Path;
import ua.nure.levchenko.SummaryTask.exception.AppException;
import ua.nure.levchenko.SummaryTask.model.entity.db.Reservation;
import ua.nure.levchenko.SummaryTask.model.services.ReservationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Manage schedule command.
 *
 * @author K.Levchenko
 */
public class ShowAttendanceCommand implements Command {
    private static final Logger LOG = Logger.getLogger(ShowAttendanceCommand.class);


    /**
     * Shows to admin amount of people attend cinema hall
     *
     * @param request
     * @param response
     * @return
     * @throws AppException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        //initializing service
        ReservationService reservationService = new ReservationService();
        List<Reservation> reservationsDay = reservationService.getAllDay();
        int dayUsers = reservationsDay.size();
        List<Reservation> reservationsWeek = reservationService.getAllWeek();
        int weekUsers = reservationsWeek.size();
        List<Reservation> reservationsMonth = reservationService.getAllMonth();
        int monthUsers = reservationsMonth.size();

        request.setAttribute(Attributes.USERS_DAY, dayUsers);
        request.setAttribute(Attributes.USERS_WEEK, weekUsers);
        request.setAttribute(Attributes.USERS_MONTH, monthUsers);
        return Path.PAGE_ATTENDANCE;
    }
}
