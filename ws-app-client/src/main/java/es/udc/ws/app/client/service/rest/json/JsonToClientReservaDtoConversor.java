package es.udc.ws.app.client.service.rest.json;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.app.client.service.dto.ClientReservaDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

public class JsonToClientReservaDtoConversor
{
    public static ObjectNode toObjectNode(ClientReservaDto reserva) throws IOException
    {

        ObjectNode reservaObject = JsonNodeFactory.instance.objectNode();

        if (reserva.getReservaId() != null) {
            reservaObject.put("reservaId", reserva.getReservaId());
        }
        reservaObject.put("email", reserva.getEmail()).
                put("numPlazas", reserva.getNumPlazas()).
                put("tarjeta", reserva.getTarjeta()).
                put("excursionId", reserva.getExcursionId());

        return reservaObject;
    }

    public static InputStream toInputStream(ClientReservaDto reserva)
    {
        try
        {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            objectMapper.writer(new DefaultPrettyPrinter()).writeValue(outputStream,
                    JsonToClientReservaDtoConversor.toObjectNode(reserva));

            return new ByteArrayInputStream(outputStream.toByteArray());
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static ClientReservaDto toClientReservaDto(InputStream jsonReserva) throws ParsingException
    {
        try {

            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonReserva);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                return toClientReservaDto(rootNode);
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static ClientReservaDto toClientReservaDto(JsonNode reservaNode) throws ParsingException {
        if (reservaNode.getNodeType() != JsonNodeType.OBJECT) {
            throw new ParsingException("Unrecognized JSON (object expected)");
        } else {
            ObjectNode reservaObject = (ObjectNode) reservaNode;

            JsonNode reservaIdNode = reservaObject.get("reservaId");
            Long reservaId = (reservaIdNode != null) ? reservaIdNode.longValue() : null;

            String email = reservaObject.get("email").textValue().trim();
            int numPlazas = reservaObject.get("numPlazas").intValue();
            String tarjeta = reservaObject.get("tarjeta").textValue().trim();
            Long excursionId = reservaObject.get("excursionId").longValue();
            ClientReservaDto reserva = new ClientReservaDto(email,
                    numPlazas, tarjeta, excursionId);
            reserva.setReservaId(reservaId);
            reserva.setFecha(LocalDateTime.parse(
                    reservaObject.get("fecha").textValue().trim()));

            return reserva;
        }
    }
}
