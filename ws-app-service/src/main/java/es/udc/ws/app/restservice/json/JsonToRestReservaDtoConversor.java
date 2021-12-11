package es.udc.ws.app.restservice.json;

import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;

import es.udc.ws.app.restservice.dto.RestReservaDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

public class JsonToRestReservaDtoConversor
{
    public static ObjectNode toObjectNode(RestReservaDto reserva)
    {

        ObjectNode reservaObject = JsonNodeFactory.instance.objectNode();

        if(reserva.getReservaId() != null)
        {
            reservaObject.put("reservaId", reserva.getReservaId());
        }
        reservaObject.put("email", reserva.getEmail()).
                put("numPlazas", reserva.getNumPlazas()).
                put("tarjeta", reserva.getTarjeta()).
                put("fecha", reserva.getFecha()).
                put("excursionId", reserva.getExcursionId());

        return reservaObject;
    }

    public static ArrayNode toArrayNode(List<RestReservaDto> reservas)
    {

        ArrayNode reservasNode = JsonNodeFactory.instance.arrayNode();
        for(int i = 0; i < reservas.size(); i++)
        {
            RestReservaDto reservaDto = reservas.get(i);
            ObjectNode reservaObject = toObjectNode(reservaDto);
            reservasNode.add(reservaObject);
        }

        return reservasNode;
    }

    public static RestReservaDto toRestReservaDto(InputStream jsonReserva) throws ParsingException
    {
        try
        {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonReserva);

            if(rootNode.getNodeType() != JsonNodeType.OBJECT)
            {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else
            {
                ObjectNode reservaObject = (ObjectNode) rootNode;

                // reservaId optativo al recivir un post del cliente
                JsonNode reservaIdNode = reservaObject.get("reservaId");
                Long reservaId = (reservaIdNode != null) ? reservaIdNode.longValue() : null;

                String email = reservaObject.get("email").textValue().trim();
                int numPlazas = reservaObject.get("numPlazas").intValue();
                // solo los 4 digitos al recivir un post del cliente
                String tarjeta = reservaObject.get("tarjeta").textValue().trim();
                // fecha optativo al recivir un post del cliente
                JsonNode fechaNode = reservaObject.get("fecha");
                String fecha = (fechaNode != null) ? fechaNode.textValue().trim() : null;

                Long excursionId = reservaObject.get("excursionId").longValue();

                return new RestReservaDto(reservaId, email, numPlazas,
                        tarjeta, fecha, excursionId);
            }
        }
        catch(ParsingException ex)
        {
            throw ex;
        }
        catch(Exception e)
        {
            throw new ParsingException(e);
        }
    }
}