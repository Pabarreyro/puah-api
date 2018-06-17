package dao;

import models.*;

import java.util.List;

public interface ReportDao {

    // CREATE
    void add(Report report);
    void addCommunityToReport(int reportId, int communityId);
    void addOrganizationToReport(int reportId, int organizationId);


    // READ
    List<Report> getAll();
    List<Community> getAllCommunities(int reportId);
    List<Organization> getAllOrganizations(int reportId);
    Report findById(int reportId);
    Report findByConfirmationCode (String confirmationCode);

    // UPDATE
    void update(Report report);

    // DELETE
    void deleteById(int reportId);
    void clearAll();

}
