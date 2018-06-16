package dao;

import models.Community;
import models.Report;

import java.util.List;

public interface ReportDao {

    // CREATE
    void add(Report report);
    void addCommunityToReport(Report report, Community community);

    // READ
    List<Report> getAll();
    List<Community> getAllCommunities(int reportId);
    Report findById(int reportId);
    Report findByConfirmationCode (String confirmationCode);

    // UPDATE
    void update(Report report);

    // DELETE
    void deleteById(int reportId);
    void deleteByConfirmationCode(String confirmationCode);
    void clearAll();

}
