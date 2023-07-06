CREATE TABLE acquiring(
id integer auto_increment,
terminal_id varchar(255) not null,
cr_id BIGINT not null,
unn_id integer,
transaction_date date,
channel varchar(255),
volume integer not null,
transaction_value double not null,
city varchar(255),
location varchar(255),
region varchar(255),
mcc integer not null,
mcc_description varchar(255)
);