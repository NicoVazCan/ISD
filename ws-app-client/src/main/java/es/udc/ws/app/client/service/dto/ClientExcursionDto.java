package es.udc.ws.app.client.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ClientExcursionDto
{
    private Long excursionId;
    private String ciudad, descrip;
    private LocalDateTime fechaComienzo;
    private BigDecimal precioXPersona;
    private int maxPlazas;
    private int plazasOcupadas;

    public ClientExcursionDto(String ciudad, String descrip, LocalDateTime fechaComienzo, BigDecimal precioXPersona, int maxPlazas)
    {
        this.ciudad = ciudad;
        this.descrip = descrip;
        this.fechaComienzo = fechaComienzo.withNano(0);
        this.precioXPersona = precioXPersona;
        this.maxPlazas = maxPlazas;
    }

    public Long getExcursionId()
    {
        return excursionId;
    }

    public void setExcursionId(Long excursionId)
    {
        this.excursionId = excursionId;
    }

    public String getCiudad()
    {
        return ciudad;
    }

    public void setCiudad(String ciudad)
    {
        this.ciudad = ciudad;
    }

    public String getDescrip()
    {
        return descrip;
    }

    public void setDescrip(String descrip)
    {
        this.descrip = descrip;
    }

    public LocalDateTime getFechaComienzo()
    {
        return fechaComienzo;
    }

    public void setFechaComienzo(LocalDateTime fechaComienzo)
    {
        this.fechaComienzo = fechaComienzo.withNano(0);
    }

    public BigDecimal getPrecioXPersona()
    {
        return precioXPersona;
    }

    public void setPrecioXPersona(BigDecimal precioXPersona)
    {
        this.precioXPersona = precioXPersona;
    }

    public int getMaxPlazas()
    {
        return maxPlazas;
    }

    public void setMaxPlazas(int maxPlazas)
    {
        this.maxPlazas = maxPlazas;
    }

    public int getPlazasOcupadas()
    {
        return plazasOcupadas;
    }

    public void setPlazasOcupadas(int plazasLibres)
    {
        this.plazasOcupadas = plazasLibres;
    }
}
