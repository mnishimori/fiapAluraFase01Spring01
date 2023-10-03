create schema "user_management";

create table if not exists "user_management"."users"
(
    "id"         uuid                        not null default gen_random_uuid() primary key,
    "deleted"    boolean                     not null default false,
    "version"    bigint                      not null,
    "created_at" timestamp without time zone null,
    "updated_at" timestamp without time zone null,
    "name"       varchar(500)                not null,
    "email"      varchar(500)                not null,
    "password"   varchar(255)                not null
);

create index if not exists users_email_idx ON user_management.users using btree (email);