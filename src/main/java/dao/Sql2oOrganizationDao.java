package dao;

import models.*;
import org.sql2o.*;

import java.util.ArrayList;
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
    public void addOrganizationToService(Organization organization, Service service) {
        String sql = "INSERT INTO organizations_services (organizationId, serviceId) VALUES (:organizationId, :serviceId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("organizationId", organization.getId())
                    .addParameter("serviceId", service.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addOrganizationToCommunity(Organization organization, Community community) {
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
    public void addOrganizationToRegion(Organization organization, Region region) {
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
    public void addOrganizationToContact(int organizationId, int contactId) {
        String sql = "INSERT INTO organizations_contacts (organizationId, contactId) VALUES (:organizationId, :contactId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("organizationId", organizationId)
                    .addParameter("contactId", contactId)
                    .executeUpdate();
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
    public List<Service> getAllServices(int organizationId) {
        ArrayList<Service> returnedServices = new ArrayList<>();

        String joinQuery = "SELECT serviceId FROM organizations_services WHERE organizationId = :organizationId";
        try (Connection con = sql2o.open()) {
            List<Integer> serviceIds = con.createQuery(joinQuery)
                    .addParameter("organizationId", organizationId)
                    .executeAndFetch(Integer.class);
            for (Integer serviceId :serviceIds) {
                String serviceQuery = "SELECT * FROM services WHERE id = :serviceId";
                returnedServices.add(
                        con.createQuery(serviceQuery)
                                .addParameter("serviceId", serviceId)
                                .executeAndFetchFirst(Service.class)
                );
            }
        }
        return returnedServices;
    }

    @Override
    public List<Community> getAllCommunities(int organizationId) {
        ArrayList<Community> returnedCommunities = new ArrayList<>();

        String joinQuery = "SELECT communityId FROM organizations_communities WHERE organizationId = :organizationId";
        try (Connection con = sql2o.open()) {
            List<Integer>  communityIds = con.createQuery(joinQuery)
                    .addParameter("organizationId", organizationId)
                    .executeAndFetch(Integer.class);
            for (Integer communityId : communityIds) {
                String communityQuery = "SELECT * FROM communities WHERE id = :communityId";
                returnedCommunities.add(
                  con.createQuery(communityQuery)
                          .addParameter("communityId", communityId)
                          .executeAndFetchFirst(Community.class)
                  );
            }
        }
        return returnedCommunities;
    }

    @Override
    public List<Region> getAllRegions(int organizationId) {
        ArrayList<Region> returnedRegions = new ArrayList<>();

        String joinQuery = "SELECT regionId FROM organizations_regions WHERE organizationId = :organizationId";
        try (Connection con = sql2o.open()) {
            List<Integer> regionIds = con.createQuery(joinQuery)
                    .addParameter("organizationId", organizationId)
                    .executeAndFetch(Integer.class);
            for (Integer regionId : regionIds) {
                String regionQuery = "SELECT * FROM regions WHERE id = :regionId";
                returnedRegions.add(
                        con.createQuery(regionQuery)
                                .addParameter("regionId", regionId)
                                .executeAndFetchFirst(Region.class)
                );
            }
        }
        return returnedRegions;
    }

    @Override
    public List<Report> getAllReports(int organizationId) {
        ArrayList<Report> returnedReports = new ArrayList<>();

        String joinQuery = "SELECT reportId FROM organizations_reports WHERE organizationId = :organizationId";
        try (Connection con = sql2o.open()) {
            List<Integer> reportIds = con.createQuery(joinQuery)
                    .addParameter("organizationId", organizationId)
                    .executeAndFetch(Integer.class);
            for (Integer reportId : reportIds) {
                String reportQuery = "SELECT * FROM reports WHERE id = :reportId";
                returnedReports.add(
                        con.createQuery(reportQuery)
                                .addParameter("reportId", reportId)
                                .executeAndFetchFirst(Report.class)
                );
            }
        }
        return returnedReports;
    }

    @Override
    public List<Contact> getAllContacts(int organizationId) {
        ArrayList<Contact> returnedContacts = new ArrayList<>();

        String joinQuery = "SELECT contactId FROM organizations_contacts WHERE organizationId = :organizationId";
        try (Connection con = sql2o.open()) {
            List<Integer> contactIds = con.createQuery(joinQuery)
                    .addParameter("organizationId", organizationId)
                    .executeAndFetch(Integer.class);
            for (Integer contactId : contactIds) {
                String contactQuery = "SELECT * FROM contacts WHERE id = :contactId";
                returnedContacts.add(
                        con.createQuery(contactQuery)
                                .addParameter("contactId", contactId)
                                .executeAndFetchFirst(Contact.class)
                );
            }
        }
        return returnedContacts;
    }



    @Override
    public Organization findById(int id) {
        String sql = "SELECT * FROM organizations WHERE id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Organization.class);
        }
    }

    @Override
    public void update(Organization organization) {
        String sql = "UPDATE organizations SET (name, address, zip, phone, website, email) = (:name, :address, :zip, :phone, :website, :email) WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .bind(organization)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from organizations WHERE id = :id";
        String deleteServiceJoin = "DELETE from organizations_services WHERE organizationId = :organizationId";
        String deleteCommunityJoin = "DELETE from organizations_communities WHERE organizationId = :organizationId";
        String deleteRegionJoin = "DELETE from organizations_regions WHERE organizationId = :organizationId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteServiceJoin)
                    .addParameter("organizationId", id)
                    .executeUpdate();
            con.createQuery(deleteCommunityJoin)
                    .addParameter("organizationId", id)
                    .executeUpdate();
            con.createQuery(deleteRegionJoin)
                    .addParameter("organizationId", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "TRUNCATE organizations";
        String clearServiceJoins = "TRUNCATE organizations_services";
        String clearRegionJoins = "TRUNCATE organizations_regions";
        String clearCommunityJoins = "TRUNCATE organizations_communities";
        String clearContactJoins = "TRUNCATE organizations_contacts";
        String clearReportJoins = "TRUNCATE organizations_reports";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
            con.createQuery(clearServiceJoins)
                    .executeUpdate();
            con.createQuery(clearRegionJoins)
                    .executeUpdate();
            con.createQuery(clearCommunityJoins)
                    .executeUpdate();
            con.createQuery(clearContactJoins)
                    .executeUpdate();
            con.createQuery(clearReportJoins)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
