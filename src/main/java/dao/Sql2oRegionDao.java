package dao;

import models.Organization;
import models.Region;
import org.sql2o.*;

import java.util.ArrayList;
import java.util.List;

public class Sql2oRegionDao implements RegionDao{
    private final Sql2o sql2o;

    public Sql2oRegionDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Region region) {
        String sql = "INSERT INTO regions (name) VALUES (:name)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(region)
                    .executeUpdate()
                    .getKey();
            region.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addRegionToOrganization(Region region, Organization organization) {
        String sql = "INSERT INTO organizations_regions (organizationId, regionId) VALUES (:organizationId, :regionId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("organizationId", organization.getId())
                    .addParameter("regionId", region.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Region> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM regions")
                    .executeAndFetch(Region.class);
        }
    }

    @Override
    public Region findById(int id) {
        String sql = "SELECT * FROM regions WHERE id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Region.class);
        }
    }

    @Override
    public List<Organization> getAllOrganizations(int regionId) {
        ArrayList<Organization> returnedOrganizations = new ArrayList<>();

        String joinQuery = "SELECT organizationId FROM organizations_regions WHERE regionId = :regionId";
        try (Connection con = sql2o.open()) {
            List<Integer> organizationIds = con.createQuery(joinQuery)
                    .addParameter("regionId", regionId)
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
    public void update(Region region) {
        String sql = "UPDATE regions SET name = :name WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .bind(region)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql= "DELETE from regions WHERE id = :id";
        String deleteOrganizationJoin = "DELETE from organizations_regions WHERE regionId = :regionId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteOrganizationJoin)
                    .addParameter("regionId", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "TRUNCATE regions";
        String clearOrganizationJoins = "TRUNCATE organizations_regions";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
            con.createQuery(clearOrganizationJoins)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
