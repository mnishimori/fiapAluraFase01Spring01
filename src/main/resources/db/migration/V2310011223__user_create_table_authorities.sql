create table if not exists "user_management"."authorities"
(
    "id"         uuid                        not null default gen_random_uuid() primary key,
    "version"    bigint                      not null,
    "created_at" timestamp without time zone not null,
    "updated_at" timestamp without time zone not null,
    "name"       varchar(500)                not null
)