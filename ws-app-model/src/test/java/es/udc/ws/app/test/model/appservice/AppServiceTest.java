package es.udc.ws.app.test.model.appservice;

import es.udc.ws.app.model.excursion.Excursion;
import es.udc.ws.app.model.excursion.SqlExcursionDao;
import es.udc.ws.app.model.excursion.SqlExcursionDaoFactory;
import es.udc.ws.app.model.fictripservice.FicTripServiceFactory;
import es.udc.ws.app.model.fictripservice.exceptions.FechaComienzoMuyCercaException;
import es.udc.ws.app.model.fictripservice.exceptions.NoHayTantasPlazasException;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.model.reserva.SqlReservaDao;
import es.udc.ws.app.model.reserva.SqlReservaDaoFactory;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import es.udc.ws.app.model.fictripservice.FicTripService;

import javax.sql.DataSource;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static es.udc.ws.app.model.util.ModelConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class AppServiceTest
{
    private final long NON_EXISTENT_EXCURSION_ID = -1;
    private final long NON_EXISTENT_RESERVA_ID = -1;

    private final String VALID_CREDIT_CARD_NUMBER = "1234567890123456";
    private final String INVALID_CREDIT_CARD_NUMBER = "";

    private static FicTripService ficTripService = null;
    private static SqlExcursionDao excursionDao = null;
    private static SqlReservaDao reservaDao = null;

    @BeforeAll
    public static void init()
    {
        /*
         * Create a simple data source and add it to "DataSourceLocator" (this
         * is needed to test "es.udc.ws.app.model.fictripservice.MovieService"
         */
        DataSource dataSource = new SimpleDataSource();

        /* Add "dataSource" to "DataSourceLocator". */
        DataSourceLocator.addDataSource(APP_DATA_SOURCE, dataSource);

        ficTripService = FicTripServiceFactory.getService();
        excursionDao = SqlExcursionDaoFactory.getDao();
        reservaDao = SqlReservaDaoFactory.getDao();

    }

    private Excursion getValidExcursion(String ciudad)
    {
        return new Excursion(ciudad, "descrip",
                LocalDateTime.now().withNano(0).plusHours(100), BigDecimal.TEN, 8);
    }

    private Excursion getValidExcursion()
    {
        return getValidExcursion("ciudad");
    }

    private Reserva getValidReserva(Long excursionId)
    {
        return new Reserva("test@test.com", 5, VALID_CREDIT_CARD_NUMBER, excursionId);
    }

    private Excursion createExcursion(Excursion excursion)
    {
        Excursion addedExcursion = null;
        try
        {
            addedExcursion = ficTripService.addExcursion(excursion);
        }
        catch(InputValidationException | FechaComienzoMuyCercaException e)
        {
            throw new RuntimeException(e);
        }
        return addedExcursion;
    }

    private Excursion findExcursion(Long excursionId)
    {
        Excursion excursion = null;
        DataSource dataSource = DataSourceLocator.getDataSource(APP_DATA_SOURCE);

        try(Connection connection = dataSource.getConnection())
        {
            try
            {
                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                excursion = excursionDao.find(connection, excursionId);

                /* Commit. */
                connection.commit();
            }
            catch(InstanceNotFoundException e)
            {
                connection.commit();
                throw new RuntimeException(e);
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

        return excursion;
    }

    private Reserva findReserva(Long reservaId)
    {
        Reserva reserva = null;
        DataSource dataSource = DataSourceLocator.getDataSource(APP_DATA_SOURCE);

        try(Connection connection = dataSource.getConnection())
        {
            try
            {
                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                reserva = reservaDao.find(connection, reservaId);

                /* Commit. */
                connection.commit();
            }
            catch(InstanceNotFoundException e)
            {
                connection.commit();
                throw new RuntimeException(e);
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

        return reserva;
    }

    private void removeExcursion(Long excursionId)
    {
        DataSource dataSource = DataSourceLocator.getDataSource(APP_DATA_SOURCE);

        try(Connection connection = dataSource.getConnection())
        {
            try
            {
                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                excursionDao.remove(connection, excursionId);

                /* Commit. */
                connection.commit();
            }
            catch(InstanceNotFoundException e)
            {
                connection.commit();
                throw new RuntimeException(e);
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

    private void removeReserva(Long reservaId)
    {
        DataSource dataSource = DataSourceLocator.getDataSource(APP_DATA_SOURCE);

        try(Connection connection = dataSource.getConnection())
        {
            try
            {
                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                reservaDao.remove(connection, reservaId);

                /* Commit. */
                connection.commit();
            }
            catch(InstanceNotFoundException e)
            {
                connection.commit();
                throw new RuntimeException(e);
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

    @Test
    public void testAddExcursion() throws InputValidationException,
            FechaComienzoMuyCercaException
    {
        Excursion excursion = getValidExcursion();
        Excursion addedExcursion = null;

        try
        {
            LocalDateTime beforeCreationDate = LocalDateTime.now().withNano(0);

            addedExcursion = ficTripService.addExcursion(excursion);

            LocalDateTime afterCreationDate = LocalDateTime.now().withNano(0);

            Excursion foundExcursion = findExcursion(addedExcursion.getExcursionId());

            assertEquals(addedExcursion, foundExcursion);
            assertEquals(addedExcursion.getExcursionId(), foundExcursion.getExcursionId());
            assertEquals(addedExcursion.getCiudad(), foundExcursion.getCiudad());
            assertEquals(addedExcursion.getDescrip(), foundExcursion.getDescrip());
            assertEquals(addedExcursion.getFechaAlta(), foundExcursion.getFechaAlta());
            assertEquals(addedExcursion.getFechaComienzo(), foundExcursion.getFechaComienzo());
            assertEquals(addedExcursion.getMaxPlazas(), foundExcursion.getMaxPlazas());
            assertTrue(addedExcursion.getMaxPlazas() > 0);
            assertEquals(addedExcursion.getPlazasLibres(), foundExcursion.getPlazasLibres());
            assertEquals(addedExcursion.getPlazasLibres(), addedExcursion.getMaxPlazas());
            assertTrue(addedExcursion.getPlazasLibres() >= 0);
            assertEquals(addedExcursion.getPrecioXPersona(), foundExcursion.getPrecioXPersona());
            assertTrue((foundExcursion.getFechaAlta().compareTo(beforeCreationDate) >= 0)
                    && (foundExcursion.getFechaAlta().compareTo(afterCreationDate) <= 0));
            assertTrue(foundExcursion.getFechaComienzo().isAfter(
                    foundExcursion.getFechaAlta().plusHours(EXCURSION_MARGEN)));

        }
        finally
        {
            // Clear Database
            if(addedExcursion != null)
            {
                removeExcursion(addedExcursion.getExcursionId());
            }
        }
    }

    @Test
    public void testAddInvalidExcursion()
    {
        // Check excursion ciudad not null
        assertThrows(InputValidationException.class, () ->
        {
            Excursion excursion = getValidExcursion();
            excursion.setCiudad(null);
            Excursion addedExcursion = ficTripService.addExcursion(excursion);
            removeExcursion(addedExcursion.getExcursionId());
        });

        // Check excursion ciudad not empty
        assertThrows(InputValidationException.class, () ->
        {
            Excursion excursion = getValidExcursion();
            excursion.setCiudad("");
            Excursion addedExcursion = ficTripService.addExcursion(excursion);
            removeExcursion(addedExcursion.getExcursionId());
        });

        // Check excursion descrip not null
        assertThrows(InputValidationException.class, () ->
        {
            Excursion excursion = getValidExcursion();
            excursion.setDescrip(null);
            Excursion addedExcursion = ficTripService.addExcursion(excursion);
            removeExcursion(addedExcursion.getExcursionId());
        });

        // Check excursion descrip not empty
        assertThrows(InputValidationException.class, () ->
        {
            Excursion excursion = getValidExcursion();
            excursion.setDescrip("");
            Excursion addedExcursion = ficTripService.addExcursion(excursion);
            removeExcursion(addedExcursion.getExcursionId());
        });

        // Check excursion fechaComienzo not null
        assertThrows(InputValidationException.class, () ->
        {
            Excursion excursion = getValidExcursion();
            excursion.setFechaComienzo(null);
            Excursion addedExcursion = ficTripService.addExcursion(excursion);
            removeExcursion(addedExcursion.getExcursionId());
        });

        // Check excursion fechaComienzo 72 horas antes que fechaAlta
        assertThrows(FechaComienzoMuyCercaException.class, () ->
        {
            Excursion excursion = getValidExcursion();
            excursion.setFechaComienzo(LocalDateTime.now());
            Excursion addedExcursion = ficTripService.addExcursion(excursion);
            removeExcursion(addedExcursion.getExcursionId());
        });

        // Check excursion precio_x_persona not null
        assertThrows(InputValidationException.class, () ->
        {
            Excursion excursion = getValidExcursion();
            excursion.setPrecioXPersona(null);
            Excursion addedExcursion = ficTripService.addExcursion(excursion);
            removeExcursion(addedExcursion.getExcursionId());
        });

        // Check excursion precio_x_persona not negative
        assertThrows(InputValidationException.class, () ->
        {
            Excursion excursion = getValidExcursion();
            excursion.setPrecioXPersona(BigDecimal.valueOf(-1));
            Excursion addedExcursion = ficTripService.addExcursion(excursion);
            removeExcursion(addedExcursion.getExcursionId());
        });

        // Check excursion maxPlazas not negative
        assertThrows(InputValidationException.class, () ->
        {
            Excursion excursion = getValidExcursion();
            excursion.setMaxPlazas(-1);
            Excursion addedExcursion = ficTripService.addExcursion(excursion);
            removeExcursion(addedExcursion.getExcursionId());
        });
    }

    @Test
    public void testAddReserva() throws InstanceNotFoundException,
            InputValidationException, NoHayTantasPlazasException, FechaComienzoMuyCercaException
    {
        Excursion excursion = createExcursion(getValidExcursion());
        Reserva reserva = null;

        try
        {
            reserva = getValidReserva(excursion.getExcursionId());
            // Buy movie
            LocalDateTime beforeCreationDate = LocalDateTime.now().withNano(0);

            reserva = ficTripService.addReserva(reserva);
            excursion = findExcursion(excursion.getExcursionId());

            LocalDateTime afterCreationDate = LocalDateTime.now().withNano(0);

            // Find sale
            Reserva foundReserva = findReserva(reserva.getReservaId());

            // Check sale
            assertEquals(reserva, foundReserva);
            assertEquals(VALID_CREDIT_CARD_NUMBER, foundReserva.getTarjeta());
            assertEquals(reserva.getReservaId(), foundReserva.getReservaId());
            assertEquals(reserva.getEmail(), foundReserva.getEmail());
            assertEquals(reserva.getNumPlazas(), foundReserva.getNumPlazas());
            assertTrue((excursion.getMaxPlazas() - foundReserva.getNumPlazas()) ==
                    excursion.getPlazasLibres());
            assertTrue(foundReserva.getNumPlazas() >= 1 &&
                    foundReserva.getNumPlazas() <= 5);
            assertTrue((foundReserva.getFecha().compareTo(beforeCreationDate) >= 0)
                    && (foundReserva.getFecha().compareTo(afterCreationDate) <= 0));
            assertTrue(excursion.getFechaComienzo().isAfter(
                    foundReserva.getFecha().plusHours(RESERVA_MARGEN)));
        }
        finally
        {
            // Clear database: remove sale (if created) and movie
            if(reserva != null)
            {
                removeReserva(reserva.getReservaId());
            }
            removeExcursion(excursion.getExcursionId());
        }
    }

    @Test
    public void textAddInvalidReserva()
    {
        Excursion excursion = createExcursion(getValidExcursion());
        try {
            assertThrows(InstanceNotFoundException.class, () ->
            {
                Reserva reserva = getValidReserva(NON_EXISTENT_EXCURSION_ID);
                reserva = ficTripService.addReserva(reserva);
                removeReserva(reserva.getReservaId());
            });

            assertThrows(InputValidationException.class, () -> {
                var reserva = getValidReserva(excursion.getExcursionId());
                reserva.setTarjeta(INVALID_CREDIT_CARD_NUMBER);
                reserva = ficTripService.addReserva(reserva);
                removeReserva(reserva.getReservaId());
            });

            assertThrows(InputValidationException.class, () -> {
                var reserva = getValidReserva(excursion.getExcursionId());
                reserva.setEmail(null);
                reserva = ficTripService.addReserva(reserva);
                removeReserva(reserva.getReservaId());
            });

            assertThrows(InputValidationException.class, () -> {
                var reserva = getValidReserva(excursion.getExcursionId());
                reserva.setEmail("");
                reserva = ficTripService.addReserva(reserva);
                removeReserva(reserva.getReservaId());
            });

            assertThrows(InputValidationException.class, () -> {
                var reserva = getValidReserva(excursion.getExcursionId());
                reserva.setNumPlazas(0);
                reserva = ficTripService.addReserva(reserva);
                removeReserva(reserva.getReservaId());
            });

            assertThrows(InputValidationException.class, () -> {
                var reserva = getValidReserva(excursion.getExcursionId());
                reserva.setNumPlazas(6);
                reserva = ficTripService.addReserva(reserva);
                removeReserva(reserva.getReservaId());
            });

            assertThrows(NoHayTantasPlazasException.class, () -> {
                var reserva0 = getValidReserva(excursion.getExcursionId());
                reserva0 = ficTripService.addReserva(reserva0);
                var reserva1 = getValidReserva(excursion.getExcursionId());
                removeReserva(reserva0.getReservaId());
                reserva1 = ficTripService.addReserva(reserva1);
                removeReserva(reserva1.getReservaId());
            });
        } finally {
            // Clear database
            removeExcursion(excursion.getExcursionId());
        }
    }

    @Test
    public void testReservarWithInvalidFecha()
            throws SQLException
    {
        DataSource dataSource = DataSourceLocator.getDataSource(APP_DATA_SOURCE);
        Excursion excursion = getValidExcursion();
        excursion.setFechaAlta(LocalDateTime.now().minusHours(RESERVA_MARGEN+1));
        excursion.setFechaComienzo(LocalDateTime.now());

        try
        {
            try(Connection connection = dataSource.getConnection())
            {
                try
                {
                    /* Prepare connection. */
                    connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                    connection.setAutoCommit(false);

                    /* Do work. */
                    excursion = excursionDao.create(connection, excursion);

                    /* Commit. */
                    connection.commit();
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

            Excursion finalExcursion = excursion;
            assertThrows(FechaComienzoMuyCercaException.class, () ->
            {
                var reserva = getValidReserva(finalExcursion.getExcursionId());

                reserva = ficTripService.addReserva(reserva);
                removeReserva(reserva.getReservaId());
            });
        }
        finally
        {
                // Clear database
                removeExcursion(excursion.getExcursionId());
        }
    }
}