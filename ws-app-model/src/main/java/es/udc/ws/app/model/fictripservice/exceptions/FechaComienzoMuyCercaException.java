package es.udc.ws.app.model.fictripservice.exceptions;

import java.time.LocalDateTime;

public class FechaComienzoMuyCercaException extends Exception
{
    private Long excursionId;
    LocalDateTime fechaAct;
    LocalDateTime fechaComienzo;
    int margen;

    public FechaComienzoMuyCercaException(Long excursionId,
                                          LocalDateTime fechaAct,
                                          LocalDateTime fechaComienzo,
                                          int margen)
    {
        super("Excursion con id=\"" + excursionId +
                "\" de " + fechaAct + " a " + fechaComienzo +
                ", hay menos de " + margen + " horas.");
        this.excursionId = excursionId;
        this.fechaAct = fechaAct;
        this.fechaComienzo = fechaComienzo;
        this.margen = margen;
    }

    public Long getExcursionId()
    {
        return excursionId;
    }

    public void setExcursionId(Long excursionId)
    {
        this.excursionId = excursionId;
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
