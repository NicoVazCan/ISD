package es.udc.ws.app.client.service;

import es.udc.ws.app.client.service.dto.ClientExcursionDto;
import es.udc.ws.app.client.service.dto.ClientReservaDto;
import es.udc.ws.app.client.service.exception.ClientFechaComienzoMuyCercaException;
import es.udc.ws.app.client.service.exception.ClientNoHayTantasPlazasException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface ClientAppService
{
    Long addExcursion(ClientExcursionDto excursion) throws InputValidationException,
            ClientFechaComienzoMuyCercaException;

    Long addReserva(ClientReservaDto reserva)
            throws InstanceNotFoundException, InputValidationException,
            ClientFechaComienzoMuyCercaException, ClientNoHayTantasPlazasException;
}
