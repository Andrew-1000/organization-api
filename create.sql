SET MODE PostgreSQL;

CREATE DATABASE organization_api;
\c organization_api;

CREATE TABLE departments (
 department_id SERIAL PRIMARY KEY,
 department_name VARCHAR,
 description VARCHAR
);

CREATE TABLE news (
 news_id SERIAL PRIMARY KEY,
 name VARCHAR
);



CREATE TABLE users (
 user_id SERIAL PRIMARY KEY,
 user_name VARCHAR,
 position VARCHAR
);


CREATE DATABASE organization_api_test WITH TEMPLATE organization_api;