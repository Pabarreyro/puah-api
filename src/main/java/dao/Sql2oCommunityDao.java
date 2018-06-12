package dao;

import org.sql2o.*;

public class Sql2oCommunityDao implements CommunityDao {
    private final Sql2o sql2o;

    public Sql2oCommunityDao(Sql2o sql2o) {
        this.sql2o = sql2o;
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
