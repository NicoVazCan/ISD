package es.udc.ws.app.client.service.rest;

import es.udc.ws.app.client.service.ClientAppService;
import es.udc.ws.app.client.service.dto.ClientExcursionDto;
import es.udc.ws.app.client.service.dto.ClientReservaDto;
import es.udc.ws.app.client.service.rest.json.JsonToClientExceptionConversor;
import es.udc.ws.app.client.service.rest.json.JsonToClientExcursionDtoConversor;
import es.udc.ws.app.client.service.rest.json.JsonToClientReservaDtoConversor;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.io.IOException;

public class RestClientAppService implements ClientAppService
{
    private final static String ENDPOINT_ADDRESS_PARAMETER = "RestClientExcursionService.endpointAddress";
    private String endpointAddress;

    @Override
    public Long addExcursion(ClientExcursionDto excursion) throws InputValidationException
    {
        try
        {
            HttpResponse response = Request.Post(getEndpointAddress() + "excursion").
                    bodyStream(JsonToClientExcursionDtoConversor.toInputStream(excursion), ContentType.create("application/json")).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_CREATED, response);

            return JsonToClientExcursionDtoConversor.toClientExcursionDto(response.getEntity().getContent()).getExcursionId();
        }
        catch(InputValidationException e)
        {
            throw e;
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long addReserva(ClientReservaDto reserva)
            throws InstanceNotFoundException, InputValidationException
    {
        try
        {
            HttpResponse response = Request.Post(getEndpointAddress() + "reserva").
                    bodyStream(JsonToClientReservaDtoConversor.toInputStream(reserva), ContentType.create("application/json")).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_CREATED, response);

            return JsonToClientReservaDtoConversor.toClientReservaDto(
                    response.getEntity().getContent()).getReservaId();
        }
        catch(InputValidationException | InstanceNotFoundException e)
        {
            throw e;
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private synchronized String getEndpointAddress()
    {
        if(endpointAddress == null)
        {
            endpointAddress = ConfigurationParametersManager
                    .getParameter(ENDPOINT_ADDRESS_PARAMETER);
        }
        return endpointAddress;
    }

    private void validateStatusCode(int successCode, HttpResponse response) throws Exception
    {
        try
        {
            int statusCode = response.getStatusLine().getStatusCode();

            /* Success? */
            if(statusCode == successCode) return;

            /* Handler error. */
            switch(statusCode)
            {
                case HttpStatus.SC_NOT_FOUND:
                    throw JsonToClientExceptionConversor.fromNotFoundErrorCode(
                            response.getEntity().getContent());

                case HttpStatus.SC_BAD_REQUEST:
                    throw JsonToClientExceptionConversor.fromBadRequestErrorCode(
                            response.getEntity().getContent());

                case HttpStatus.SC_FORBIDDEN:
                    throw JsonToClientExceptionConversor.fromForbiddenErrorCode(
                            response.getEntity().getContent());

                default:
                    throw new RuntimeException("HTTP error; status code = "
                            + statusCode);
            }
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
