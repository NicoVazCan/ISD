package es.udc.ws.app.model.excursion;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public abstract class AbstractSqlExcursionDao implements SqlExcursionDao
{
    protected AbstractSqlExcursionDao()
    {
    }

    @Override
    public Excursion find(Connection connection, Long excursionId)
            throws InstanceNotFoundException
    {
        String queryString = "SELECT ciudad, descrip, fecha_alta,"
                + " fecha_comienzo, precio_x_persona, max_plazas, plazas_libres"
                + " FROM excursiones WHERE excursion_id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(queryString))
        {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, excursionId);

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next())
            {
                throw new InstanceNotFoundException(excursionId,
                        Excursion.class.getName());
            }

            /* Get results. */
            i = 1;
            String ciudad = resultSet.getString(i++);
            String descrip = resultSet.getString(i++);
            Timestamp fechaAltaAsTimestamp = resultSet.getTimestamp(i++);
            LocalDateTime fechaAlta = fechaAltaAsTimestamp != null
                    ? fechaAltaAsTimestamp.toLocalDateTime()
                    : null;
            Timestamp fechaComienzoAsTimestamp = resultSet.getTimestamp(i++);
            LocalDateTime fechaComienzo = fechaComienzoAsTimestamp != null
                    ? fechaComienzoAsTimestamp.toLocalDateTime()
                    : null;
            BigDecimal precioXPersona = resultSet.getBigDecimal(i++);
            int maxPlazas = resultSet.getInt(i++);
            int plazasLibres = resultSet.getInt(i++);

            /* Return excursion. */
            Excursion excursion = new Excursion(ciudad, descrip, fechaComienzo,
                    precioXPersona, maxPlazas);
            excursion.setExcursionId(excursionId);
            excursion.setFechaAlta(fechaAlta);
            excursion.setPlazasLibres(plazasLibres);
            return excursion;

        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Connection connection, Excursion excursion)
            throws InstanceNotFoundException
    {
        /* Create "queryString". */
        String queryString = "UPDATE excursiones"
                + " SET ciudad = ?, descrip = ?,"
                + " fecha_alta = ?, fecha_comienzo = ?, precio_x_persona = ?,"
                + " max_plazas = ?, plazas_libres = ?"
                + " WHERE excursion_id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(queryString))
        {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, excursion.getCiudad());
            preparedStatement.setString(i++, excursion.getDescrip());
            preparedStatement.setTimestamp(i++, excursion.getFechaAlta() != null ?
                    Timestamp.valueOf(excursion.getFechaAlta()) : null);
            preparedStatement.setTimestamp(i++, excursion.getFechaComienzo() != null ?
                    Timestamp.valueOf(excursion.getFechaComienzo()) : null);
            preparedStatement.setBigDecimal(i++, excursion.getPrecioXPersona());
            preparedStatement.setInt(i++, excursion.getMaxPlazas());
            preparedStatement.setInt(i++, excursion.getPlazasLibres());
            preparedStatement.setLong(i++, excursion.getExcursionId());

            /* Execute query. */
            int updatedRows = preparedStatement.executeUpdate();

            if(updatedRows == 0)
            {
                throw new InstanceNotFoundException(excursion.getExcursionId(),
                        Excursion.class.getName());
            }

        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Connection connection, Long excursionId)
            throws InstanceNotFoundException
    {
        /* Create "queryString". */
        String queryString = "DELETE FROM excursiones WHERE excursion_id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(queryString))
        {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, excursionId);

            /* Execute query. */
            int removedRows = preparedStatement.executeUpdate();

            if(removedRows == 0)
            {
                throw new InstanceNotFoundException(excursionId,
                        Excursion.class.getName());
            }

        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
