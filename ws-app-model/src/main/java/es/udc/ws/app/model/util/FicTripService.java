package es.udc.ws.app.model.util;
import es.udc.ws.util.exceptions.*;

public interface FicTripService {
    Excursion addExcursion(Excursion excursion) throws InputValidationException;
    Reserva addReserva(Reserva reserva) throws InputValidationException,
            InstanceNotFoundException;
}
