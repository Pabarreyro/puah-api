package dao;

import models.Service;
import org.sql2o.*;

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
    public List<Service> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM services")
                    .executeAndFetch(Service.class);
        }
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
    public void clearAll() {
        String sql = "DELETE from services";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
