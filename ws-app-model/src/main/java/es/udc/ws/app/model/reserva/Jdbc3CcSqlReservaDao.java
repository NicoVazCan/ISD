package es.udc.ws.app.model.reserva;

import java.sql.*;
import java.time.LocalDateTime;

public class Jdbc3CcSqlReservaDao extends AbstractSqlReservaDao
{

    @Override
    public Reserva create(Connection connection, Reserva reserva)
    {
        /* Create "queryString". */
        String queryString = "INSERT INTO reservas"
                + " (email, num_plazas, tarjeta, fecha, excursion_id)"
                + " VALUES (?, ?, ?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(
                queryString, Statement.RETURN_GENERATED_KEYS))
        {
            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, reserva.getEmail());
            preparedStatement.setInt(i++, reserva.getNumPlazas());
            preparedStatement.setString(i++, reserva.getTarjeta());
            LocalDateTime fecha = reserva.getFecha();
            preparedStatement.setTimestamp(i++, fecha == null?
                    null : Timestamp.valueOf(fecha));
            preparedStatement.setLong(i++, reserva.getExcursionId());

            /* Execute query. */
            preparedStatement.executeUpdate();

            /* Get generated identifier. */
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(!resultSet.next())
            {
                throw new SQLException(
                        "JDBC driver did not return generated key.");
            }
            Long reservaId = resultSet.getLong(1);

            /* Return movie. */
            reserva.setReservaId(reservaId);
            return reserva;

        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }

     }
}
