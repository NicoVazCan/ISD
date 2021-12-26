package es.udc.ws.app.thriftservice;

import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.thrift.ThriftReservaDto;

public class ReservaToThriftReservaDtoConversor
{
    public static Reserva toReserva(ThriftReservaDto reservaDto)
    {
        return new Reserva(reservaDto.getEmail(),
                reservaDto.getNumPlazas(),
                reservaDto.getTarjeta(),
                reservaDto.getExcursionId());
    }

    public static ThriftReservaDto toThriftReservaDto(Reserva reserva)
    {
        return new ThriftReservaDto(reserva.getReservaId(),
                reserva.getEmail(), reserva.getNumPlazas(),
                reserva.getTarjeta(), reserva.getFecha().toString(),
                reserva.getExcursionId());
    }
}
