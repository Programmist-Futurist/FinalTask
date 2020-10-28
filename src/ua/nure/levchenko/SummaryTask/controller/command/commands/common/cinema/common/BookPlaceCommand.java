package ua.nure.levchenko.SummaryTask.controller.command.commands.common.cinema.common;

import org.apache.log4j.Logger;
import ua.nure.levchenko.SummaryTask.controller.command.commands.Command;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Attributes;
import ua.nure.levchenko.SummaryTask.controller.command.consts.Parameters;
import ua.nure.levchenko.SummaryTask.controller.jspPath.Path;
import ua.nure.levchenko.SummaryTask.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


/**
 * Command that changed the color of the
 * chair in the cinema when you press on it
 * and it is showed that current place is booked or not
 *
 * @author K.Levchenko
 */
public class BookPlaceCommand implements Command {
    private static final Logger LOG = Logger.getLogger(BookPlaceCommand.class);


    /**
     * This is the main method of the class,
     * it is doing all the things a described above the class
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

        String forward = Path.PAGE_CINEMA;
        // getting needed parameters from the request
        int placeId = Integer.parseInt(request.getParameter(Parameters.PLACE_ID));
        LOG.trace("Request parameter: placeId --> " + placeId);
        // getting needed parameters from the session
        List<Integer> placesToOrder = (List<Integer>) session.getAttribute(Attributes.PLACES_TO_ORDER);
        LOG.trace("Session attribute: placesToOrder --> " + placesToOrder);
        Map<Integer, Boolean> places = (Map<Integer, Boolean>) session.getAttribute(Attributes.PLACES);
        LOG.trace("Session attribute: places --> " + places);

        if (placeId != 0) {
            // updating order places
            if (placesToOrder.contains(placeId)
                    && places.get(placeId)) {
                boolean b = placesToOrder.remove(new Integer(placeId));
                places.put(placeId, true);
            } else {
                placesToOrder.add(placeId);
                places.put(placeId, false);
            }
            System.out.println(placesToOrder);
            // updating attributes
            session.setAttribute(Attributes.PLACES_TO_ORDER, placesToOrder);
            session.setAttribute(Attributes.PLACES, places);
        }
        LOG.debug("Command ends");
        return forward;
    }
}
