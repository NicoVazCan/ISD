namespace java es.udc.ws.app.thrift

struct ThriftExcursionDto
{
    1: i64 excursionId;
    2: string ciudad;
    3: string descrip;
    4: string fechaComienzo;
    5: string precioXPersona;
    6: i32 maxPlazas;
    7: i32 plazasLibres;
}

struct ThriftReservaDto
{
    1: i64 reservaId;
    2: string email;
    3: i32 numPlazas;
    4: string tarjeta;
    5: string fecha;
    6: i64 excursionId;
}


exception ThriftInputValidationException
{
    1: string message
}

exception ThriftInstanceNotFoundException
{
    1: string instanceId
    2: string instanceType
}

exception ThriftFechaComienzoMuyCercaException
{
    1: string fechaAct;
    2: string fechaComienzo;
    3: i32 margen;
}

exception ThriftNoHayTantasPlazasException
{
    1: i64 excursionId;
    2: i32 disponibles;
    3: i32 solicitadas;
}


service ThriftFicTripService
{
    ThriftExcursionDto addExcursion(1: ThriftExcursionDto excursionDto) throws(1: ThriftInputValidationException e1, 2: ThriftFechaComienzoMuyCercaException e2);

    ThriftReservaDto addReserva(1: ThriftReservaDto reservaDto) throws(1: ThriftInputValidationException e1, 2: ThriftInstanceNotFoundException e2, 3: ThriftFechaComienzoMuyCercaException e3, 4: ThriftNoHayTantasPlazasException e4);
}