package es.udc.ws.app.restservice.servlets;

import es.udc.ws.app.model.fictripservice.FicTripServiceFactory;
import es.udc.ws.app.model.fictripservice.exceptions.FechaComienzoMuyCercaException;
import es.udc.ws.app.model.fictripservice.exceptions.NoHayTantasPlazasException;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.restservice.dto.*;
import es.udc.ws.app.restservice.json.AppExceptionToJsonConversor;
import es.udc.ws.app.restservice.json.JsonToRestReservaDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.RestHttpServletTemplate;
import es.udc.ws.util.servlet.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReservaServlet extends RestHttpServletTemplate
{
    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            InputValidationException, InstanceNotFoundException
    {
        ServletUtils.checkEmptyPath(req);
        RestReservaDto reservaDto = JsonToRestReservaDtoConversor.
                toRestReservaDto(req.getInputStream());
        Reserva reserva = ReservaToRestReservaDtoConversor.toReserva(reservaDto);

        try
        {
            reserva = FicTripServiceFactory.getService().addReserva(reserva);
        }
        catch(FechaComienzoMuyCercaException ex)
        {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_FORBIDDEN,
                    AppExceptionToJsonConversor.toFechaComienzoMuyCercaException(ex),
                    null);
            return;
        }
        catch(NoHayTantasPlazasException ex)
        {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_FORBIDDEN,
                    AppExceptionToJsonConversor.toNoHayTantasPlazasException(ex),
                    null);
            return;
        }

        reservaDto = ReservaToRestReservaDtoConversor.toRestReservaDto(reserva);
        String reservaURL = ServletUtils.normalizePath(req.getRequestURL().toString()) +
                "/" + reserva.getReservaId().toString();
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Location", reservaURL);
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                JsonToRestReservaDtoConversor.toObjectNode(reservaDto), headers);
    }
}
