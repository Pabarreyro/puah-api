import com.google.gson.Gson;
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

        String connectionString = "jdbc:postgresql://localhost:5432/resources";
        Sql2o sql2o = new Sql2o(connectionString, null, null);

        organizationDao = new Sql2oOrganizationDao(sql2o);
        communityDao = new Sql2oCommunityDao(sql2o);
        serviceDao = new Sql2oServiceDao(sql2o);
        regionDao = new Sql2oRegionDao(sql2o);

        conn = sql2o.open();

        // CREATE
        post("/organizations/new", "application/json", (req, res) -> {
            HashMap<String, Object[]> requestBody = gson.fromJson(req.body(), HashMap.class);

            // Parse Organization Out

            // Parse Services[] out

            // Parse Communities[] out

            gson.fromJson(req.body(), Organization.class);
            res.status(201);
            return gson.toJson(requestBody);
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
            String[] serviceIds = req.queryParamsValues("service");
            String[] communityIds = req.queryParamsValues("community");
            String[] regionIds = req.queryParamsValues("region");

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
            organizationDao.update();
        });

        post("/communities/:id/update", "application/json", (req, res) ->{

        });

        post("/regions/:id/update", "application/json", (req, res) ->{

        });

        post("/services/:id/update", "application/json", (req, res) ->{

        });



        // DELETE



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

    // Helper
    public static ArrayList<Integer> parseQuery(String query) {
        ArrayList<Integer> queryValues = new ArrayList<>();
        String[] parsedQuery = query.split(",");
        for (String queryValue : parsedQuery) {
            int serviceId = Integer.parseInt(queryValue);
            queryValues.add(serviceId);
        }
        return queryValues;
    }
}
