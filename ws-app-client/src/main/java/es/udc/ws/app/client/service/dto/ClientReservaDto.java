package es.udc.ws.app.client.service.dto;

import java.time.LocalDateTime;

public class ClientReservaDto
{
    private Long reservaId;
    private String email;
    private int numPlazas;
    private String tarjeta;
    private LocalDateTime fecha;
    private Long excursionId;

    public ClientReservaDto(String email, int numPlazas, String tarjeta, Long excursionId)
    {
        this.email = email;
        this.numPlazas = numPlazas;
        this.tarjeta = tarjeta;
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

    public LocalDateTime getFecha()
    {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha)
    {
        this.fecha = fecha.withNano(0);
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
