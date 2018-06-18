package dao;

import models.*;

import java.util.List;

public interface ReportDao {

    // CREATE
    void add(Report report);
    void addCommunityToReport(int reportId, int communityId);
    void addOrganizationToReport(int reportId, int organizationId);
    void addContactToReport(int reportId, int contactId);


    // READ
    List<Report> getAll();
    List<Community> getAllCommunities(int reportId);
    List<Organization> getAllOrganizations(int reportId);
    List<Contact> getAllContacts(int contactId);
    Report findById(int reportId);
    Report findByConfirmationCode(String confirmationCode);
    Report findByContact(int contactId);

    // UPDATE
    void update(Report report);

    // DELETE
    void deleteById(int reportId);
    void clearAll();

}
