package es.udc.ws.app.model.excursion;

import java.sql.*;
import java.time.LocalDateTime;

public class Jdbc3CcSqlExcursionDao extends AbstractSqlExcursionDao
{
    @Override
    public Excursion create(Connection connection, Excursion excursion)
    {
        /* Create "queryString". */
        String queryString = "INSERT INTO excursiones"
                + " (ciudad, descrip, fecha_alta, fecha_comienzo,"
                + " precio_x_persona, max_plazas, plazas_libres)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(
                queryString, Statement.RETURN_GENERATED_KEYS))
        {
            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, excursion.getCiudad());
            preparedStatement.setString(i++, excursion.getDescrip());
            LocalDateTime fechaAlta = excursion.getFechaAlta();
            preparedStatement.setTimestamp(i++, fechaAlta == null?
                    null : Timestamp.valueOf(fechaAlta));
            LocalDateTime fechaComienzo = excursion.getFechaComienzo();
            preparedStatement.setTimestamp(i++, fechaComienzo == null?
                    null : Timestamp.valueOf(fechaComienzo));
            preparedStatement.setBigDecimal(i++, excursion.getPrecioXPersona());
            preparedStatement.setInt(i++, excursion.getMaxPlazas());
            preparedStatement.setInt(i++, excursion.getPlazasLibres());

            /* Execute query. */
            preparedStatement.executeUpdate();

            /* Get generated identifier. */
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(!resultSet.next())
            {
                throw new SQLException(
                        "JDBC driver did not return generated key.");
            }
            Long excursionId = resultSet.getLong(1);

            /* Return movie. */
            excursion.setExcursionId(excursionId);
            return excursion;

        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }

    }
}
