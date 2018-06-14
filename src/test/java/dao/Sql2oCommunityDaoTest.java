package dao;

import models.Community;
import models.Organization;
import org.junit.*;
import org.sql2o.*;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Sql2oCommunityDaoTest {
    private static Connection conn;
    private static Sql2oOrganizationDao organizationDao;
    private static Sql2oCommunityDao communityDao;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/puah_test";
        Sql2o sql2o = new Sql2o(connectionString, null, null);
        organizationDao = new Sql2oOrganizationDao(sql2o);
        communityDao = new Sql2oCommunityDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        organizationDao.clearAll();
        communityDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception {
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void add_setsId() throws Exception {
        Community testCommunity = setUpCommunity();
        assertNotEquals(null, testCommunity.getId());
    }

    @Test
    public void getAll_returnsAllExistingCommunities() throws Exception {
        Community testCommunity = setUpCommunity();
        assertEquals(1, communityDao.getAll().size());
        assertEquals(testCommunity, communityDao.getAll().get(0));
    }

    @Test
    public void getAll_returnsEmptyListIfNoCommunitiesExist() throws Exception {
        assertEquals(0, communityDao.getAll().size());
    }

    @Test
    public void getAllOrganizations_returnsAllAssociatedOrganizations() {
        Organization testOrganization = setUpOrganization();
        Organization altTestOrganization = setUpAltOrganization();

        Community testCommunity= setUpCommunity();
        communityDao.addCommunityToOrganization(testCommunity, testOrganization);
        communityDao.addCommunityToOrganization(testCommunity, altTestOrganization);

        Organization[] organizations = {testOrganization, altTestOrganization};
        assertEquals(Arrays.asList(organizations), communityDao.getAllOrganizations(testCommunity.getId()));
    }

    @Test
    public void findById_returnsCorrectCommunity() throws Exception {
        Community testCommunity = setUpCommunity();
        Community altTestCommunity = setUpAltCommunity();
        assertEquals(testCommunity, communityDao.findById(testCommunity.getId()));
    }

    @Test
    public void update_correctlyUpdatesCommunity() throws Exception {
        Community testCommunity = setUpCommunity();
        Community altTestCommunity = new Community("gender fluid", "gender identity");
        altTestCommunity.setId(testCommunity.getId());
        communityDao.update(altTestCommunity);
        Community updatedCommunity = communityDao.findById(testCommunity.getId());
        assertEquals(altTestCommunity.getName(), updatedCommunity.getName());
        assertEquals(altTestCommunity.getType(), updatedCommunity.getType());
    }

    @Test
    public void deleteById_deletesCorrectCommunity() throws Exception {
        Community testCommunity = setUpCommunity();
        Community altTestCommunity = setUpAltCommunity();
        communityDao.deleteById(altTestCommunity.getId());
        assertEquals(1, communityDao.getAll().size());
    }

    @Test
    public void clearAll_deletesAllExistingOrganizations() throws Exception {
        Community testCommunity = setUpCommunity();
        Community altTestCommunity = setUpAltCommunity();
        communityDao.clearAll();
        assertEquals(0, communityDao.getAll().size());
    }

    // Helpers
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