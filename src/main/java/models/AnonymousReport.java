package models;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class AnonymousReport extends Report {
    private int id;
    private String confirmationNumber;
    private ArrayList<String> selfIdentifiers;
    private static final String DATABASE_TYPE = "Anonymous";

    public AnonymousReport(
            String type,
            String reporterRole,
            int reporterAge,
            String reporterLocation,
            Date incidentDate,
            Time incidentTime,
            String incidentCrossStreets,
            String incidentSetting,
            String incidentType,
            String incidentTypeNotes,
            String incidentMotivation,
            String incidentMotivationNotes,
            boolean injuryOccurred,
            String injuryNotes,
            boolean damagesOccurred,
            String damagesNotes,
            boolean officiallyReported,
            String officialReportNotes,
            String additionalNotes
    ) {
        this.confirmationNumber = "";
        type = DATABASE_TYPE;
        this.type = type ;
        this.reporterRole = reporterRole ;
        this.reporterAge = reporterAge ;
        this.reporterLocation = reporterLocation ;
        this.incidentDate = incidentDate ;
        this.incidentTime = incidentTime ;
        this.incidentCrossStreets = incidentCrossStreets ;
        this.incidentSetting = incidentSetting ;
        this.incidentType = incidentType ;
        this.incidentTypeNotes = incidentTypeNotes ;
        this.incidentMotivation = incidentMotivation ;
        this.incidentMotivationNotes = incidentMotivationNotes ;
        this.injuryOccurred = injuryOccurred ;
        this.injuryNotes = injuryNotes ;
        this.damagesOccurred = damagesOccurred ;
        this.damagesNotes = damagesNotes ;
        this.officiallyReported = officiallyReported ;
        this.officialReportNotes = officialReportNotes ;
        this.additionalNotes = additionalNotes ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public static String getDatabaseType() {
        return DATABASE_TYPE;
    }

    public ArrayList<String> getSelfIdentifiers() {
        return selfIdentifiers;
    }

    public void setSelfIdentifiers(ArrayList<String> selfIdentifiers) {
        this.selfIdentifiers = selfIdentifiers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnonymousReport that = (AnonymousReport) o;
        return id == that.id &&
                Objects.equals(confirmationNumber, that.confirmationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, confirmationNumber);
    }
}
