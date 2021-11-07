package es.udc.ws.app.model.reserva;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public abstract class AbstractSqlReservaDao implements SqlReservaDao
{
    protected AbstractSqlReservaDao()
    {}

    @Override
    public Reserva find(Connection connection, Long reservaId)
            throws InstanceNotFoundException
    {
        String queryString = "SELECT email, num_plazas, tarjeta, fecha, excursion_id"
                + " FROM reservas WHERE reserva_id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(queryString))
        {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, reservaId);

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
            {
                throw new InstanceNotFoundException(reservaId,
                        Reserva.class.getName());
            }

            /* Get results. */
            i = 1;
            String email = resultSet.getString(i++);
            int numPlazas = resultSet.getInt(i++);
            String tarjeta = resultSet.getString(i++);
            Timestamp fechaAsTimestamp = resultSet.getTimestamp(i++);
            LocalDateTime fecha = fechaAsTimestamp != null
                    ? fechaAsTimestamp.toLocalDateTime()
                    : null;
            Long excursionId = resultSet.getLong(i++);

            /* Return reserva. */
            Reserva reserva = new Reserva(email, numPlazas, tarjeta, excursionId);
            reserva.setReservaId(reservaId);
            reserva.setFecha(fecha);
            return reserva;

        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Connection connection, Long reservaId)
            throws InstanceNotFoundException
    {
        /* Create "queryString". */
        String queryString = "DELETE FROM reservas WHERE reserva_id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(queryString))
        {
            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, reservaId);

            /* Execute query. */
            int removedRows = preparedStatement.executeUpdate();

            if(removedRows == 0)
            {
                throw new InstanceNotFoundException(reservaId,
                        Reserva.class.getName());
            }

        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
