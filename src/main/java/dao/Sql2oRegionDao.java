package dao;

import models.Region;
import org.sql2o.*;

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
    public void update(int id, String name) {
        String sql = "UPDATE regions SET name = :name WHERE id = :id";
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
            con.createQuery("DELETE from regions WHERE id = :id ")
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from regions";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
