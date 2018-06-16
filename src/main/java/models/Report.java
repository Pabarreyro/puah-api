package models;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public abstract class Report {
    public Date dateFiled;
    public Time timeFiled;
    public String type;
    public String reporterRole;
    public int reporterAge;
    public String reporterLocation;
    public Date incidentDate;
    public Time incidentTime;
    public String incidentCrossStreets;
    public String incidentSetting;
    public String incidentType;
    public String incidentTypeNotes;
    public String incidentMotivation;
    public String incidentMotivationNotes;
    public boolean injuryOccurred;
    public String injuryNotes;
    public boolean damagesOccurred;
    public String damagesNotes;
    public boolean officiallyReported;
    public String officialReportNotes;
    public String additionalNotes;
}


