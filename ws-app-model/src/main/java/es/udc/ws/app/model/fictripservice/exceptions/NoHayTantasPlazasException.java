package es.udc.ws.app.model.fictripservice.exceptions;

public class NoHayTantasPlazasException extends Exception
{
    Long excursionId;
    int disponibles;
    int solicitadas;

    public NoHayTantasPlazasException(Long excursionId,
                                      int disponibles,
                                      int solicitadas)
    {
        super("Excursion con id=\"" + excursionId +
                "\" tiene " + disponibles + " plazas disponibles y se han solicitado " +
                solicitadas + ".");

        this.excursionId = excursionId;
        this.disponibles = disponibles;
        this.solicitadas = solicitadas;
    }

    public Long getExcursionId()
    {
        return excursionId;
    }

    public void setExcursionId(Long excursionId)
    {
        this.excursionId = excursionId;
    }

    public int getDisponibles()
    {
        return disponibles;
    }

    public void setDisponibles(int disponibles)
    {
        this.disponibles = disponibles;
    }

    public int getSolicitadas()
    {
        return solicitadas;
    }

    public void setSolicitadas(int solicitadas)
    {
        this.solicitadas = solicitadas;
    }
}
