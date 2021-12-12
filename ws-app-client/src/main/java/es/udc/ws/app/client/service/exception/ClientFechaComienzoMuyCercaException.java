package es.udc.ws.app.client.service.exception;

import java.time.LocalDateTime;

public class ClientFechaComienzoMuyCercaException extends Exception
{
    LocalDateTime fechaAct;
    LocalDateTime fechaComienzo;
    int margen;

    public ClientFechaComienzoMuyCercaException(LocalDateTime fechaAct,
                                          LocalDateTime fechaComienzo,
                                          int margen)
    {
        super("Entre la fecha actual y la fecha de comienzo de la excursion, " +
                fechaAct + " a " + fechaComienzo +
                ", hay menos de " + margen + " horas.");
        this.fechaAct = fechaAct;
        this.fechaComienzo = fechaComienzo;
        this.margen = margen;
    }

    public LocalDateTime getFechaAct()
    {
        return fechaAct;
    }

    public void setFechaAct(LocalDateTime fechaAct)
    {
        this.fechaAct = fechaAct;
    }

    public LocalDateTime getFechaComienzo()
    {
        return fechaComienzo;
    }

    public void setFechaComienzo(LocalDateTime fechaComienzo)
    {
        this.fechaComienzo = fechaComienzo;
    }

    public int getMargen()
    {
        return margen;
    }

    public void setMargen(int margen)
    {
        this.margen = margen;
    }
}
