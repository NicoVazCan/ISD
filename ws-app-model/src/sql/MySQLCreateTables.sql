-- ----------------------------------------------------------------------------
-- Model
-------------------------------------------------------------------------------
drop table excursiones cascade constraint purge;
drop table reservas    cascade constraint purge;

create table excursiones
(	excursion_id bigint      constraint cp_excursion_id primary key,
    ciudad 	varchar          constraint cn_ciudad not null,
 	descrip varchar          constraint cn_descrip not null,
 	fecha_alta date          constraint cm_fecha_alta not null,
    fecha_comienza date      constraint cn_fecha_comienza not null,
    precio_x_persona numeric constraint cn_precio_x_persona not null,
    max_plazas integer       constraint cn_max_plazas not null,
    plazas_libres integer    constraint cn_plazas_libres not null
);

create table reservas
(	reserva_id bigint  constraint cp_reserva_id primary key,
    email varchar      constraint cn_email not null,
    num_plazas integer constraint cn_num_plazas not null,
    tarjeta varchar    constraint cn_tarjeta not null,
    fecha date         constraint cn_fecha not null,
    excursion_id bigint constraint cfn_plazas_libres
        references excursiones(excursion_id) not null
);