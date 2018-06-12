package dao;

import models.Organization;
import org.sql2o.*;

import java.util.List;

public class Sql2oOrganizationDao implements OrganizationDao {
    private final Sql2o sql2o;

    public Sql2oOrganizationDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Organization organization) {
        String sql = "INSERT INTO organizations (name, address, zip, phone, website, email) VALUES (:name, :address, :zip, :phone, :website, :email)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(organization)
                    .executeUpdate()
                    .getKey();
            organization.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Organization> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM organizations")
                    .executeAndFetch(Organization.class);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from organizations";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
