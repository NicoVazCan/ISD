package es.udc.ws.app.model.util;

import java.sql.Connection;
import es.udc.ws.util.exceptions.*;

public interface SqlReservaDao
{
    Reserva create(Connection connection, Reserva reserva);
    Reserva find(Connection connection, Long reservaId)
            throws InputValidationException;
    void remove(Connection connection, Long reservaId)
            throws InputValidationException;
}
