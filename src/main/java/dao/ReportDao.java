package dao;

import models.Report;

import java.util.List;

public interface ReportDao {

    // CREATE
    public void add(Report report);

    // READ
    public List<Report> getAll();
    public Report findById(int reportId);
    public Report findByConfirmationNumber (String confirmationNumber);

    // UPDATE
    public void update(Report report);

    // DELETE
    public void deleteById(int reportId);
    public void deleteByConfirmationNumber(String confirmationNumber);
    public void clearAll();

}
