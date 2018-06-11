CREATE DATABASE resources;

\c resources

CREATE TABLE organizations (
    id SERIAL PRIMARY KEY,
    name VARCHAR,
    address VARCHAR,
    zip VARCHAR,
    phone VARCHAR,
    website VARCHAR,
    email VARCHAR,
);

CREATE TABLE communities (
    id SERIAL PRIMARY KEY,
    name VARCHAR,
    type VARCHAR,
);

CREATE TABLE services (
    id SERIAL PRIMARY KEY,
    name VARCHAR,
);

CREATE TABLE regions (
    id SERIAL PRIMARY KEY,
    name VARCHAR,
)

CREATE TABLE organizations_communities (
    id SERIAL PRIMARY KEY,
    organizationId INTEGER,
    communityId INTEGER
);

CREATE TABLE organizations_services (
    id SERIAL PRIMARY KEY,
    organizationId INTEGER,
    serviceId INTEGER
);

CREATE TABLE organizations_regions (
    id SERIAL PRIMARY KEY,
    organizationId INTEGER,
    regionId INTEGER
);

CREATE TABLE resources_test WITH TEMPLATE resources;
