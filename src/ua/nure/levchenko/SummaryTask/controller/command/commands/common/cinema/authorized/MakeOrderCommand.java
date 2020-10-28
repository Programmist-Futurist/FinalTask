package ua.nure.levchenko.SummaryTask.controller.command.commands.common.cinema.authorized;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.command.commands.common.cinema.CinemaCommand;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Actions;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Attributes;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Parameters;
import ua.nure.levchenko.SummaryTask.exception.AppException;
import ua.nure.levchenko.SummaryTask.model.entity.db.Reservation;
import ua.nure.levchenko.SummaryTask.model.entity.db.ScheduleEntity;
import ua.nure.levchenko.SummaryTask.model.entity.db.User;
import ua.nure.levchenko.SummaryTask.model.services.ReservationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Command for booking places for some user
 * (available only for logged users)
 *
 * @author K.Levchenko
 */
public class MakeOrderCommand implements Command {
    private static final Logger LOG = Logger.getLogger(MakeOrderCommand.class);


    /**
     * Command creates new Reservation entities in DB,
     * acording to the user choices
     *
     * @param request
     * @param response
     * @return
     * @throws AppException if some unexpected error occurred
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        // getting session
        HttpSession session = request.getSession();

        String action = request.getParameter(Parameters.ACTION);
        LOG.trace("Parameter required: action -->" + action);

        ReservationService reservationService = new ReservationService();
        if (action.equals(Actions.MAKE_ORDER)) {
            // getting needed parameters from the session
            List<Integer> placesToOrder = (List<Integer>) session.getAttribute(Attributes.PLACES_TO_ORDER);
            LOG.trace("Session attribute: placesToOrder --> " + placesToOrder);
            ScheduleEntity currentScheduleEntity = (ScheduleEntity) session.getAttribute(Attributes.CURRENT_SCHEDULE_ENTITY);
            LOG.trace("Session attribute: currentScheduleEntity --> " + currentScheduleEntity);
            User user = (User) session.getAttribute(Attributes.USER);
            LOG.trace("Session attribute: user --> " + user);

            // crating reservation in DB
            for (Integer placeNum : placesToOrder) {
                Reservation reservation = new Reservation(currentScheduleEntity.getId(),
                        user.getId(), placeNum);
                reservationService.create(reservation);
            }

            // setting attribute currentScheduleEntity
            session.setAttribute(Attributes.CURRENT_SCHEDULE_ENTITY, null);
            // setting attribute currentHallPlaces
            session.setAttribute(Attributes.PLACES, null);
            // setting attribute placesToOrder
            session.setAttribute(Attributes.PLACES_TO_ORDER, null);
        }
        CinemaCommand cinemaCommand = new CinemaCommand();
        return cinemaCommand.execute(request, response);
    }
}
