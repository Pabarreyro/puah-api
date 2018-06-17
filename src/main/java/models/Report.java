package models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Report {
    private String type;
    private String reporterRole;
    private int reporterAge;
    private String reporterLocation;
    private String incidentDate;
    private String incidentTime;
    private String incidentCrossStreets;
    private String incidentSetting;
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
    private long dateTimeFiled;
    private String formattedDateTime;
    private String confirmationCode;

    private int id;
    private List<Community> identifies;
    private List<Organization> organizations;
    private List<Contact> contacts;


    public Report(String type, String reporterRole, int reporterAge, String reporterLocation, String incidentDate, String incidentTime, String incidentCrossStreets, String incidentSetting, String incidentType, String incidentTypeNotes, String incidentMotivation, String incidentMotivationNotes, boolean injuryOccurred, String injuryNotes, boolean damagesOccurred, String damagesNotes, boolean officiallyReported, String officialReportNotes, String additionalNotes) {
        this.type = type;
        this.reporterRole = reporterRole;
        this.reporterAge = reporterAge;
        this.reporterLocation = reporterLocation;
        this.incidentDate = incidentDate;
        this.incidentTime = incidentTime;
        this.incidentCrossStreets = incidentCrossStreets;
        this.incidentSetting = incidentSetting;
        this.incidentType = incidentType;
        this.incidentTypeNotes = incidentTypeNotes;
        this.incidentMotivation = incidentMotivation;
        this.incidentMotivationNotes = incidentMotivationNotes;
        this.injuryOccurred = injuryOccurred;
        this.injuryNotes = injuryNotes;
        this.damagesOccurred = damagesOccurred;
        this.damagesNotes = damagesNotes;
        this.officiallyReported = officiallyReported;
        this.officialReportNotes = officialReportNotes;
        this.additionalNotes = additionalNotes;

        this.dateTimeFiled = System.currentTimeMillis();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReporterRole() {
        return reporterRole;
    }

    public void setReporterRole(String reporterRole) {
        this.reporterRole = reporterRole;
    }

    public int getReporterAge() {
        return reporterAge;
    }

    public void setReporterAge(int reporterAge) {
        this.reporterAge = reporterAge;
    }

    public String getReporterLocation() {
        return reporterLocation;
    }

    public void setReporterLocation(String reporterLocation) {
        this.reporterLocation = reporterLocation;
    }

    public String getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(String incidentDate) {
        this.incidentDate = incidentDate;
    }

    public String getIncidentTime() {
        return incidentTime;
    }

    public void setIncidentTime(String incidentTime) {
        this.incidentTime = incidentTime;
    }

    public String getIncidentCrossStreets() {
        return incidentCrossStreets;
    }

    public void setIncidentCrossStreets(String incidentCrossStreets) {
        this.incidentCrossStreets = incidentCrossStreets;
    }

    public String getIncidentSetting() {
        return incidentSetting;
    }

    public void setIncidentSetting(String incidentSetting) {
        this.incidentSetting = incidentSetting;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public String getIncidentTypeNotes() {
        return incidentTypeNotes;
    }

    public void setIncidentTypeNotes(String incidentTypeNotes) {
        this.incidentTypeNotes = incidentTypeNotes;
    }

    public String getIncidentMotivation() {
        return incidentMotivation;
    }

    public void setIncidentMotivation(String incidentMotivation) {
        this.incidentMotivation = incidentMotivation;
    }

    public String getIncidentMotivationNotes() {
        return incidentMotivationNotes;
    }

    public void setIncidentMotivationNotes(String incidentMotivationNotes) {
        this.incidentMotivationNotes = incidentMotivationNotes;
    }

    public boolean isInjuryOccurred() {
        return injuryOccurred;
    }

    public void setInjuryOccurred(boolean injuryOccurred) {
        this.injuryOccurred = injuryOccurred;
    }

    public String getInjuryNotes() {
        return injuryNotes;
    }

    public void setInjuryNotes(String injuryNotes) {
        this.injuryNotes = injuryNotes;
    }

    public boolean isDamagesOccurred() {
        return damagesOccurred;
    }

    public void setDamagesOccurred(boolean damagesOccurred) {
        this.damagesOccurred = damagesOccurred;
    }

    public String getDamagesNotes() {
        return damagesNotes;
    }

    public void setDamagesNotes(String damagesNotes) {
        this.damagesNotes = damagesNotes;
    }

    public boolean isOfficiallyReported() {
        return officiallyReported;
    }

    public void setOfficiallyReported(boolean officiallyReported) {
        this.officiallyReported = officiallyReported;
    }

    public String getOfficialReportNotes() {
        return officialReportNotes;
    }

    public void setOfficialReportNotes(String officialReportNotes) {
        this.officialReportNotes = officialReportNotes;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public long getDateTimeFiled() {
        return dateTimeFiled;
    }

    public void setDateTimeFiled(long dateTimeFiled) {
        this.dateTimeFiled = dateTimeFiled;
    }

    public String getFormattedDateTime() {
        return formattedDateTime;
    }

    public void setFormattedDateTime() {
        Date date = new Date(this.dateTimeFiled);
        String datePatternToUse = "MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(datePatternToUse);
        this.formattedDateTime = sdf.format(date);
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public void createConfirmationCode(int id) {
        String newId = Integer.toString(id);
        this.confirmationCode = String.format("%s-%s-%s", this.formattedDateTime, this.type, newId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Community> getIdentifies() {
        return identifies;
    }

    public void setIdentifies(List<Community> identifies) {
        this.identifies = identifies;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return dateTimeFiled == report.dateTimeFiled &&
                id == report.id &&
                Objects.equals(type, report.type) &&
                Objects.equals(confirmationCode, report.confirmationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, dateTimeFiled, confirmationCode, id);
    }
}


