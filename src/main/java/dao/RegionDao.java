package dao;

import models.Organization;
import models.Region;

import java.util.List;

public interface RegionDao {

    // CREATE
    void add(Region region);
    void addRegionToOrganization(Region region, Organization organization);

    // READ
    List<Region> getAll();
    Region findById(int id);
    List<Organization> getAllOrganizations(int id);

    // UPDATE
    void update(int id, String name);

    // DELETE
    void deleteById(int id);
    void clearAll();
}
