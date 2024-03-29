package dao;

import models.Organization;
import models.Service;
import org.junit.*;
import org.sql2o.*;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Sql2oServiceDaoTest {
    private static Connection conn;
    private static Sql2oOrganizationDao organizationDao;
    private static Sql2oServiceDao serviceDao;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/puah_test";
        Sql2o sql2o = new Sql2o(connectionString, null, null);
        organizationDao = new Sql2oOrganizationDao(sql2o);
        serviceDao = new Sql2oServiceDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        organizationDao.clearAll();
        serviceDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception {
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void add_setsId() throws Exception {
        Service testService = setUpService();
        assertNotEquals(null, testService.getId());
    }

    @Test
    public void getAll_returnsAllExistingServices() throws Exception {
        Service testService = setUpService();
        assertEquals(1, serviceDao.getAll().size());
        assertEquals(testService, serviceDao.getAll().get(0));
    }

    @Test
    public void getAll_returnsEmptyListIfNoServicesExist() throws Exception {
        assertEquals(0, serviceDao.getAll().size());
    }

    @Test
    public void getAllOrganizations_returnsAllAssociatedOrganizations() {
        Organization testOrganization = setUpOrganization();
        Organization altTestOrganization = setUpAltOrganization();

        Service testService = setUpService();
        serviceDao.addServiceToOrganization(testService, testOrganization);
        serviceDao.addServiceToOrganization(testService, altTestOrganization);

        Organization[] organizations = {testOrganization, altTestOrganization};
        assertEquals(Arrays.asList(organizations), serviceDao.getAllOrganizations(testService.getId()));
    }

    @Test
    public void findById_returnsCorrectService() throws Exception {
        Service testService = setUpService();
        Service altTestService = setUpAltService();
        assertEquals(testService, serviceDao.findById(testService.getId()));
    }

    @Test
    public void update_correctlyUpdatesService() throws Exception {
        Service testService = setUpService();
        Service altTestService = new Service("Support");
        altTestService.setId(testService.getId());
        serviceDao.update(altTestService);
        Service updatedService = serviceDao.findById(testService.getId());
        assertEquals(altTestService.getName(), updatedService.getName());
    }

    @Test
    public void deleteById_deletesCorrectService() throws Exception {
        Service testService = setUpService();
        Service altTestService = setUpAltService();
        serviceDao.deleteById(altTestService.getId());
        assertEquals(testService, serviceDao.getAll().get(0));
        assertEquals(1, serviceDao.getAll().size());
    }

    @Test
    public void deleteById_deletesAllAssociatedJoins() throws Exception {
        Organization testOrganization = setUpOrganization();

        Service testService = setUpService();
        int serviceId = testService.getId();

        serviceDao.addServiceToOrganization(testService, testOrganization);
        serviceDao.deleteById(serviceId);
        assertEquals(0, serviceDao.getAllOrganizations(serviceId).size());
    }

    @Test
    public void clearAll_deletesAllExistingServices() throws Exception {
        Service testService = setUpService();
        Service altTestService = setUpAltService();
        serviceDao.clearAll();
        assertEquals(0, serviceDao.getAll().size());
    }

    // Helpers
    public Service setUpService() {
        Service newService = new Service ("Advocacy");
        serviceDao.add(newService);
        return newService;
    }

    public Service setUpAltService() {
        Service newService = new Service ("Health Services");
        serviceDao.add(newService);
        return newService;
    }

    public Organization setUpOrganization() {
        Organization newOrganization = new Organization("CCC", "201 NW 2nd Ave", "97201", "503-260-5690", "www.ccc.org", "info@ccc.org");
        organizationDao.add(newOrganization);
        return newOrganization;
    }

    public Organization setUpAltOrganization() {
        Organization newOrganization = new Organization("Coalition of Communities of Color", "211 NW 3rd Ave", "97202", "517-286-5722");
        organizationDao.add(newOrganization);
        return newOrganization;
    }
}