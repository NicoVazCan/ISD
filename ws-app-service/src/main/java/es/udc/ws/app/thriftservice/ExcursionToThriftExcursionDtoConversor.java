package es.udc.ws.app.thriftservice;

import es.udc.ws.app.model.excursion.Excursion;
import es.udc.ws.app.thrift.ThriftExcursionDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExcursionToThriftExcursionDtoConversor
{
    public static Excursion toExcursion(ThriftExcursionDto excursionDto)
    {
        return new Excursion(excursionDto.getCiudad(), excursionDto.getDescrip(),
                excursionDto.getFechaComienzo() == null? null:
                        LocalDateTime.parse(excursionDto.getFechaComienzo()),
                excursionDto.getPrecioXPersona() == null? null:
                        new BigDecimal(excursionDto.getPrecioXPersona()),
                excursionDto.getMaxPlazas());
    }

    public static ThriftExcursionDto toThriftExcursionDto(Excursion excursion)
    {
        return new ThriftExcursionDto(excursion.getExcursionId(),
                excursion.getCiudad(), excursion.getDescrip(),
                excursion.getFechaComienzo().toString(),
                excursion.getPrecioXPersona().toString(),
                excursion.getMaxPlazas(), excursion.getPlazasLibres());
    }
}