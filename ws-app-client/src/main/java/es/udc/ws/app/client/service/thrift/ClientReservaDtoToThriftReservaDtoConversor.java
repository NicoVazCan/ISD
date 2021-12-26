package es.udc.ws.app.client.service.thrift;

import es.udc.ws.app.client.service.dto.ClientReservaDto;
import es.udc.ws.app.thrift.ThriftReservaDto;

import java.time.LocalDateTime;

public class ClientReservaDtoToThriftReservaDtoConversor
{
    public static ThriftReservaDto toThriftReservaDto(
            ClientReservaDto clientReservaDto)
    {
        Long reservaId = clientReservaDto.getReservaId();
        LocalDateTime fecha = clientReservaDto.getFecha();

        return new ThriftReservaDto(
                reservaId == null ? -1 : reservaId,
                clientReservaDto.getEmail(),
                clientReservaDto.getNumPlazas(),
                clientReservaDto.getTarjeta(),
                fecha == null? null: fecha.toString(),
                clientReservaDto.getExcursionId());
    }

    private static ClientReservaDto toClientReservaDto(ThriftReservaDto reservaDto)
    {
        return new ClientReservaDto(reservaDto.getEmail(),
                reservaDto.getNumPlazas(),
                reservaDto.getTarjeta(),
                reservaDto.getExcursionId());

    }
}
