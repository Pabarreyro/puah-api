package dao;

import models.Contact;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oContactDao implements ContactDao {
    private final Sql2o sql2o;

    public Sql2oContactDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Contact contact) {
        String sql = "INSERT INTO contacts (firstName, lastName, email, phone) VALUES (:firstName, :lastName, :email, :phone)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(contact)
                    .executeUpdate()
                    .getKey();
            contact.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public List<Contact> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM contacts")
                    .executeAndFetch(Contact.class);
        }
    }
    @Override
    public Contact findById(int id) {
        String sql = "SELECT * FROM contacts WHERE id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Contact.class);
        }
    }

    @Override
    public void update(Contact contact) {
        String sql = "UPDATE contacts SET (firstName, lastName, email, phone) = (:firstName, :lastName, :email, :phone)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .bind(contact)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from contacts WHERE id = :id";
        String deleteReportJoin = "DELETE from reports_contacts WHERE contactId = :contactId";
        String deleteOrganizationJoin = "DELETE from organizations_contacts WHERE contactId = :contactId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteReportJoin)
                    .addParameter("contactId", id)
                    .executeUpdate();
            con.createQuery(deleteOrganizationJoin)
                    .addParameter("contactId", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "TRUNCATE contacts";
        String clearReportJoins = "TRUNCATE reports_contacts";
        String clearOrganizationJoins = "TRUNCATE organizations_contacts";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
            con.createQuery(clearReportJoins)
                    .executeUpdate();
            con.createQuery(clearOrganizationJoins)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

}
