package dao;

import models.Community;
import models.Organization;
import models.Service;

import java.util.List;

public interface CommunityDao {

    // CREATE
    void add(Community community);
    void addCommunityToOrganization(Service service, Organization organization);

    // READ
    List<Community> getAll();
    Community findById(int id);
    List<Organization> getAllOrganizations(int id);

    // UPDATE
    void update(int id, String name, String type);

    // DELETE
    void deleteById(int id);
    void clearAll();
}
