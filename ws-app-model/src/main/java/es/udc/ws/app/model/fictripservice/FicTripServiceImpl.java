package es.udc.ws.app.model.fictripservice;

import es.udc.ws.app.model.excursion.Excursion;
import es.udc.ws.app.model.excursion.SqlExcursionDao;
import es.udc.ws.app.model.excursion.SqlExcursionDaoFactory;
import es.udc.ws.app.model.fictripservice.exceptions.FechaComienzoMuyCercaException;
import es.udc.ws.app.model.fictripservice.exceptions.NoHayTantasPlazasException;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.model.reserva.SqlReservaDao;
import es.udc.ws.app.model.reserva.SqlReservaDaoFactory;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.validation.PropertyValidator;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static es.udc.ws.app.model.util.ModelConstants.*;

public class FicTripServiceImpl implements FicTripService
{
    private final DataSource dataSource;
    private final SqlExcursionDao excursionDao;
    private final SqlReservaDao reservaDao;

    public FicTripServiceImpl()
    {
        dataSource = DataSourceLocator.getDataSource(APP_DATA_SOURCE);
        excursionDao = SqlExcursionDaoFactory.getDao();
        reservaDao = SqlReservaDaoFactory.getDao();
    }

    private void validateExcursion(Excursion excursion)
            throws InputValidationException, FechaComienzoMuyCercaException
    {
        PropertyValidator.validateMandatoryString("ciudad", excursion.getCiudad());
        PropertyValidator.validateMandatoryString("descrip", excursion.getDescrip());
        if(excursion.getFechaComienzo() == null)
        {
            throw new InputValidationException("La excursion tiene que tener " +
                    "una fecha de comienzo no nula.");
        }
        if(excursion.getFechaComienzo().isBefore(excursion.getFechaAlta().plusHours(EXCURSION_MARGEN)))
        {
            throw new FechaComienzoMuyCercaException(excursion.getFechaAlta(),
                    excursion.getFechaComienzo(), EXCURSION_MARGEN);
        }
        if(excursion.getPrecioXPersona() == null || excursion.getPrecioXPersona().signum() == -1)
        {
            throw new InputValidationException("La excursion tiene que tener " +
                    "un precio por persona no nulo y positiva.");
        }
        if(excursion.getMaxPlazas() <= 0)
        {
            throw new InputValidationException("La excursion tiene que tener " +
                    "plazas disponibles inicialmente.");
        }
    }

    private void validateReserva(Reserva reserva) throws InputValidationException
    {
        PropertyValidator.validateMandatoryString("email", reserva.getEmail());
        if(reserva.getNumPlazas() < 1 || reserva.getNumPlazas() > 5)
        {
            throw new InputValidationException("La reserva solicita un número " +
                    "de plazas inferior a 0 o superior a 5 .");
        }
        PropertyValidator.validateCreditCard(reserva.getTarjeta());
    }

    @Override
    public Excursion addExcursion(Excursion excursion) throws InputValidationException,
            FechaComienzoMuyCercaException
    {
        excursion.setFechaAlta(LocalDateTime.now().withNano(0));
        excursion.setPlazasLibres(excursion.getMaxPlazas());
        validateExcursion(excursion);


        try(Connection connection = dataSource.getConnection())
        {
            try
            {
                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                Excursion createdExcursion = excursionDao.create(connection, excursion);

                /* Commit. */
                connection.commit();

                return createdExcursion;

            }
            catch(SQLException e)
            {
                connection.rollback();
                throw new RuntimeException(e);
            }
            catch(RuntimeException | Error e)
            {
                connection.rollback();
                throw e;
            }

        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Reserva addReserva(Reserva reserva) throws InputValidationException,
            InstanceNotFoundException, FechaComienzoMuyCercaException,
            NoHayTantasPlazasException
    {
        LocalDateTime fecha = LocalDateTime.now().withNano(0);
        validateReserva(reserva);

        try(Connection connection = dataSource.getConnection())
        {
            try
            {
                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                Excursion excursion = excursionDao.find(connection, reserva.getExcursionId());
                if(excursion.getFechaComienzo().isBefore(fecha.plusHours(RESERVA_MARGEN)))
                {
                    throw new FechaComienzoMuyCercaException(fecha,
                            excursion.getFechaComienzo(), EXCURSION_MARGEN);
                }
                if(excursion.getPlazasLibres() < reserva.getNumPlazas())
                {
                    throw new NoHayTantasPlazasException(excursion.getExcursionId(),
                            excursion.getPlazasLibres(), reserva.getNumPlazas());
                }
                reserva.setFecha(fecha);
                reserva = reservaDao.create(connection, reserva);
                excursion.setPlazasLibres(excursion.getPlazasLibres()-reserva.getNumPlazas());
                excursionDao.update(connection, excursion);

                /* Commit. */
                connection.commit();

                return reserva;

            }
            catch(InstanceNotFoundException |
                    FechaComienzoMuyCercaException |
                    NoHayTantasPlazasException e)
            {
                connection.commit();
                throw e;
            }
            catch(SQLException e)
            {
                connection.rollback();
                throw new RuntimeException(e);
            }
            catch(RuntimeException | Error e)
            {
                connection.rollback();
                throw e;
            }

        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
