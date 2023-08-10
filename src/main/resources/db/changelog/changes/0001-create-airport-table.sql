--liquibase formatted sql

--changeset andrejs:1

CREATE TABLE airport
(
    id      serial PRIMARY KEY,

    country VARCHAR(255) NOT NULL,
    city    VARCHAR(255) NOT NULL,
    airport VARCHAR(3)   NOT NULL
);



