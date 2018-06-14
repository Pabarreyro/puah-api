package dao;

import models.Community;
import models.Organization;
import models.Region;
import models.Service;
import org.junit.*;
import org.sql2o.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class Sql2oOrganizationDaoTest {
    private static Connection conn;
    private static Sql2oOrganizationDao organizationDao;
    private static Sql2oServiceDao serviceDao;
    private static Sql2oCommunityDao communityDao;
    private static Sql2oRegionDao regionDao;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/puah_test";
        Sql2o sql2o = new Sql2o(connectionString, null, null);
        organizationDao = new Sql2oOrganizationDao(sql2o);
        serviceDao = new Sql2oServiceDao(sql2o);
        communityDao = new Sql2oCommunityDao(sql2o);
        regionDao = new Sql2oRegionDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        organizationDao.clearAll();
        serviceDao.clearAll();
        communityDao.clearAll();
        regionDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception {
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void add_setsId() throws Exception {
        Organization testOrganization = setUpOrganization();
        assertNotEquals(null, testOrganization.getId());
    }

    @Test
    public void getAll_returnsAllExistingOrganizations() throws Exception {
        Organization testOrganization = setUpOrganization();
        assertEquals(1, organizationDao.getAll().size());
        assertEquals(testOrganization, organizationDao.getAll().get(0));
    }

    @Test
    public void getAll_returnsEmptyListIfNoOrganizationsExist() throws Exception {
        assertEquals(0, organizationDao.getAll().size());
    }

    @Test
    public void getAllServices_returnsAllAssociatedServices() {
        Service testService = setUpService();
        Service altTestService = setUpAltService();

        Organization testOrganization = setUpOrganization();
        organizationDao.addOrganizationToService(testOrganization, testService);
        organizationDao.addOrganizationToService(testOrganization, altTestService);

        Service[] services = {testService, altTestService};
        assertEquals(Arrays.asList(services), organizationDao.getAllServices(testOrganization.getId()));
    }

    @Test
    public void getAllCommunities_returnsAllAssociatedCommunities() {
        Community testCommunity = setUpCommunity();
        Community altTestCommunity = setUpAltCommunity();

        Organization testOrganization = setUpOrganization();
        organizationDao.addOrganizationToCommunity(testOrganization, testCommunity);
        organizationDao.addOrganizationToCommunity(testOrganization, altTestCommunity);

        Community[] communities = {testCommunity, altTestCommunity};
        assertEquals(Arrays.asList(communities), organizationDao.getAllCommunities(testOrganization.getId()));
    }

    @Test
    public void getAllRegions_returnsAllAssociatedRegions() {
        Region testRegion = setUpRegion();
        Region altTestRegion = setUpAltRegion();

        Organization testOrganization = setUpOrganization();
        organizationDao.addOrganizationToRegion(testOrganization, testRegion);
        organizationDao.addOrganizationToRegion(testOrganization, altTestRegion);

        Region[] regions = {testRegion, altTestRegion};
        assertEquals(Arrays.asList(regions), organizationDao.getAllRegions(testOrganization.getId()));
    }

    @Test
    public void findById_returnsCorrectOrganization() throws Exception {
        Organization testOrganization = setUpOrganization();
        Organization altTestOrganization = setUpAltOrganization();
        assertEquals(testOrganization, organizationDao.findById(testOrganization.getId()));
    }

    @Test
    public void update_correctlyUpdatesOrganization() throws Exception {
        Organization testOrganization = setUpOrganization();
        Organization testUpdatedOrganization = new Organization("Coalition of Communities of Color", "211 NW 3rd Ave", "97202", "517-286-5722");
        testUpdatedOrganization.setId(testOrganization.getId());
        organizationDao.update(testUpdatedOrganization);
        Organization newOrganization = organizationDao.findById(testUpdatedOrganization.getId());
        assertEquals(testUpdatedOrganization.getName(), newOrganization.getName());
        assertEquals(testUpdatedOrganization.getAddress(), newOrganization.getAddress());
        assertEquals(testUpdatedOrganization.getZip(), newOrganization.getZip());
        assertEquals(testUpdatedOrganization.getPhone(), newOrganization.getPhone());
        assertEquals(testUpdatedOrganization.getWebsite(), newOrganization.getWebsite());
        assertEquals(testUpdatedOrganization.getEmail(), newOrganization.getEmail());
    }

    @Test
    public void deleteById_deletesCorrectOrganization() throws Exception {
        Organization testOrganization = setUpOrganization();
        Organization altTestOrganization = setUpAltOrganization();
        organizationDao.deleteById(altTestOrganization.getId());
        assertEquals(testOrganization, organizationDao.getAll().get(0));
        assertEquals(1, organizationDao.getAll().size());
    }

    @Test
    public void deleteById_deletesAllAssociatedJoins() throws Exception {
        Organization testOrganization = setUpOrganization();
        int organizationId = testOrganization.getId();
        Service testService = setUpService();
        Community testCommunity = setUpCommunity();
        Region testRegion = setUpRegion();

        organizationDao.addOrganizationToCommunity(testOrganization, testCommunity);
        organizationDao.addOrganizationToService(testOrganization, testService);
        organizationDao.addOrganizationToRegion(testOrganization, testRegion);

        organizationDao.deleteById(organizationId);
        assertEquals(0, organizationDao.getAllServices(organizationId).size());
        assertEquals(0, organizationDao.getAllCommunities(organizationId).size());
        assertEquals(0, organizationDao.getAllRegions(organizationId).size());
    }

    @Test
    public void clearAll_deletesAllExistingOrganizations() throws Exception {
        Organization testOrganization = setUpOrganization();
        Organization altTestOrganization = setUpAltOrganization();
        organizationDao.clearAll();
        assertEquals(0, organizationDao.getAll().size());
    }

    // Helpers
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

    public Community setUpCommunity() {
        Community newCommunity = new Community("gender non-binary", "gender identity");
        communityDao.add(newCommunity);
        return newCommunity;
    }

    public Community setUpAltCommunity() {
        Community newCommunity = new Community("nonlegal citizen", "immigration status");
        communityDao.add(newCommunity);
        return newCommunity;
    }

    public Region setUpRegion() {
        Region newRegion = new Region ("Inner SE");
        regionDao.add(newRegion);
        return newRegion;
    }

    public Region setUpAltRegion() {
        Region newRegion = new Region ("Outer SE");
        regionDao.add(newRegion);
        return newRegion;
    }
}