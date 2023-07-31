--liquibase formatted sql

--changeset andrejs:1

CREATE TABLE flight
(
    id              serial PRIMARY KEY,


    from_airport_id BIGINT       NOT NUll,
    to_airport_id   BIGINT       NOT NULL,
    carrier         VARCHAR(255) NOT NULL,
    arrival_time    TIMESTAMP    NOT NULL,
    departure_time  TIMESTAMP    NOT NULL,
    FOREIGN KEY (from_airport_id) REFERENCES airport (Id),
    FOREIGN KEY (to_airport_id) REFERENCES airport (Id)

);