package ua.nure.levchenko.SummaryTask.controller.command.commands.common.cinema.unauthorized;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Actions;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Attributes;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Parameters;
import ua.nure.levchenko.SummaryTask.controller.jspPath.Path;
import ua.nure.levchenko.SummaryTask.exception.AppException;
import ua.nure.levchenko.SummaryTask.model.entity.db.ScheduleEntity;
import ua.nure.levchenko.SummaryTask.model.services.ReservationService;
import ua.nure.levchenko.SummaryTask.model.services.ScheduleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public class BookPlaceCommand implements Command {
    private static final Logger LOG = Logger.getLogger(BookPlaceCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");

        // getting session
        HttpSession session = request.getSession();

        String action = request.getParameter(Parameters.ACTION);
        LOG.trace("Parameter required: action -->" + action);

        String forward = Path.PAGE_CINEMA;
        if (action.equals(Actions.BOOK_PLACE)) {
            // getting needed parameters from the request
            int placeId = Integer.parseInt(request.getParameter(Parameters.PLACE_ID));
            LOG.trace("Request parameter: placeId --> " + placeId);
            // getting needed parameters from the session
            List<Integer> placesToOrder = (List<Integer>) session.getAttribute(Attributes.PLACES_TO_ORDER);
            LOG.trace("Session attribute: placesToOrder --> " + placesToOrder);
            Map<Integer, Boolean> places = (Map<Integer, Boolean>) session.getAttribute(Attributes.PLACES);
            LOG.trace("Session attribute: places --> " + places);

            // updating order places
            if (placesToOrder.contains(placeId)) {
                placesToOrder.remove(placeId);
                places.put(placeId, true);
            } else {
                placesToOrder.add(placeId);
                places.put(placeId, false);
            }

            // updating attributes
            session.setAttribute(Attributes.PLACES_TO_ORDER, placesToOrder);
            session.setAttribute(Attributes.PLACES, places);
        }
        LOG.debug("Command ends");
        return forward;
    }
}
