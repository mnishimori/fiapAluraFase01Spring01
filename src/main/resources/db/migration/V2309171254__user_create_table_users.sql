create schema "user";

create table "user"."users"
(
    "id"         uuid                        not null default gen_random_uuid(),
    "deleted"    boolean                     not null default false,
    "version"    bigint                      not null,
    "created_at" timestamp without time zone not null,
    "updated_at" timestamp without time zone not null,
    "name"       varchar(500)                not null,
    "email"      varchar(500)                not null,
    "password"   varchar(255)                not null
)