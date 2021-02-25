insert into privileges (id, name) values (1, 'READ_PRIVILEGE');
insert into privileges (id, name) values (2, 'WRITE_PRIVILEGE');

insert into roles (id, name) values (1, 'ROLE_ADMIN');
insert into roles (id, name) values (2, 'ROLE_USER');

insert into users (id, first_name, last_name, email, password, enabled) values (1, 'Marek', 'Żołek', 'mzolek93@gmail.com', '$2a$04$/dkcmgfweinqtidig2ouced3O4dDdNyPB3RhNL2OdHImVXk9berU2', true);
insert into USERS_ROLES (user_id, role_id) values (1, 1);

insert into roles_privileges (role_id, privilege_id) values (1,1);
insert into roles_privileges (role_id, privilege_id) values (1,2);
insert into roles_privileges (role_id, privilege_id) values (2,1);