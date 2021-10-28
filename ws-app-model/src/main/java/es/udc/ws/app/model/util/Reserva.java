package es.udc.ws.app.model.util;

import java.time.LocalDateTime;

public class Reserva
{
    Long reservaId;
    String email;
    int numPlazas;
    String tarjeta;
    LocalDateTime fecha;
    Long excursionId;

    public Reserva(String email, int numPlazas, String tarjeta, Long excursionId)
    {
        this.reservaId = null;
        this.email = email;
        this.numPlazas = numPlazas;
        this.tarjeta = tarjeta;
        this.fecha = null;
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

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Reserva reserva = (Reserva) o;

        if(numPlazas != reserva.numPlazas) return false;
        if(!reservaId.equals(reserva.reservaId)) return false;
        if(!email.equals(reserva.email)) return false;
        if(!tarjeta.equals(reserva.tarjeta)) return false;
        if(!fecha.equals(reserva.fecha)) return false;
        return excursionId.equals(reserva.excursionId);
    }

    @Override
    public int hashCode()
    {
        int result = reservaId.hashCode();
        result = 31*result + email.hashCode();
        result = 31*result + numPlazas;
        result = 31*result + tarjeta.hashCode();
        result = 31*result + fecha.hashCode();
        result = 31*result + reservaId.hashCode();
        return result;
    }
}
