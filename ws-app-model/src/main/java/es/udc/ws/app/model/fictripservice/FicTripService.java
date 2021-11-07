package es.udc.ws.app.model.fictripservice;
import es.udc.ws.app.model.excursion.Excursion;
import es.udc.ws.app.model.fictripservice.exceptions.FechaComienzoMuyCercaException;
import es.udc.ws.app.model.fictripservice.exceptions.NoHayTantasPlazasException;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.util.exceptions.*;

public interface FicTripService {
    Excursion addExcursion(Excursion excursion) throws InputValidationException,
            FechaComienzoMuyCercaException;
    Reserva addReserva(Reserva reserva) throws InputValidationException,
            InstanceNotFoundException, FechaComienzoMuyCercaException,
            NoHayTantasPlazasException;
}
