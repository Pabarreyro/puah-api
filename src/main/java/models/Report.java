package models;

import java.sql.Time;
import java.util.Date;

public class Report {
    private String confirmationNumber;
    private Date dateFiled;
    private Time timeFiled;
    private int reporterId;
    private int incidentId;

    public Report(int reporterId, int incidentId) {
        this.reporterId = reporterId;
        this.incidentId = incidentId;
        this.dateFiled =;
        this.timeFiled =;
        this.confirmationNumber =;
    }
}


