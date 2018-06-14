package dao;

import models.Community;
import models.Organization;
import models.Service;

import java.util.List;

public interface CommunityDao {

    // CREATE
    void add(Community community);
    void addCommunityToOrganization(Community community, Organization organization);

    // READ
    List<Community> getAll();
    Community findById(int id);
    List<Organization> getAllOrganizations(int serviceId);

    // UPDATE
    void update(Community community);

    // DELETE
    void deleteById(int id);
    void clearAll();
}
