package dao;

import models.Community;
import models.Organization;
import org.sql2o.*;

import java.util.ArrayList;
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
    public void addCommunityToOrganization(Community community, Organization organization) {
        String sql = "INSERT INTO organizations_communities (organizationId, communityId) VALUES (:organizationId, :communityId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("organizationId", organization.getId())
                    .addParameter("communityId", community.getId() )
                    .executeUpdate();
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
    public List<Organization> getAllOrganizations(int communityId) {
        ArrayList<Organization> returnedOrganizations = new ArrayList<>();

        String joinQuery = "SELECT organizationId FROM organizations_communities WHERE communityId = :communityId";
        try (Connection con = sql2o.open()) {
            List<Integer> organizationIds = con.createQuery(joinQuery)
                    .addParameter("communityId", communityId)
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
    public Community findById(int id) {
        String sql = "SELECT * FROM communities WHERE id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Community.class);
        }
    }

    @Override
    public void update(int id, String name, String type) {
        String sql = "UPDATE communities SET (name, type) = (:name, :type) WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("name", name)
                    .addParameter("type", type)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection con = sql2o.open()) {
            con.createQuery("DELETE from communities WHERE id = :id")
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "TRUNCATE communities";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
