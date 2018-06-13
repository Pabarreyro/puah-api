package models;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;

public class Incident {
    private Date dateOfIncident;
    private Time timeOfIncident;
    private String crossStreets;
    private String location;
    private String locationDetails;
    private String type;
    private String typeDetails;
    private String motivation;
    private String motivationDetails;
    private Boolean injury;
    private Boolean propertyDamage;
    private Boolean officialReport;
    private String reportedTo;

    public Incident(Date dateOfIncident, Time timeOfIncident, String crossStreets, String location, String locationDetails, String type, String typeDetails, String motivation, String motivationDetails, Boolean injury, Boolean propertyDamage, Boolean officialReport, String reportedTo) {
        this.dateOfIncident = dateOfIncident;
        this.timeOfIncident = timeOfIncident;
        this.crossStreets = crossStreets;
        this.location = location;
        this.locationDetails = locationDetails;
        this.type = type;
        this.typeDetails = typeDetails;
        this.motivation = motivation;
        this.motivationDetails = motivationDetails;
        this.injury = injury;
        this.propertyDamage = propertyDamage;
        this.officialReport = officialReport;
        this.reportedTo = reportedTo;
    }

    public Date getDateOfIncident() {
        return dateOfIncident;
    }

    public void setDateOfIncident(Date dateOfIncident) {
        this.dateOfIncident = dateOfIncident;
    }

    public Time getTimeOfIncident() {
        return timeOfIncident;
    }

    public void setTimeOfIncident(Time timeOfIncident) {
        this.timeOfIncident = timeOfIncident;
    }

    public String getCrossStreets() {
        return crossStreets;
    }

    public void setCrossStreets(String crossStreets) {
        this.crossStreets = crossStreets;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationDetails() {
        return locationDetails;
    }

    public void setLocationDetails(String locationDetails) {
        this.locationDetails = locationDetails;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeDetails() {
        return typeDetails;
    }

    public void setTypeDetails(String typeDetails) {
        this.typeDetails = typeDetails;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public String getMotivationDetails() {
        return motivationDetails;
    }

    public void setMotivationDetails(String motivationDetails) {
        this.motivationDetails = motivationDetails;
    }

    public Boolean getInjury() {
        return injury;
    }

    public void setInjury(Boolean injury) {
        this.injury = injury;
    }

    public Boolean getPropertyDamage() {
        return propertyDamage;
    }

    public void setPropertyDamage(Boolean propertyDamage) {
        this.propertyDamage = propertyDamage;
    }

    public Boolean getOfficialReport() {
        return officialReport;
    }

    public void setOfficialReport(Boolean officialReport) {
        this.officialReport = officialReport;
    }

    public String getReportedTo() {
        return reportedTo;
    }

    public void setReportedTo(String reportedTo) {
        this.reportedTo = reportedTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Incident incident = (Incident) o;
        return Objects.equals(dateOfIncident, incident.dateOfIncident) &&
                Objects.equals(timeOfIncident, incident.timeOfIncident) &&
                Objects.equals(crossStreets, incident.crossStreets) &&
                Objects.equals(location, incident.location) &&
                Objects.equals(locationDetails, incident.locationDetails) &&
                Objects.equals(type, incident.type) &&
                Objects.equals(typeDetails, incident.typeDetails) &&
                Objects.equals(motivation, incident.motivation) &&
                Objects.equals(motivationDetails, incident.motivationDetails) &&
                Objects.equals(injury, incident.injury) &&
                Objects.equals(propertyDamage, incident.propertyDamage) &&
                Objects.equals(officialReport, incident.officialReport) &&
                Objects.equals(reportedTo, incident.reportedTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateOfIncident, timeOfIncident, crossStreets, location, locationDetails, type, typeDetails, motivation, motivationDetails, injury, propertyDamage, officialReport, reportedTo);
    }
}
