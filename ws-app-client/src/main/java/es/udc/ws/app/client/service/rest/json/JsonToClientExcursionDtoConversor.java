package es.udc.ws.app.client.service.rest.json;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.app.client.service.dto.ClientExcursionDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class JsonToClientExcursionDtoConversor
{
    public static ObjectNode toObjectNode(ClientExcursionDto excursion) throws IOException
    {

        ObjectNode excursionObject = JsonNodeFactory.instance.objectNode();

        if(excursion.getExcursionId() != null)
        {
            excursionObject.put("excursionId", excursion.getExcursionId());
        }
        excursionObject.put("ciudad", excursion.getCiudad()).
                put("descrip", excursion.getDescrip()).
                put("fechaComienzo", excursion.getFechaComienzo().toString()).
                put("precioXPersona", excursion.getPrecioXPersona()).
                put("maxPlazas", excursion.getMaxPlazas());

        return excursionObject;
    }

    public static InputStream toInputStream(ClientExcursionDto excursion)
    {
        try
        {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            objectMapper.writer(new DefaultPrettyPrinter()).writeValue(outputStream,
                    JsonToClientExcursionDtoConversor.toObjectNode(excursion));

            return new ByteArrayInputStream(outputStream.toByteArray());
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static ClientExcursionDto toClientExcursionDto(InputStream jsonExcursion) throws ParsingException
    {
        try
        {

            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonExcursion);
            if(rootNode.getNodeType() != JsonNodeType.OBJECT)
            {
                throw new ParsingException("Unrecognized JSON (object expected)");
            }
            else
            {
                return toClientExcursionDto(rootNode);
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

    private static ClientExcursionDto toClientExcursionDto(JsonNode excursionNode) throws ParsingException
    {
        if(excursionNode.getNodeType() != JsonNodeType.OBJECT)
        {
            throw new ParsingException("Unrecognized JSON (object expected)");
        }
        else
        {
            ObjectNode excursionObject = (ObjectNode) excursionNode;

            JsonNode excursionIdNode = excursionObject.get("excursionId");
            Long excursionId = (excursionIdNode != null) ? excursionIdNode.longValue() : null;

            String ciudad = excursionObject.get("ciudad").textValue().trim();
            String descrip = excursionObject.get("descrip").textValue().trim();
            LocalDateTime fechaComienzo = LocalDateTime.parse(
                    excursionObject.get("fechaComienzo").textValue().trim());
            BigDecimal precioXPersona = new BigDecimal(
                    excursionObject.get("precioXPersona").bigIntegerValue());
            int maxPlazas = excursionObject.get("maxPlazas").intValue();
            ClientExcursionDto excursion = new ClientExcursionDto(ciudad,
                    descrip, fechaComienzo, precioXPersona, maxPlazas);
            excursion.setExcursionId(excursionId);
            excursion.setPlazasOcupadas(maxPlazas -
                    excursionObject.get("plazasLibres").intValue());

            return excursion;
        }
    }
}
