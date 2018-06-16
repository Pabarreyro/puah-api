package models;

import java.util.ArrayList;
import java.util.Objects;

public class AnonymousReport extends Report {
    private int id;
    private long dateTimeFiled;
    private String formattedDateTime;
    private String confirmationCode;
    private ArrayList<String> selfIdentifiers;
    private static final String DATABASE_TYPE = "Anonymous";

    public AnonymousReport(
            String reporterRole,
            int reporterAge,
            String reporterLocation,
            String incidentDate,
            String incidentTime,
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
        this.dateTimeFiled = System.currentTimeMillis();
        this.confirmationCode = "";
        type = DATABASE_TYPE;
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

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
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

    public String getFormattedDateTime() { return formattedDateTime; }

    public void setFormattedDateTime() { this.formattedDateTime = formattedDateTime; }

    public long getDateTimeFiled() { return dateTimeFiled; }

    public void setDateTimeFiled(long dateTimeFiled) { this.dateTimeFiled = dateTimeFiled; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnonymousReport that = (AnonymousReport) o;
        return id == that.id &&
                Objects.equals(confirmationCode, that.confirmationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, confirmationCode);
    }
}
