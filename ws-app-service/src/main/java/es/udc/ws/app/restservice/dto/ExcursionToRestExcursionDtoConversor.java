package es.udc.ws.app.restservice.dto;

import es.udc.ws.app.model.excursion.Excursion;

import java.time.LocalDateTime;

public class ExcursionToRestExcursionDtoConversor
{
    public static RestExcursionDto toRestExcursionDto(Excursion excursion) {
        return new RestExcursionDto(excursion.getExcursionId(), excursion.getCiudad(),
                excursion.getDescrip(), excursion.getFechaComienzo().toString(),
                excursion.getPrecioXPersona(), excursion.getMaxPlazas(), excursion.getPlazasLibres());
    }

    public static Excursion toExcursion(RestExcursionDto excursionDto)
    {
        return new Excursion(excursionDto.getCiudad(), excursionDto.getDescrip(),
                LocalDateTime.parse(excursionDto.getFechaComienzo()),
                excursionDto.getPrecioXPersona(), excursionDto.getMaxPlazas());
    }
}
