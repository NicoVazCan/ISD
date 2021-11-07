-- ----------------------------------------------------------------------------
-- Model
-------------------------------------------------------------------------------
drop table reservas;
drop table excursiones;

create table excursiones
(	excursion_id bigint      not null auto_increment,
    ciudad varchar(32)       collate latin1_bin not null,
 	descrip varchar(1024)    collate latin1_bin not null,
 	fecha_alta datetime      not null,
    fecha_comienzo datetime  not null,
    precio_x_persona numeric not null,
    max_plazas integer       not null,
    plazas_libres integer    not null,
    constraint cp_excursion_id primary key(excursion_id)
) engine = InnoDB;

create table reservas
(	reserva_id bigint   not null auto_increment,
    email varchar(64)   collate latin1_bin not null,
    num_plazas integer  not null,
    tarjeta varchar(16) collate latin1_bin not null,
    fecha datetime      not null,
    excursion_id bigint not null,
    constraint cp_reserva_id primary key(reserva_id),
    constraint cfn_plazas_libres foreign key(excursion_id)
        references excursiones(excursion_id) on delete cascade
) engine = InnoDB;