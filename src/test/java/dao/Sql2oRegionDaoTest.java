package dao;

import models.Organization;
import models.Region;
import org.junit.*;
import org.sql2o.*;

import static org.junit.Assert.*;

public class Sql2oRegionDaoTest {
    private static Connection conn;
    private static Sql2oOrganizationDao organizationDao;
    private static Sql2oRegionDao regionDao;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/resources_test";
        Sql2o sql2o = new Sql2o(connectionString, null, null);
        organizationDao = new Sql2oOrganizationDao(sql2o);
        regionDao = new Sql2oRegionDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        organizationDao.clearAll();
        regionDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception {
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void add_setsId() throws Exception {
        Region testRegion = setUpRegion();
        assertEquals(0, testRegion.getId());
    }

    @Test
    public void getAll_returnsAllExistingRegions() throws Exception {
        Region testRegion = setUpRegion();
        assertEquals(1, regionDao.getAll().size());
        assertEquals(testRegion, regionDao.getAll().get(0));
    }

    @Test
    public void getAll_returnsEmptyListIfNoRegionsExist() throws Exception {
        assertEquals(0, regionDao.getAll().size());
    }

    @Test
    public void findById_returnsCorrectRegion() throws Exception {
        Region testRegion = setUpRegion();
        Region altTestRegion = setUpAltRegion();
        assertEquals(testRegion, regionDao.findById(testRegion.getId()));
    }

    @Test
    public void update_correctlyUpdatesRegion() throws Exception {
        Region testRegion = setUpRegion();
        regionDao.update(testRegion.getId(), "Outer NE");
        Region updatedRegion = regionDao.findById(testRegion.getId());
        assertEquals("Outer NE", updatedRegion.getName());
    }

    @Test
    public void deleteById_deletesCorrectRegion() throws Exception {
        Region testRegion = setUpRegion();
        Region altTestRegion = setUpAltRegion();
        regionDao.deleteById(altTestRegion.getId());
        assertEquals(testRegion, regionDao.getAll().get(0));
        assertEquals(1, regionDao.getAll().size());
    }

    @Test
    public void clearAll_deletesAllExistingRegions() throws Exception {
        Region testRegion = setUpRegion();
        Region altTestRegion = setUpAltRegion();
        regionDao.clearAll();
        assertEquals(0, regionDao.getAll().size());
    }

    // Helpers
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