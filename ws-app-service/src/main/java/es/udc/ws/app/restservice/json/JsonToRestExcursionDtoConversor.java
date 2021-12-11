package es.udc.ws.app.restservice.json;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;

import es.udc.ws.app.restservice.dto.RestExcursionDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

public class JsonToRestExcursionDtoConversor
{
    public static ObjectNode toObjectNode(RestExcursionDto excursion)
    {

        ObjectNode excursionObject = JsonNodeFactory.instance.objectNode();

        if(excursion.getExcursionId() != null)
        {
            excursionObject.put("excursionId", excursion.getExcursionId());
        }
        excursionObject.put("ciudad", excursion.getCiudad()).
                put("descrip", excursion.getDescrip()).
                put("fechaComienzo", excursion.getFechaComienzo()).
                put("precioXPersona", excursion.getPrecioXPersona()).
                put("maxPlazas", excursion.getMaxPlazas()).
                put("plazasLibres", excursion.getPlazasLibres());

        return excursionObject;
    }

    public static RestExcursionDto toRestExcursionDto(InputStream jsonExcursion) throws ParsingException
    {
        try
        {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonExcursion);

            if(rootNode.getNodeType() != JsonNodeType.OBJECT)
            {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else
            {
                ObjectNode excursionObject = (ObjectNode) rootNode;

                // excursionId optativo al recivir un post del cliente
                JsonNode excursionIdNode = excursionObject.get("excursionId");
                Long excursionId = (excursionIdNode != null) ? excursionIdNode.longValue() : null;

                String ciudad = excursionObject.get("ciudad").textValue().trim();
                String descrip = excursionObject.get("descrip").textValue().trim();
                String fechaComienzo = excursionObject.get("fechaComienzo").textValue().trim();
                BigDecimal precioXPersona = new BigDecimal(excursionObject.get("precioXPersona").bigIntegerValue());
                int maxPlazas = excursionObject.get("maxPlazas").intValue();
                // plazasLibres optativo al recivir un post del cliente
                JsonNode plazasLibresNode = excursionObject.get("plazasLibres");
                int plazasLibres = (plazasLibresNode == null) ? maxPlazas : plazasLibresNode.intValue();

                return new RestExcursionDto(excursionId, ciudad, descrip, fechaComienzo,
                        precioXPersona, maxPlazas, plazasLibres);
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
