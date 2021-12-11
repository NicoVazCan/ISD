package es.udc.ws.app.restservice.json;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.app.model.fictripservice.exceptions.FechaComienzoMuyCercaException;
import es.udc.ws.app.model.fictripservice.exceptions.NoHayTantasPlazasException;

public class AppExceptionToJsonConversor
{
    public static ObjectNode toFechaComienzoMuyCercaException(FechaComienzoMuyCercaException ex) {

        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

        exceptionObject.put("errorType", "FechaComienzoMuyCercaException");
        if (ex.getFechaComienzo() != null) {
            exceptionObject.put("fechaComienzo", ex.getFechaComienzo().toString());
        } else {
            exceptionObject.set("fechaComienzo", null);
        }
        if (ex.getFechaAct() != null) {
            exceptionObject.put("fechaActual", ex.getFechaAct().toString());
        } else {
            exceptionObject.set("fechaActual", null);
        }
        exceptionObject.put("margen", ex.getMargen());

        return exceptionObject;
    }

    public static ObjectNode toNoHayTantasPlazasException(NoHayTantasPlazasException ex) {

        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

        exceptionObject.put("errorType", "NoHayTantasPlazasException");
        exceptionObject.put(
                "excursionId", (ex.getExcursionId() != null) ? ex.getExcursionId() : null);
        exceptionObject.put("plazasSolicitadas", ex.getSolicitadas());
        exceptionObject.put("plazasDisponibles", ex.getDisponibles());

        return exceptionObject;
    }
}
