begin;

-- UNCOMMENT THESE LINES TO RESET THIS DB
-- **************************************

set schema 'youcoach';
drop table if exists secured_users, users cascade;
drop schema youcoach;

-- END UNCOMMENT
-- ***************************************

create schema if not exists youcoach;

set schema 'youcoach';

create table if not exists secured_users(
                                            su_id uuid primary key DEFAULT uuid_generate_v4 (),
                                            username varchar(50),
                                            password varchar,
                                            role varchar(15)
);

create table if not exists users(
                                    u_id uuid primary key,
                                    firstname varchar(50),
                                    lastname varchar(50),
                                    email varchar(50),
                                    secured_id uuid,
                                    foreign key (secured_id) references secured_users(su_id)
);

--- insert some secured users for login
insert into secured_users (username, password, role) values ('student','$2y$12$ZSWZWelm2qwDkpclH4/FR.EgTg4H297cvNFI0Li61//H4c7nT6Vva','coachee');
insert into secured_users (username, password, role) values ('coach','$2y$12$LGjbl1dKNu2vLz5ZwrLOkO5nOg2VzXmvp0asq89isoZ6CChDuqXG6','coach');

--- new requirements on 13/05
insert into secured_users (username, password, role) values ('coachee1@school.org','YouC0ach', 'coachee');
insert into secured_users (username, password, role) values ('coachee2@school.org','YouC0ach', 'coachee');
insert into secured_users (username, password, role) values ('coach1@school.org','YouC0ach', 'coach');
insert into secured_users (username, password, role) values ('coach2@school.org','YouC0ach', 'coach');
insert into secured_users (username, password, role) values ('admin1@school.org','YouC0ach', 'admin');
insert into secured_users (username, password, role) values ('admin2@school.org','YouC0ach', 'admin');

--rollback;

commit;
