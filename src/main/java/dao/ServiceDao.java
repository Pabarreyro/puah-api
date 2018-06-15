package dao;

import models.Organization;
import models.Service;

import java.util.List;

public interface ServiceDao {

    // CREATE
    void add(Service service);
    void addServiceToOrganization(Service service, Organization organization);

    // READ
    List<Service> getAll();
    Service findById(int id);
    List<Organization> getAllOrganizations(int id);

    // UPDATE
    void update(Service service);

    // DELETE
    void deleteById(int id);
    void clearAll();
}
