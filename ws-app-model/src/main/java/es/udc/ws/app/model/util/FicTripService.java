package es.udc.ws.app.model.util;
import es.udc.ws.util.exceptions.*;
public interface FicTripService {
    Excursion crearExcursion(String ciudad, String desc, int precioXPersona, int maxPlazas) throws InputValidationException;
    Reserva reservarExcursion(Long excursionId, String email, int numPlazas, String targeta)
            throws InputValidationException, InstanceNotFoundException;
}
