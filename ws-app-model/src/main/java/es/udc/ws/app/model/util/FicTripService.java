package es.udc.ws.app.model.util;

public interface FicTripService {
    Excursion crearExcursion(String ciudad, String desc, int precioXPersona, int maxPlazas);
    Reserva reservarExcursion(Long excursionId, String email, int numPlazas, String targeta);
}
