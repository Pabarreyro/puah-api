CREATE DATABASE puah;

\c puah;

CREATE TABLE organizations (
    id SERIAL PRIMARY KEY,
    name VARCHAR,
    address VARCHAR,
    zip VARCHAR,
    phone VARCHAR,
    website VARCHAR,
    email VARCHAR
);

CREATE TABLE communities (
    id SERIAL PRIMARY KEY,
    name VARCHAR,
    type VARCHAR
);

CREATE TABLE services (
    id SERIAL PRIMARY KEY,
    name VARCHAR
);

CREATE TABLE regions (
    id SERIAL PRIMARY KEY,
    name VARCHAR
);

CREATE TABLE reports (
    id SERIAL PRIMARY KEY,
    confirmationNumber VARCHAR,
    dateTimeFiled INTEGER,
    type VARCHAR,
    firstName VARCHAR,
    lastName VARCHAR,
    phone VARCHAR,
    email VARCHAR,
    reporterRole VARCHAR,
    reporterAge INTEGER,
    reporterLocation VARCHAR,
    incidentDate VARCHAR,
    incidentTime VARCHAR,
    incidentCrossStreets VARCHAR,
    incidentSetting VARCHAR,
    incidentType VARCHAR,
    incidentTypeNotes VARCHAR,
    incidentMotivation VARCHAR,
    incidentMotivationNotes VARCHAR,
    injuryOccurred BOOLEAN NOT NULL,
    injuryNotes VARCHAR,
    damagesOccurred BOOLEAN NOT NULL,
    damagesNotes VARCHAR,
    officiallyReported BOOLEAN NOT NULL,
    officialReportNotes VARCHAR,
    additionalNotes VARCHAR,
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

CREATE DATABASE puah_test WITH TEMPLATE puah;