package es.udc.ws.app.client.service.thrift;

import es.udc.ws.app.client.service.ClientAppService;
import es.udc.ws.app.client.service.dto.ClientExcursionDto;
import es.udc.ws.app.client.service.dto.ClientReservaDto;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.app.client.service.exception.ClientFechaComienzoMuyCercaException;
import es.udc.ws.app.client.service.exception.ClientNoHayTantasPlazasException;
import es.udc.ws.app.thrift.*;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.time.LocalDateTime;

public class ThriftClientAppService implements ClientAppService
{
    private final static String ENDPOINT_ADDRESS_PARAMETER =
            "ThriftClientAppService.endpointAddress";

    private final static String endpointAddress =
            ConfigurationParametersManager.getParameter(ENDPOINT_ADDRESS_PARAMETER);

    @Override
    public Long addExcursion(ClientExcursionDto excursion) throws InputValidationException,
            ClientFechaComienzoMuyCercaException
    {
        ThriftFicTripService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try
        {
            transport.open();

            return client.addExcursion(
                    ClientExcursionDtoToThriftExcursionDtoConversor.toThriftExcursionDto(excursion)
            ).getExcursionId();
        }
        catch(ThriftInputValidationException e)
        {
            throw new InputValidationException(e.getMessage());
        }
        catch(ThriftFechaComienzoMuyCercaException e)
        {
            throw new ClientFechaComienzoMuyCercaException(
                    LocalDateTime.parse(e.getFechaAct()),
                    LocalDateTime.parse(e.getFechaComienzo()), e.getMargen());
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            transport.close();
        }
    }

    @Override
    public Long addReserva(ClientReservaDto reserva) throws InstanceNotFoundException,
            InputValidationException, ClientFechaComienzoMuyCercaException,
            ClientNoHayTantasPlazasException
    {
        ThriftFicTripService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try
        {
            transport.open();

            return client.addReserva(
                    ClientReservaDtoToThriftReservaDtoConversor.toThriftReservaDto(reserva)
            ).getReservaId();
        }
        catch(ThriftInstanceNotFoundException e)
        {
            throw new InstanceNotFoundException(e.getInstanceId(),
                    e.getInstanceType());
        }
        catch(ThriftInputValidationException e)
        {
            throw new InputValidationException(e.getMessage());
        }
        catch(ThriftFechaComienzoMuyCercaException e)
        {
            throw new ClientFechaComienzoMuyCercaException(
                    LocalDateTime.parse(e.getFechaAct()),
                    LocalDateTime.parse(e.getFechaComienzo()),
                    e.getMargen());
        }
        catch(ThriftNoHayTantasPlazasException e)
        {
            throw new ClientNoHayTantasPlazasException(
                    e.getExcursionId(), e.getDisponibles(),
                    e.getSolicitadas());
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            transport.close();
        }
    }

    private ThriftFicTripService.Client getClient()
    {
        try
        {
            TTransport transport = new THttpClient(endpointAddress);
            TProtocol protocol = new TJSONProtocol(transport);

            return new ThriftFicTripService.Client(protocol);
        }
        catch(TTransportException e)
        {
            throw new RuntimeException(e);
        }
    }
}
