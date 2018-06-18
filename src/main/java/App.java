import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import dao.*;
import exceptions.ApiException;
import models.*;
import org.sql2o.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        Sql2oOrganizationDao organizationDao;
        Sql2oCommunityDao communityDao;
        Sql2oServiceDao serviceDao;
        Sql2oRegionDao regionDao;
        Sql2oReportDao reportDao;
        Sql2oContactDao contactDao;

        Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:postgresql://localhost:5432/puah";
        Sql2o sql2o = new Sql2o(connectionString, null, null);

        organizationDao = new Sql2oOrganizationDao(sql2o);
        communityDao = new Sql2oCommunityDao(sql2o);
        serviceDao = new Sql2oServiceDao(sql2o);
        regionDao = new Sql2oRegionDao(sql2o);
        reportDao = new Sql2oReportDao(sql2o);
        contactDao = new Sql2oContactDao(sql2o);

        conn = sql2o.open();

        // CREATE
        post("/organizations/new", "application/json", (req, res) -> {

            // Cast request object to HashMap
            HashMap<String, ArrayList<LinkedTreeMap<String, String>>> requestBody = gson.fromJson(req.body(), HashMap.class);

            // Parse organization
            LinkedTreeMap<String, String> sentOrganization = requestBody.get("organization").get(0);
            String name = sentOrganization.get("name");
            String address = sentOrganization.get("address");
            String zip = sentOrganization.get("zip");
            String phone = sentOrganization.get("phone");
            String website = sentOrganization.get("website");
            String email = sentOrganization.get("email");
            Organization newOrganization = new Organization(name, address, zip, phone, website, email);
            organizationDao.add(newOrganization);

            // Parse services and add join table row(s)
            for (LinkedTreeMap<String, String> service : requestBody.get("services")) {
                int serviceId = Integer.parseInt(service.get("id"));
                Service associatedService = serviceDao.findById(serviceId);
                organizationDao.addOrganizationToService(newOrganization, associatedService);
            }

            // Parse communities and add join table row(s)
            for (LinkedTreeMap<String, String> community : requestBody.get("communities")) {
                int communityId = Integer.parseInt(community.get("id"));
                Community associatedCommunity = communityDao.findById(communityId);
                organizationDao.addOrganizationToCommunity(newOrganization, associatedCommunity);
            }

            // Parse regions and add join table row(s)
            for (LinkedTreeMap<String, String> region : requestBody.get("regions")) {
                int regionId = Integer.parseInt(region.get("id"));
                Region associatedRegion = regionDao.findById(regionId);
                organizationDao.addOrganizationToRegion(newOrganization, associatedRegion);
            }

            res.status(201);
            return gson.toJson(newOrganization);
        });

        post("/reports/new", "application/json", (req, res) -> {

            // Cast request object to HashMap
            HashMap<String, ArrayList<LinkedTreeMap<String, String>>> requestBody = gson.fromJson(req.body(), HashMap.class);

            // Parse report
            LinkedTreeMap<String, String> sentReport = requestBody.get("report").get(0);

            String type = sentReport.get("type");
            String reporterRole = sentReport.get("reporterRole");
            int reporterAge = Integer.parseInt(sentReport.get("reporterAge"));
            String reporterLocation = sentReport.get("reporterLocation");
            String incidentDate = sentReport.get("incidentDate");
            String incidentTime = sentReport.get("incidentTime");
            String incidentCrossStreets = sentReport.get("incidentCrossStreets");
            String incidentSetting = sentReport.get("incidentSetting");
            String incidentType = sentReport.get("incidentType");
            String incidentTypeNotes = sentReport.get("incidentTypeNotes");
            String incidentMotivation = sentReport.get("incidentMotivation");
            String incidentMotivationNotes = sentReport.get("incidentMotivationNotes");
            boolean injuryOccurred = Boolean.parseBoolean(sentReport.get("injuryOccurred"));
            String injuryNotes = sentReport.get("injuryNotes");
            boolean damagesOccurred = Boolean.parseBoolean(sentReport.get("damagesOccurred"));
            String damagesNotes = sentReport.get("damagesNotes");
            boolean officiallyReported = Boolean.parseBoolean(sentReport.get("officiallyReported"));
            String officialReportNotes = sentReport.get("officialReportNotes");
            String additionalNotes = sentReport.get("additionalNotes");

            Report newReport = new Report(type, reporterRole, reporterAge, reporterLocation, incidentDate, incidentTime, incidentCrossStreets, incidentSetting, incidentType, incidentTypeNotes,  incidentMotivation, incidentMotivationNotes, injuryOccurred, injuryNotes, damagesOccurred, damagesNotes, officiallyReported, officialReportNotes, additionalNotes);
            reportDao.add(newReport);
            int reportId = newReport.getId();
            System.out.println(newReport.getDateTimeFiled());
            System.out.println(newReport.getFormattedDateTime());

            // Parse contact and add join table row(s)
            LinkedTreeMap<String, String> sentContact = requestBody.get("contact").get(0);
            String firstName = sentContact.get("firstName");
            String lastName = sentContact.get("lastName");
            String email = sentContact.get("email");
            String phone = sentContact.get("phone");

            Contact newContact = new Contact(firstName, lastName, email, phone);
            contactDao.add(newContact);
            int contactId = newContact.getId();
            reportDao.addContactToReport(reportId, contactId);
            System.out.println(contactDao.getAll().size());

            // Parse communities and add join table row(s)
            for (LinkedTreeMap<String, String> community : requestBody.get("communities")) {
                int communityId = Integer.parseInt(community.get("id"));
                reportDao.addCommunityToReport(reportId, communityId);
                System.out.println(reportDao.getAllCommunities(reportId).size());
            }

            // Parse organizations and add join table row(s)
            for (LinkedTreeMap<String, String> organization : requestBody.get("organizations")) {
                int organizationId = Integer.parseInt(organization.get("id"));
                reportDao.addOrganizationToReport(reportId, organizationId);
                System.out.println(reportDao.getAllOrganizations(reportId).size());
            }

            res.status(201);
            return gson.toJson(String.format("{\"confirmationNumber\":\"%s\"}", newReport.getConfirmationCode()));
        });

        post("/services/new", "application/json", (req, res) -> {
           Service newService = gson.fromJson(req.body(), Service.class);
           serviceDao.add(newService);
           res.status(201);
           return gson.toJson(newService);
        });

        post("/communities/new", "application/json", (req, res) -> {
            Community newCommunity = gson.fromJson(req.body(), Community.class);
            communityDao.add(newCommunity);
            res.status(201);
            return gson.toJson(newCommunity);
        });

        post("/regions/new", "application/json", (req, res) -> {
            Region newRegion = gson.fromJson(req.body(), Region.class);
            regionDao.add(newRegion);
            res.status(201);
            return gson.toJson(newRegion);
        });

        // READ
        get("/organizations", "application/json", (req, res) -> {
            // Retrieve all orgs and instantiate ArrayList for holding filtered orgs
            List<Organization> allOrgs = organizationDao.getAll();
            ArrayList<Organization> filteredOrgs = new ArrayList<>();

            // Instantiate Arrays for holding any query params
            String[] serviceIds = {};
            String[] communityIds = {};
            String[] regionIds = {};

            // Grab query params
            if (req.queryParams("service") != null) {
                serviceIds = req.queryParamsValues("service");
            }
            if (req.queryParams("community") != null) {
                communityIds = req.queryParamsValues("community");
            }
            if (req.queryParams("region") != null) {
                regionIds = req.queryParamsValues("region");
            }

            // Add organizations that match ANY query to filtered orgs ArrayList
            if (serviceIds.length > 0 ) {
                for (String serviceId : serviceIds) {
                    int queryId = Integer.parseInt(serviceId);

                    for (Organization org : serviceDao.getAllOrganizations(queryId)) {
                        if (!filteredOrgs.contains(org)) {
                            filteredOrgs.add(org);
                        }
                    }
                }
            }
            if (communityIds.length > 0) {
                for (String communityId : communityIds) {
                    int queryId = Integer.parseInt(communityId);

                    for (Organization org : communityDao.getAllOrganizations(queryId)) {
                        if (!filteredOrgs.contains(org)) {
                            filteredOrgs.add(org);
                        }
                    }
                }
            }
            if (regionIds.length > 0) {
                for (String regionId : regionIds) {
                    int queryId = Integer.parseInt(regionId);

                    for (Organization org : regionDao.getAllOrganizations(queryId)) {
                        if (!filteredOrgs.contains(org)) {
                            filteredOrgs.add(org);
                        }
                    }
                }
            }

            // Add associated services, communities and regions and return either all orgs or filtered orgs
            if (filteredOrgs.size() > 0) {
                for (Organization org : filteredOrgs){
                    int orgId = org.getId();
                    org.setServices(organizationDao.getAllServices(orgId));
                    org.setCommunities(organizationDao.getAllCommunities(orgId));
                    org.setRegions(organizationDao.getAllRegions(orgId));
                }
                return gson.toJson(filteredOrgs);
            } else if (allOrgs.size() > 0){
                for (Organization org : allOrgs){
                    int orgId = org.getId();
                    org.setServices(organizationDao.getAllServices(orgId));
                    org.setCommunities(organizationDao.getAllCommunities(orgId));
                    org.setRegions(organizationDao.getAllRegions(orgId));
                }
                return gson.toJson(allOrgs);
            } else {
                return "{\"message\":\"I'm sorry, but no organizations are currently listed in the database.\"}";
            }
        });

        get("/communities", "application/json", (req, res) -> {
            System.out.println(communityDao.getAll());

            if (communityDao.getAll().size() > 0) {
                return gson.toJson(communityDao.getAll());
            } else {
                return "{\"message\":\"I'm sorry, but no communities are currently listed in the database.\"}";
            }
        });

        get("/services", "application/json", (req, res) -> {
            System.out.println(serviceDao.getAll());

            if (serviceDao.getAll().size() > 0) {
                return gson.toJson(serviceDao.getAll());
            } else {
                return "{\"message\":\"I'm sorry, but no services are currently listed in the database.\"}";
            }
        });

        get("/regions", "application/json", (req, res) -> {
            System.out.println(regionDao.getAll());

            if (regionDao.getAll().size() > 0) {
                return gson.toJson(regionDao.getAll());
            } else {
                return "{\"message\":\"I'm sorry, but no regions are currently listed in the database.\"}";
            }
        });

        get("/reports", "application/json", (req, res) -> {
            List<Report> allReports = reportDao.getAll();
            if (allReports.size() > 0) {
                for (Report report : allReports) {
                    int reportId = report.getId();
                    report.setIdentifies(reportDao.getAllCommunities(reportId));
                    report.setOrganizations(reportDao.getAllOrganizations(reportId));
                    report.setContacts(reportDao.getAllContacts(reportId));
                }
                return gson.toJson(allReports);
            } else {
                return "{\"message\":\"I'm sorry, but no reports are currently listed in the database.\"}";
            }
        });

        get("/contacts", "application/json", (req, res) -> {
            if (contactDao.getAll().size() > 0) {
                return gson.toJson(contactDao.getAll());
            } else {
                return "{\"message\":\"I'm sorry, but no contacts are currently listed in the database.\"}";
            }
        });

        get("/organizations/:id", "application/json", (req, res) -> {
            int organizationId = Integer.parseInt(req.params("id"));

            Organization organizationToFind = organizationDao.findById(organizationId);

            if (organizationToFind == null) {
                throw new ApiException(404, String.format("No organization with the id: \"%s\" exists", req.params("id")));
            }

            organizationToFind.setCommunities(organizationDao.getAllCommunities(organizationId));
            organizationToFind.setServices(organizationDao.getAllServices(organizationId));
            organizationToFind.setRegions(organizationDao.getAllRegions(organizationId));

            return gson.toJson(organizationToFind);
        });

        get("/organizations/:id/reports", "application/json", (req, res) -> {
            int organizationId = Integer.parseInt(req.params("id"));

            if (organizationDao.findById(organizationId) == null) {
                throw new ApiException(404, String.format("No organization with the id: \"%s\" exists", req.params("id")));
            }

            if (organizationDao.getAllReports(organizationId).size() > 0) {
                return gson.toJson(organizationDao.getAllReports(organizationId));
            } else {
                return "{\"message\":\"I'm sorry, but no reports are currently listed in the database.\"}";
            }
        });

        get("/organizations/:id/contacts", "application/json", (req, res) -> {
            int organizationId = Integer.parseInt(req.params("id"));

            if (organizationDao.findById(organizationId) == null) {
                throw new ApiException(404, String.format("No organization with the id: \"%s\" exists", req.params("id")));
            }

            if (organizationDao.getAllContacts(organizationId).size() > 0) {
                return gson.toJson(organizationDao.getAllContacts(organizationId));
            } else {
                return "{\"message\":\"I'm sorry, but no contacts are currently listed in the database.\"}";
            }
        });

        get("/services/:id", "application/json", (req, res) -> {
            int serviceId = Integer.parseInt(req.params("id"));


            Service serviceToFind = serviceDao.findById(serviceId);

            if (serviceToFind == null) {
                throw new ApiException(404, String.format("No organization with the id: \"%s\" exists", req.params("id")));
            }

            return gson.toJson(serviceDao.findById(serviceId));
        });

        get("/communities/:id", "application/json", (req, res) -> {
            int communityId = Integer.parseInt(req.params("id"));

            Community communityToFind = communityDao.findById(communityId);

            if (communityToFind == null) {
                throw new ApiException(404, String.format("No community with the id: \"%s\" exists", req.params("id")));
            }

            return gson.toJson(communityDao.findById(communityId));
        });

        get("/regions/:id", "application/json", (req, res) -> {
            int regionId = Integer.parseInt(req.params("id"));

            Region regionToFind = regionDao.findById(regionId);

            if (regionToFind == null) {
                throw new ApiException(404, String.format("No region with the id: \"%s\" exists", req.params("id")));
            }

            return gson.toJson(regionDao.findById(regionId));
        });

        get("/reports/:id", "application/json", (req, res) -> {
            int reportId = Integer.parseInt(req.params("id"));

            Report reportToFind = reportDao.findById(reportId);

            if (reportToFind == null) {
                throw new ApiException(404, String.format("No report with the id: \"%s\" exists", req.params("id")));
            }

            return gson.toJson(reportDao.findById(reportId));
        });

        get("/contacts/:id", "application/json", (req, res) -> {
            int contactId = Integer.parseInt(req.params("id"));

            Contact contactToFind = contactDao.findById(contactId);

            if (contactToFind == null) {
                throw new ApiException(404, String.format("No contact with the id: \"%s\" exists", req.params("id")));
            }

            return gson.toJson(contactDao.findById(contactId));
        });

        // UPDATE
        post("/organizations/:id/update", "application/json", (req, res) ->{
            Organization updatedOrganization = gson.fromJson(req.body(), Organization.class);
            organizationDao.update(updatedOrganization);
            res.status(201);
            return gson.toJson(updatedOrganization);
        });

        post("/communities/:id/update", "application/json", (req, res) ->{
            Community updatedCommunity = gson.fromJson(req.body(), Community.class);
            communityDao.update(updatedCommunity);
            res.status(201);
            return gson.toJson(updatedCommunity);
         });

        post("/regions/:id/update", "application/json", (req, res) ->{
            Region updatedRegion= gson.fromJson(req.body(), Region.class);
            regionDao.update(updatedRegion);
            res.status(201);
            return gson.toJson(updatedRegion);
        });

        post("/services/:id/update", "application/json", (req, res) ->{
            Region updatedRegion= gson.fromJson(req.body(), Region.class);
            regionDao.update(updatedRegion);
            res.status(201);
            return gson.toJson(updatedRegion);
        });

        post("/reports/:id/update", "application/json", (req, res) -> {
            Report updatedReport= gson.fromJson(req.body(), Report.class);
            reportDao.update(updatedReport);
            res.status(201);
            return gson.toJson(updatedReport);
        });

        post("/contacts/:id/update", "application/json", (req, res) -> {
            Contact updatedContact= gson.fromJson(req.body(), Contact.class);
            contactDao.update(updatedContact);
            res.status(201);
            return gson.toJson(updatedContact);
        });

        // DELETE
        post("/organizations/:id/delete", "application/json", (req, res) -> {
            int organizationId = Integer.parseInt(req.params("id"));
            String organizationName = organizationDao.findById(organizationId).getName();
            organizationDao.deleteById(organizationId);
            res.status(200);
            return String.format("{\"message\": \" %s has been removed from your organizations.\"}", organizationName);
        });

        post("/communities/:id/delete", "application/json", (req, res) -> {
            int communityId = Integer.parseInt(req.params("id"));
            String communityName = communityDao.findById(communityId).getName();
            communityDao.deleteById(communityId);
            res.status(200);
            return String.format("{\"message\": \" %s has been removed from your communities.\"}", communityName);
        });

        post("/services/:id/delete", "application/json", (req, res) -> {
            int serviceId = Integer.parseInt(req.params("id"));
            String serviceName = serviceDao.findById(serviceId).getName();
            serviceDao.deleteById(serviceId);
            res.status(200);
            return String.format("{\"message\": \" %s has been removed from your services.\"}", serviceName);
        });

        post("/regions/:id/delete", "application/json", (req, res) -> {
            int regionId = Integer.parseInt(req.params("id"));
            String regionName = regionDao.findById(regionId).getName();
            regionDao.deleteById(regionId);
            res.status(200);
            return String.format("{\"message\": \" %s has been removed from your regions.\"}", regionName);
        });

        post("/reports/:id/delete", "application/json", (req, res) -> {
            int reportId = Integer.parseInt(req.params("id"));
            String reportConfirmationCode = reportDao.findById(reportId).getConfirmationCode();
            reportDao.deleteById(reportId);
            res.status(200);
            return String.format("{\"message\": \" %s has been removed from your reports.\"}", reportConfirmationCode);
        });

        post("/contacts/:id/delete", "application/json", (req, res) -> {
            int contactId = Integer.parseInt(req.params("id"));
            String firstName = contactDao.findById(contactId).getFirstName();
            String lastName = contactDao.findById(contactId).getLastName();
            contactDao.deleteById(contactId);
            res.status(200);
            return String.format("{\"message\": \" %s %s has been removed from your contacts.\"}", firstName, lastName);
        });

        // FILTER
        after((req, res) ->{
            res.type("application/json");
        });

        exception(ApiException.class, (exception, req, res) -> {
            ApiException err = (ApiException) exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatusCode());
            res.body(gson.toJson(jsonMap));
        });
    }
}
