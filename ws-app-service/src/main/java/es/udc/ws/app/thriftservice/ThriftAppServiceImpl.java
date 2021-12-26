package es.udc.ws.app.thriftservice;

import es.udc.ws.app.model.excursion.Excursion;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.model.fictripservice.FicTripServiceFactory;
import es.udc.ws.app.model.fictripservice.exceptions.FechaComienzoMuyCercaException;
import es.udc.ws.app.model.fictripservice.exceptions.NoHayTantasPlazasException;
import es.udc.ws.app.thrift.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class ThriftAppServiceImpl implements ThriftFicTripService.Iface
{
    @Override
    public ThriftExcursionDto addExcursion(ThriftExcursionDto excursionDto) throws ThriftInputValidationException,
            ThriftFechaComienzoMuyCercaException
    {

        Excursion excursion = ExcursionToThriftExcursionDtoConversor.toExcursion(excursionDto);

        try
        {
            Excursion addedExcursion = FicTripServiceFactory.getService().addExcursion(excursion);
            return ExcursionToThriftExcursionDtoConversor.toThriftExcursionDto(addedExcursion);
        }
        catch(InputValidationException e)
        {
            throw new ThriftInputValidationException(e.getMessage());
        }
        catch(FechaComienzoMuyCercaException e)
        {
            throw new ThriftFechaComienzoMuyCercaException(
                    e.getFechaAct().toString(),
                    e.getFechaComienzo().toString(), e.getMargen());
        }
    }

    @Override
    public ThriftReservaDto addReserva(ThriftReservaDto reservaDto) throws ThriftInputValidationException,
            ThriftFechaComienzoMuyCercaException, ThriftInstanceNotFoundException, ThriftNoHayTantasPlazasException
    {

        Reserva reserva = ReservaToThriftReservaDtoConversor.toReserva(reservaDto);

        try
        {
            Reserva addedReserva = FicTripServiceFactory.getService().addReserva(reserva);
            return ReservaToThriftReservaDtoConversor.toThriftReservaDto(addedReserva);
        }
        catch(InputValidationException e)
        {
            throw new ThriftInputValidationException(e.getMessage());
        }
        catch(FechaComienzoMuyCercaException e)
        {
            throw new ThriftFechaComienzoMuyCercaException(
                    e.getFechaAct().toString(),
                    e.getFechaComienzo().toString(), e.getMargen());
        }
        catch(InstanceNotFoundException e)
        {
            throw new ThriftInstanceNotFoundException(e.getInstanceId().toString(),
                    e.getInstanceType());
        }
        catch(NoHayTantasPlazasException e)
        {
            throw new ThriftNoHayTantasPlazasException(e.getExcursionId(),
                    e.getDisponibles(), e.getSolicitadas());
        }
    }
}