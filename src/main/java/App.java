import com.google.gson.Gson;
import dao.Sql2oCommunityDao;
import dao.Sql2oOrganizationDao;
import dao.Sql2oRegionDao;
import dao.Sql2oServiceDao;
import models.Organization;
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


        // READ



        // UPDATE



        // DELETE



        // FILTER
        after((req, res) ->{
            res.type("application/json");
        });


    }
}
