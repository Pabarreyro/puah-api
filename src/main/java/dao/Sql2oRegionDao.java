package dao;

import org.sql2o.*;

public class Sql2oRegionDao implements RegionDao{
    private final Sql2o sql2o;

    public Sql2oRegionDao(Sql2o sql2o) {
        this.sql2o = sql2o;
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
