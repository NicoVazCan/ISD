package es.udc.ws.app.restservice.servlets;

import es.udc.ws.app.model.excursion.Excursion;
import es.udc.ws.app.model.fictripservice.FicTripServiceFactory;
import es.udc.ws.app.model.fictripservice.exceptions.FechaComienzoMuyCercaException;
import es.udc.ws.app.restservice.json.AppExceptionToJsonConversor;
import es.udc.ws.app.restservice.dto.ExcursionToRestExcursionDtoConversor;
import es.udc.ws.app.restservice.dto.RestExcursionDto;
import es.udc.ws.app.restservice.json.JsonToRestExcursionDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.servlet.RestHttpServletTemplate;
import es.udc.ws.util.servlet.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcursionServlet extends RestHttpServletTemplate
{
    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            InputValidationException
    {
        ServletUtils.checkEmptyPath(req);

        RestExcursionDto excursionDto = JsonToRestExcursionDtoConversor.
                toRestExcursionDto(req.getInputStream());
        Excursion excursion = ExcursionToRestExcursionDtoConversor.toExcursion(excursionDto);

        try
        {
            excursion = FicTripServiceFactory.getService().addExcursion(excursion);
        }
        catch(FechaComienzoMuyCercaException ex)
        {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_FORBIDDEN,
                    AppExceptionToJsonConversor.toFechaComienzoMuyCercaException(ex),
                    null);
            return;
        }

        excursionDto = ExcursionToRestExcursionDtoConversor.toRestExcursionDto(excursion);
        String excursionURL = ServletUtils.normalizePath(req.getRequestURL().toString()) +
                "/" + excursion.getExcursionId();
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Location", excursionURL);
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                JsonToRestExcursionDtoConversor.toObjectNode(excursionDto), headers);
    }
}
