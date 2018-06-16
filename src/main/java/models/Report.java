package models;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public abstract class Report {
    private int id;
    private String confirmationNumber;
    private Date dateFiled;
    private Time timeFiled;
    private String reporterRole;
    private ArrayList<String> selfIdentifiers;
    private int reporterAge;
    private String reporterLocation;
    private Date incidentDate;
    private Time incidentTime;
    private String incidentCrossStreets;
    private String indicentSetting;
    private String incidentType;
    private String incidentTypeNotes;
    private String incidentMotivation;
    private String incidentMotivationNotes;
    private boolean injuryOccurred;
    private String injuryNotes;
    private boolean damagesOccurred;
    private String damagesNotes;
    private boolean officiallyReported;
    private String officialReportNotes;
    private String additionalNotes;
}


