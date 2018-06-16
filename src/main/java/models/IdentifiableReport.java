package models;

import java.util.ArrayList;
import java.util.Objects;

public class IdentifiableReport extends Report {
    private int id;
    private long dateTimeFiled;
    private String formattedDateTime;
    private String confirmationCode;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private ArrayList<String> selfIdentifiers;
    private static final String DATABASE_TYPE = "Nonanonymous";

    public IdentifiableReport(
            String firstName,
            String lastName,
            String phone,
            String email,
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
        this.firstName = firstName ;
        this.lastName = lastName ;
        this.phone = phone ;
        this.email = email ;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        IdentifiableReport that = (IdentifiableReport) o;
        return id == that.id &&
                Objects.equals(confirmationCode, that.confirmationCode) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, confirmationCode, firstName, lastName);
    }
}
