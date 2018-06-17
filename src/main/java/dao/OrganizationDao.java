package dao;

import models.*;

import java.util.List;

public interface OrganizationDao {

    // CREATE
    void add(Organization organization);
    void addOrganizationToService(Organization organization, Service service);
    void addOrganizationToRegion(Organization organization, Region region );
    void addOrganizationToCommunity(Organization organization, Community community);
    void addOrganizationToContact(int organizationId, int contactId);

    // READ
    List<Organization> getAll();
    Organization findById(int id);
    List<Service> getAllServices(int id);
    List<Community> getAllCommunities(int id);
    List<Region> getAllRegions(int id);
    List<Report> getAllReports(int id);
    List<Contact> getAllContacts(int id);

    // UPDATE
    void update(Organization organization);

    // DELETE
    void deleteById(int id);
    void clearAll();
}
