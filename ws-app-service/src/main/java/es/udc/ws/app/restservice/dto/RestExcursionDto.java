package es.udc.ws.app.restservice.dto;

import java.math.BigDecimal;

public class RestExcursionDto
{
    private Long excursionId;
    private String ciudad, descrip;
    private String fechaComienzo;
    private BigDecimal precioXPersona;
    private int maxPlazas;
    private int plazasLibres;

    public RestExcursionDto(Long excursionId, String ciudad, String descrip,
                            String fechaComienzo, BigDecimal precioXPersona,
                            int maxPlazas, int plazasLibres)
    {
        this.excursionId = excursionId;
        this.ciudad = ciudad;
        this.descrip = descrip;
        this.fechaComienzo = fechaComienzo;
        this.precioXPersona = precioXPersona;
        this.maxPlazas = maxPlazas;
        this.plazasLibres = plazasLibres;
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

    public String getFechaComienzo()
    {
        return fechaComienzo;
    }

    public void setFechaComienzo(String fechaComienzo)
    {
        this.fechaComienzo = fechaComienzo;
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

    public int getPlazasLibres()
    {
        return plazasLibres;
    }

    public void setPlazasLibres(int plazasLibres)
    {
        this.plazasLibres = plazasLibres;
    }
}
