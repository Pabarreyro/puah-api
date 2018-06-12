package dao;

import models.Organization;
import models.Service;
import org.sql2o.*;

import java.util.ArrayList;
import java.util.List;

public class Sql2oServiceDao implements ServiceDao{
    private final Sql2o sql2o;

    public Sql2oServiceDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Service service) {
        String sql = "INSERT INTO services (name) VALUES (:name)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(service)
                    .executeUpdate()
                    .getKey();
            service.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addServiceToOrganization(Service service, Organization organization) {
        String sql = "INSERT INTO organizations_services (organizationId, serviceId) VALUES (:organizationId, :serviceId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("organizationId", organization.getId())
                    .addParameter("serviceId", service.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Service> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM services")
                    .executeAndFetch(Service.class);
        }
    }

    @Override
    public List<Organization> getAllOrganizations(int serviceId) {
        ArrayList<Organization> returnedOrganizations = new ArrayList<>();

        String joinQuery = "SELECT organizationId FROM organizations_services WHERE serviceId = :serviceId";
        try (Connection con = sql2o.open()) {
            List<Integer> organizationIds = con.createQuery(joinQuery)
                    .addParameter("serviceId", serviceId)
                    .executeAndFetch(Integer.class);
            for (Integer organizationId : organizationIds) {
                String organizationQuery = "SELECT * FROM organizations WHERE id =:organizationId";
                returnedOrganizations.add(
                        con.createQuery(organizationQuery)
                                .addParameter("organizationId", organizationId)
                                .executeAndFetchFirst(Organization.class)
                );
            }
        }
        return returnedOrganizations;
    }

    @Override
    public Service findById(int id) {
        String sql = "SELECT * FROM services WHERE id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Service.class);
        }
    }

    @Override
    public void update(int id, String name) {
        String sql = "UPDATE services SET name = :name WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("name", name)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection con = sql2o.open()) {
            con.createQuery("DELETE from services WHERE id = :id")
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "TRUNCATE services";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
