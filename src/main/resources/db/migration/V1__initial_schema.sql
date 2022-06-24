create table if not exists value
(
    id bigint not null primary key,
    city text,
    streets text
);
create sequence if not exists SEQ_VALUE increment 1 start 1;

