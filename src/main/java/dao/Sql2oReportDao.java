package dao;

import models.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oReportDao implements ReportDao {
    private final Sql2o sql2o;

    public Sql2oReportDao(Sql2o sql2o) { this.sql2o = sql2o; }
    
    @Override
    public void add(Report report) {
        String sql = "INSERT INTO reports (reporterRole, reporterAge, reporterLocation, incidentDate, incidentTime, incidentCrossStreets, incidentSetting, incidentType, incidentTypeNotes, incidentMotivation, incidentMotivationNotes, injuryOccurred, injuryNotes, damagesOccurred, damagesNotes, officiallyReported, officialReportNotes, additionalNotes) VALUES (:reporterRole, :reporterAge, :reporterLocation, :incidentDate, :incidentTime, :incidentCrossStreets, :incidentSetting, :incidentType, :incidentTypeNotes, :incidentMotivation, :incidentMotivationNotes, :injuryOccurred, :injuryNotes, :damagesOccurred, :damagesNotes, :officiallyReported, :officialReportNotes, :additionalNotes)";
        String confirmationCode = "UPDATE reports SET confirmationCode = :confirmationCode WHERE id = :id";
        try (Connection con= sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(report)
                    .executeUpdate()
                    .getKey();
            report.setId(id);
            report.createConfirmationCode(id);
            con.createQuery(confirmationCode)
                    .addParameter("confirmationCode", report.getConfirmationCode())
                    .addParameter("id", report.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addOrganizationToReport(int reportId, int organizationId) {
        String sql = "INSERT INTO reports_organizations (reportId, organizationId) VALUES (:reportId, :organizationId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("organizationId", organizationId)
                    .addParameter("reportId", reportId)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addCommunityToReport(int reportId, int communityId) {
        String sql = "INSERT INTO reports_communities (reportId, communityId) VALUES (:reportId, :communityId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("communityId", communityId)
                    .addParameter("reportId", reportId)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Report> getAll() {
        String sql = "SELECT * FROM reports";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(Report.class);
        }
    }

    @Override
    public List<Community> getAllCommunities(int reportId) {
        ArrayList<Community> returnedCommunities = new ArrayList<>();

        String joinQuery = "SELECT communityId FROM reports_communities WHERE reportId = :reportId";
        try (Connection con = sql2o.open()) {
            List<Integer> communityIds = con.createQuery(joinQuery)
                    .addParameter("reportId", reportId)
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
    public List<Organization> getAllOrganizations(int reportId) {
        ArrayList<Organization> returnedOrganizations = new ArrayList<>();

        String joinQuery = "SELECT organizationId FROM reports_organizations WHERE reportId = :reportId";
        try (Connection con = sql2o.open()) {
            List<Integer> organizationIds = con.createQuery(joinQuery)
                    .addParameter("reportId", reportId)
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
    public Report findById(int reportId) {
        String sql = "SELECT * FROM reports WHERE id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", reportId)
                    .executeAndFetchFirst(Report.class);
        }
    }

    @Override
    public Report findByConfirmationCode(String confirmationCode) {
        String sql = "SELECT * FROM reports WHERE confirmationCode = :confirmationCode";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("confirmationCode", confirmationCode)
                    .executeAndFetchFirst(Report.class);
        }
    }

    @Override
    public void update(Report report) {
        String sql = "UPDATE reports SET (confirmationCode, reporterRole, reporterAge, reporterLocation, incidentDate, incidentTime, incidentCrossStreets, incidentSetting, incidentType, incidentTypeNotes, incidentMotivation, incidentMotivationNotes, injuryOccurred, injuryNotes, damagesOccurred, damagesNotes, officiallyReported, officialReportNotes, additionalNotes) = (:confirmationCode, :reporterRole, :reporterAge, :reporterLocation, :incidentDate, :incidentTime, :incidentCrossStreets, :incidentSetting, :incidentType, :incidentTypeNotes, :incidentMotivation, :incidentMotivationNotes, :injuryOccurred, :injuryNotes, :damagesOccurred, :damagesNotes, :officiallyReported, :officialReportNotes, :additionalNotes) WHERE id =:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .bind(report)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from reports WHERE id = :id";
        String deleteCommunityJoin = "DELETE from reports_communities WHERE reportId = :reportId";
        String deleteOrganizationJoin = "DELETE from reports_organizations WHERE reportId = :reportId";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql).addParameter("id", id).executeUpdate();
            con.createQuery(deleteCommunityJoin).addParameter("reportId", id ).executeUpdate();
            con.createQuery(deleteOrganizationJoin).addParameter("reportId", id ).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "TRUNCATE reports";
        String clearCommunityJoins = "TRUNCATE reports_communities";
        String clearOrganizationJoins = "TRUNCATE reports_organizations";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
            con.createQuery(clearCommunityJoins)
                    .executeUpdate();
            con.createQuery(clearOrganizationJoins)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
