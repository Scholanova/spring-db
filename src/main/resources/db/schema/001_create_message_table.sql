--liquibase formatted sql

--changeset scholanova:1
CREATE TABLE IF NOT EXISTS MESSAGES (
  ID                  NUMERIC         NOT NULL,
  CONTENT             VARCHAR(255)    NOT NULL,
  PRIMARY KEY (ID)
);