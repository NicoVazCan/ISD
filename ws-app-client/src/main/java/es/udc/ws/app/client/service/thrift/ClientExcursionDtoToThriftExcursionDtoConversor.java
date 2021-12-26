package es.udc.ws.app.client.service.thrift;

import es.udc.ws.app.client.service.dto.ClientExcursionDto;
import es.udc.ws.app.thrift.ThriftExcursionDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ClientExcursionDtoToThriftExcursionDtoConversor
{
    public static ThriftExcursionDto toThriftExcursionDto(
            ClientExcursionDto clientExcursionDto)
    {
        Long excursionId = clientExcursionDto.getExcursionId();

        return new ThriftExcursionDto(
                excursionId == null ? -1 : excursionId,
                clientExcursionDto.getCiudad(),
                clientExcursionDto.getDescrip(),
                clientExcursionDto.getFechaComienzo().toString(),
                clientExcursionDto.getPrecioXPersona().toString(),
                clientExcursionDto.getMaxPlazas(),
                clientExcursionDto.getMaxPlazas() - clientExcursionDto.getPlazasOcupadas());
    }

    private static ClientExcursionDto toClientExcursionDto(ThriftExcursionDto excursionDto)
    {
        return new ClientExcursionDto(
                excursionDto.getCiudad(), excursionDto.getDescrip(),
                LocalDateTime.parse(excursionDto.getFechaComienzo()),
                new BigDecimal(excursionDto.getPrecioXPersona()),
                excursionDto.getMaxPlazas());

    }
}
