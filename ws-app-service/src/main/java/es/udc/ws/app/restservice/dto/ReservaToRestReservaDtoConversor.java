package es.udc.ws.app.restservice.dto;

import es.udc.ws.app.model.reserva.Reserva;

public class ReservaToRestReservaDtoConversor
{
    public static RestReservaDto toRestReservaDto(Reserva reserva)
    {
        return new RestReservaDto(reserva.getReservaId(), reserva.getEmail(),
                reserva.getNumPlazas(), '*'*12 + reserva.getTarjeta().substring(12),
                reserva.getFecha().toString(), reserva.getExcursionId());
    }

    public static Reserva toReserva(RestReservaDto reservaDto)
    {
        return new Reserva(reservaDto.getEmail(), reservaDto.getNumPlazas(),
                reservaDto.getTarjeta().startsWith("*")? null: reservaDto.getTarjeta(),
                reservaDto.getExcursionId());
    }
}
