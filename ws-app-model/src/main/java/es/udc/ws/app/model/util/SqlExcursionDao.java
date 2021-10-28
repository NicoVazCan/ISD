package es.udc.ws.app.model.util;

import es.udc.ws.util.exceptions.*;

import java.sql.Connection;

public interface SqlExcursionDao
{
    Excursion create(Connection connection, Excursion excursion);
    Excursion find(Connection connection, Long excursionId)
            throws InstanceNotFoundException;
    void update(Connection connection, Excursion excursion)
            throws InstanceNotFoundException;
    void remove(Connection connection, Long excursion)
            throws InstanceNotFoundException;
}
