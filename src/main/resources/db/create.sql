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
    confirmationCode VARCHAR,
    dateTimeFiled INTEGER,
    type VARCHAR,
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
    additionalNotes VARCHAR
);

CREATE TABLE contacts (
    id SERIAL PRIMARY KEY,
    firstName VARCHAR,
    lastName VARCHAR,
    phone VARCHAR,
    email VARCHAR
);

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

CREATE TABLE organizations_contacts (
    id SERIAL PRIMARY KEY,
    contactId INTEGER,
    organizationId INTEGER
);

CREATE TABLE organizations_reports (
    id SERIAL PRIMARY KEY,
    reportId INTEGER,
    organizationId INTEGER
);

CREATE TABLE reports_communities (
    id SERIAL PRIMARY KEY,
    reportId INTEGER,
    communityId INTEGER
);

CREATE TABLE reports_contacts (
    id SERIAL PRIMARY KEY,
    contactId INTEGER,
    reportId INTEGER
);

CREATE DATABASE puah_test WITH TEMPLATE puah;