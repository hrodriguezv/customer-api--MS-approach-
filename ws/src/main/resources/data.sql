drop table IF EXISTS authority;
create TABLE authority (
id INTEGER,
authority VARCHAR(255),
PRIMARY KEY (id)
);

drop table IF EXISTS credentials;
create TABLE credentials (
id INTEGER,
enabled BOOLEAN NOT NULL,
name VARCHAR(255) NOT NULL,
password VARCHAR(255) NOT NULL,
version INTEGER,
PRIMARY KEY (id)
);

drop table IF EXISTS credentials_authorities;
create TABLE credentials_authorities (
credentials_id BIGINT NOT NULL,
authorities_id BIGINT NOT NULL
);

drop table IF EXISTS oauth_client_details;
create TABLE oauth_client_details (
client_id VARCHAR(256) PRIMARY KEY,
resource_ids VARCHAR(256),
client_secret VARCHAR(256),
scope VARCHAR(256),
authorized_grant_types VARCHAR(256),
web_server_redirect_uri VARCHAR(256),
authorities VARCHAR(256),
access_token_validity INTEGER,
refresh_token_validity INTEGER,
additional_information VARCHAR(4096),
autoapprove VARCHAR(256)
);

drop table IF EXISTS oauth_client_token;
create TABLE oauth_client_token (
token_id VARCHAR(256),
token BYTEA,
authentication_id VARCHAR(256),
user_name VARCHAR(256),
client_id VARCHAR(256)
);

drop table IF EXISTS oauth_access_token;
create TABLE oauth_access_token (
token_id VARCHAR(256),
token BYTEA,
authentication_id VARCHAR(256) NOT NULL DEFAULT '',
user_name VARCHAR(256),
client_id VARCHAR(256),
authentication BYTEA,
refresh_token VARCHAR(256)
);

drop table IF EXISTS oauth_refresh_token;
create TABLE oauth_refresh_token (
token_id VARCHAR(256),
token BYTEA,
authentication BYTEA
);

drop table IF EXISTS oauth_code;
create TABLE oauth_code (
code VARCHAR(256),
authentication BYTEA
);

drop table IF EXISTS oauth_approvals;
create TABLE oauth_approvals (
userid VARCHAR(256),
clientid VARCHAR(256),
scope VARCHAR(256),
status VARCHAR(10),
expiresat TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
lastmodifiedat TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

alter table oauth_access_token add CONSTRAINT oauth_access_token_pk PRIMARY KEY (authentication_id);

insert into authority values (0, 'role_oauth_admin');
insert into authority values (1, 'role_admin');
insert into authority values (2, 'role_user');
insert into credentials values (0, true, 'oauth_admin', 'admin', 0);
insert into credentials values (1, true, 'resource_admin', 'admin', 0);
insert into credentials values (2, true, 'user', 'user', 0);
insert into credentials_authorities values (0, 0);
insert into credentials_authorities values (1, 1);
insert into credentials_authorities values (2, 2);

-- secret = Y2xpZW50LW1hbmFnZXItZHVtbXktcGFzc3dvcmQ=

insert into oauth_client_details
(client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri,
authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
values
('client-manager-trusted-client', 'client-manager-ws-resources', '$e0801$qMbwUtTUOXd+MJvEcAFpEqV57AKeRaugqbjpc4Mgh+hGyqWN23NEiuslRqamVZUQ16QpRVVnua69tG4HcaKYaQ==$hbb6Y3TXm+UVkkAYA5kKBDDn5CQnnSBnjUHHDecreo4=', 'read,write',
'password,client_credentials', '', '', 15, 15, null, 'false');

insert into oauth_client_details
(client_id, client_secret)
values
('resource-server', 'resource-server');

insert into company (id, status, created_date, updated_date, name, description, address, trunk, parent_id) values
  (1, 1, '2019-09-24', '2019-09-24', 'Santo Domingo Motors', 'Vehículos Originales', 'Santo Domingo, República Dominicana', true, null),
  (2, 1, '2019-09-24', '2019-09-24', 'Nissan Dominicana', 'Jeepetas', 'La Vega, República Dominicana', false, 1);

insert into user (id, status, created_date, updated_date, username, password, roles, company_id) values
  (1, 1, '2019-09-24', '2019-09-24', 'pedro', '$e0801$QeuYaP+8n+C2+IXfEHeXDg3WqpSQipBDDtnZWIPKwHxL6cU5KN+8+XTnKp5/PRIyJF6p96vSkrsZ7tQHaa+MRw==$yzftjS3j489njcdobUtFgsE2yOfHftE3/LUrrK+9buw=', 'ROLE_ADMIN,ROLE_USER', 1),
  (2, 1, '2019-09-24', '2019-09-24', 'juan', '$e0801$QeuYaP+8n+C2+IXfEHeXDg3WqpSQipBDDtnZWIPKwHxL6cU5KN+8+XTnKp5/PRIyJF6p96vSkrsZ7tQHaa+MRw==$yzftjS3j489njcdobUtFgsE2yOfHftE3/LUrrK+9buw=', 'ROLE_USER', 1);