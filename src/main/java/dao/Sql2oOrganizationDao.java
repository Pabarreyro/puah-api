package dao;

import org.sql2o.*;

public class Sql2oOrganizationDao implements OrganizationDao {
    private final Sql2o sql2o;

    public Sql2oOrganizationDao(Sql2o sql2o) {
        this.sql2o = sql2o;
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
