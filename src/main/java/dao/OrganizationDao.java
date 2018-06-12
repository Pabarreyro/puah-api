package dao;

import models.Community;
import models.Organization;
import models.Region;
import models.Service;

import java.util.List;

public interface OrganizationDao {

    // CREATE
    void add(Organization organization);
    void addOrganizationToService(Organization organization, Service service);
    void addOrganizationToRegion(Organization organization, Region region );
    void addOrganizationToCommunity(Organization organization, Community community);

    // READ
    List<Organization> getAll();
    Organization findById(int id);
    List<Service> getAllServices(int id);
    List<Community> getAllCommunities(int id);
    List<Region> getAllRegions(int id);

    // UPDATE
    void update(int id, String name, String address, String zip, String phone, String website, String email);

    // DELETE
    void deleteById(int id);
    void clearAll();
}
