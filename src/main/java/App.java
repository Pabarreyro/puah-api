import com.google.gson.Gson;
import dao.Sql2oCommunityDao;
import dao.Sql2oOrganizationDao;
import dao.Sql2oRegionDao;
import dao.Sql2oServiceDao;
import models.Community;
import models.Organization;
import models.Region;
import models.Service;
import org.sql2o.*;

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
            Organization newOrganization = gson.fromJson(req.body(), Organization.class);
            organizationDao.add(newOrganization);
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
           System.out.println(organizationDao.getAll());

           if (organizationDao.getAll().size() > 0) {
               return gson.toJson(organizationDao.getAll());
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

        // UPDATE



        // DELETE



        // FILTER
        after((req, res) ->{
            res.type("application/json");
        });


    }
}
