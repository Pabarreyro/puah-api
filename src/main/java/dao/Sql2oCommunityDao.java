package dao;

import models.Community;
import org.sql2o.*;

import java.util.List;

public class Sql2oCommunityDao implements CommunityDao {
    private final Sql2o sql2o;

    public Sql2oCommunityDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Community community) {
        String sql = "INSERT INTO communities (name, type) VALUES (:name, :type)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(community)
                    .executeUpdate()
                    .getKey();
            community.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Community> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM communities")
                    .executeAndFetch(Community.class);
        }
    }

    @Override
    public Community findById(int id) {
        String sql = "SELECT * FROM communities WHERE id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Community.class);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from communities";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
