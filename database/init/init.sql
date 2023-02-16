create table if not exists player (
    id integer primary key,
    nickname varchar not null
);

insert into player (id, nickname) values (1, 'kaparzo');
insert into player (id, nickname) values (2, 'aggressive');
insert into player (id, nickname) values (3, 'hvarz');
insert into player (id, nickname) values (4, 'ezury');
