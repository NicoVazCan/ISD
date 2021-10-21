package es.udc.ws.app.model.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Excursion
{
    Long excursionId;
    String ciudad;
    LocalDateTime fechaAlta;
    BigDecimal precioXPersona;
    int maxPlazas;
    int plazasLibres;

    public Excursion(Long excursionId, String ciudad,
                     LocalDateTime fechaAlta, BigDecimal precioXPersona,
                     int maxPlazas, int plazasLibres)
    {
        this.excursionId = excursionId;
        this.ciudad = ciudad;
        this.fechaAlta = fechaAlta;
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

    public LocalDateTime getFechaAlta()
    {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDateTime fechaAlta)
    {
        this.fechaAlta = fechaAlta;
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

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Excursion excursion = (Excursion) o;

        if(maxPlazas != excursion.maxPlazas) return false;
        if(plazasLibres != excursion.plazasLibres) return false;
        if(!excursionId.equals(excursion.excursionId)) return false;
        if(!ciudad.equals(excursion.ciudad)) return false;
        if(!fechaAlta.equals(excursion.fechaAlta)) return false;
        return precioXPersona.equals(excursion.precioXPersona);
    }

    @Override
    public int hashCode()
    {
        int result = excursionId.hashCode();
        result = 31*result + ciudad.hashCode();
        result = 31*result + fechaAlta.hashCode();
        result = 31*result + precioXPersona.hashCode();
        result = 31*result + maxPlazas;
        result = 31*result + plazasLibres;
        return result;
    }
}
