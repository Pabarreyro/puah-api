package dao;

import models.Community;

import java.util.List;

public interface CommunityDao {

    // CREATE
    void add(Community community);
//    void addCommunityToOrganization(Service service, Organization organization);

    // READ
    List<Community> getAll();
    Community findById(int id);
//    List<Community> getAllByOrganizationId(int organizationId);

    // UPDATE
    void update(String name, String type);

    // DELETE
    void deleteById(int id);
    void clearAll();
}
