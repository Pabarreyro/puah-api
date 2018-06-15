import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import dao.Sql2oCommunityDao;
import dao.Sql2oOrganizationDao;
import dao.Sql2oRegionDao;
import dao.Sql2oServiceDao;
import exceptions.ApiException;
import models.Community;
import models.Organization;
import models.Region;
import models.Service;
import org.omg.CORBA.Any;
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

        Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:postgresql://localhost:5432/puah";
        Sql2o sql2o = new Sql2o(connectionString, null, null);

        organizationDao = new Sql2oOrganizationDao(sql2o);
        communityDao = new Sql2oCommunityDao(sql2o);
        serviceDao = new Sql2oServiceDao(sql2o);
        regionDao = new Sql2oRegionDao(sql2o);

        conn = sql2o.open();

        // CREATE
        post("/organizations/new", "application/json", (req, res) -> {
            HashMap<String, ArrayList<LinkedTreeMap<String, String>>> requestBody = gson.fromJson(req.body(), HashMap.class);
            LinkedTreeMap<String, String> sentOrganization = requestBody.get("organization").get(0);

            // Parse Organization Out
            String name = sentOrganization.get("name");
            String address = sentOrganization.get("address");
            String zip = sentOrganization.get("zip");
            String phone = sentOrganization.get("phone");
            String website = sentOrganization.get("website");
            String email = sentOrganization.get("email");
            Organization newOrganization = new Organization(name, address, zip, phone, website, email);
            organizationDao.add(newOrganization);

            // Parse Services out
            for (LinkedTreeMap<String, String> service : requestBody.get("services")) {
                int serviceId = Integer.parseInt(service.get("id"));
                Service associatedService = serviceDao.findById(serviceId);
                organizationDao.addOrganizationToService(newOrganization, associatedService);
                System.out.println(serviceDao.getAllOrganizations(serviceId).size());
            }

            // Parse Communities out
            for (LinkedTreeMap<String, String> community : requestBody.get("communities")) {
                int communityId = Integer.parseInt(community.get("id"));
                Community associatedCommunity = communityDao.findById(communityId);
                organizationDao.addOrganizationToCommunity(newOrganization, associatedCommunity);
                System.out.println(communityDao.getAllOrganizations(communityId).size());
            }

            // Parse Regions out
            for (LinkedTreeMap<String, String> region : requestBody.get("regions")) {
                int regionId = Integer.parseInt(region.get("id"));
                Region associatedRegion = regionDao.findById(regionId);
                organizationDao.addOrganizationToRegion(newOrganization, associatedRegion);
                System.out.println(regionDao.getAllOrganizations(regionId).size());
            }

            res.status(201);
            return gson.toJson(newOrganization);
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
            List<Organization> allOrgs = organizationDao.getAll();
            ArrayList<Organization> filteredOrgs = new ArrayList<>();
            String[] serviceIds = {};
            String[] communityIds = {};
            String[] regionIds = {};

            if (req.queryParams("service") != null) {
                serviceIds = req.queryParamsValues("service");
            }
            if (req.queryParams("community") != null) {
                communityIds = req.queryParamsValues("community");
            }
            if (req.queryParams("region") != null) {
                regionIds = req.queryParamsValues("region");
            }

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
            if (filteredOrgs.size() > 0) {
                return gson.toJson(filteredOrgs);
            } else if (allOrgs.size() > 0){
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

        get("/organizations/:id", "application/json", (req, res) -> {
            int organizationId = Integer.parseInt(req.params("id"));

            Organization organizationToFind = organizationDao.findById(organizationId);

            if (organizationToFind == null) {
                throw new ApiException(404, String.format("No organization with the id: \"%s\" exists", req.params("id")));
            }

            return gson.toJson(organizationDao.findById(organizationId));
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
