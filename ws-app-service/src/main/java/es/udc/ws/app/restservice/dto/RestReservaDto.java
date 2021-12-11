package es.udc.ws.app.restservice.dto;

public class RestReservaDto
{
    private Long reservaId;
    private String email;
    private int numPlazas;
    private String tarjeta;
    private String fecha;
    private Long excursionId;

    public RestReservaDto(Long reservaId, String email,
                          int numPlazas, String tarjeta,
                          String fecha, Long excursionId)
    {
        this.reservaId = reservaId;
        this.email = email;
        this.numPlazas = numPlazas;
        this.tarjeta = tarjeta;
        this.fecha = fecha;
        this.excursionId = excursionId;
    }

    public Long getReservaId()
    {
        return reservaId;
    }

    public void setReservaId(Long reservaId)
    {
        this.reservaId = reservaId;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getNumPlazas()
    {
        return numPlazas;
    }

    public void setNumPlazas(int numPlazas)
    {
        this.numPlazas = numPlazas;
    }

    public String getTarjeta()
    {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta)
    {
        this.tarjeta = tarjeta;
    }

    public String getFecha()
    {
        return fecha;
    }

    public void setFecha(String fecha)
    {
        this.fecha = fecha;
    }

    public Long getExcursionId()
    {
        return excursionId;
    }

    public void setExcursionId(Long excursionId)
    {
        this.excursionId = excursionId;
    }
}
