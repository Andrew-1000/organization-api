## CODE BEAT GRADING
[![codebeat badge](https://codebeat.co/badges/251a61ba-8bdd-4404-9df5-63e9e4de3062)](https://codebeat.co/projects/github-com-andrew-1000-organization-api-feature-organization-api)

Organisational-API
## Author
ANDREW AMBIA
Contents are the following
 App description
 Technologies used
 Installation
 Contacts
 License
## APPLICATION DESCRIPTION
This application will allow the user to update the cpmpany about what is going on and who is doing what. I t gives news to every body in the company .
## TECHNOLOGIES USED
IntelliJ IDEA Community Edition
classes with extension java
Setup
Install IntelliJ IDEAL Community Edition
Clone this repository
Open it within IntelliJ
CREATE DATABASE organisationapi;
\c organisationapi;
CREATE TABLE departements (id serial PRIMARY KEY, dept_name VARCHAR, dept_description VARCHAR, dept_size INTEGER);
CREATE TABLE users (id serial PRIMARY KEY, title VARCHAR body VARCHAR dept_Id INTEGER);
CREATE TABLE news (id serial PRIMARY KEY, user_name VARCHAR,user_position VARCHAR, user_role VARCHAR);
CREATE TABLE IF NOT EXISTS user_in_departements (id serial PRIMARY KEY ,userId INTEGER,departementsId INTEGER);
CREATE DATABASE organisationapi_Test WITH TEMPLATE organisationapi;
