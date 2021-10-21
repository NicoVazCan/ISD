package es.udc.ws.app.model.util;

public class Reserva
{
    Long reservaId;
    String email;
    int numPlazas;
    String tarjeta;

    public Reserva(Long reservaId, String email, int numPlazas, String tarjeta)
    {
        this.reservaId = reservaId;
        this.email = email;
        this.numPlazas = numPlazas;
        this.tarjeta = tarjeta;
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

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Reserva reserva = (Reserva) o;

        if(numPlazas != reserva.numPlazas) return false;
        if(!reservaId.equals(reserva.reservaId)) return false;
        if(!email.equals(reserva.email)) return false;
        return tarjeta.equals(reserva.tarjeta);
    }

    @Override
    public int hashCode()
    {
        int result = reservaId.hashCode();
        result = 31*result + email.hashCode();
        result = 31*result + numPlazas;
        result = 31*result + tarjeta.hashCode();
        return result;
    }
}
