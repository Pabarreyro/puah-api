package dao;

import models.AnonymousReport;
import models.Community;
import models.IdentifiableReport;
import models.Report;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class Sql2oReportDaoTest {
    private static Connection conn;
    private static Sql2oReportDao reportDao;
    private static Sql2oCommunityDao communityDao;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgres:localhost:4567/puah";
        Sql2o sql2o = new Sql2o(connectionString, null, null);
        reportDao = new Sql2oReportDao(sql2o);
        communityDao = new Sql2oCommunityDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        reportDao.clearAll();
        System.out.println("clearing database");
    }

    @AfterClass
    public static void shutDown() throws Exception {
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void add_setsId() throws ExecutionException {
        AnonymousReport testReport = newAnonymousReport();
        IdentifiableReport testAltReport = newIdentifiableReport();
        assertNotEquals(null, testReport);
        assertNotEquals(null, testAltReport);
    }

    @Test
    public void getAll_returnsAllExistingReports() throws Exception{
        AnonymousReport testReport = newAnonymousReport();
        IdentifiableReport testAltReport = newIdentifiableReport();
        assertEquals(2, reportDao.getAll().size());
    }

    @Test
    public void getAll_returnsEmptyListIfNoReportsExist() throws Exception {
        assertEquals(0, reportDao.getAll().size());
    }

    @Test
    public void getAllCommunities_returnsAllAssociatedReports() throws Exception {
        Community testCommunity = newCommunity();
        Community altTestCommunity = newAltCommunity();

        AnonymousReport testReport = newAnonymousReport();
        reportDao.addCommunityToReport(testReport, testCommunity);
        reportDao.addCommunityToReport(testReport, altTestCommunity);

        Community[] communities = {testCommunity, altTestCommunity};
        assertEquals(Arrays.asList(communities), reportDao.getAllCommunities(testReport.getId()));
    }

    @Test
    public void findById() throws Exception {
        AnonymousReport testReport = newAnonymousReport();
        IdentifiableReport testAltReport = newIdentifiableReport();
        assertEquals(testReport, reportDao.findById(testReport.getId()));
    }

    @Test
    public void findByConfirmationNumber() throws Exception {
        AnonymousReport testReport = newAnonymousReport();
        IdentifiableReport testAltReport = newIdentifiableReport();
        assertEquals(testReport, reportDao.findByConfirmationNumber(testReport.getConfirmationCode()));
    }

    @Test
    public void update_correctlyUpdatesReport() throws Exception {
        AnonymousReport testReport = newAnonymousReport();
        AnonymousReport testAltReport = new AnonymousReport(
                "Target",
                15,
                "Outer NE",
                "2018-07-15",
                "13:00",
                "N Lombard & NE 45th",
                "Sidewalk",
                "Threat/Intimidation",
                "",
                "Gender Identity",
                "",
                false,
                "",
                false,
                "",
                false,
                "",
                ""
        );
        testAltReport.setId(testReport.getId());
        reportDao.update(testAltReport);
        Report updatedReport = reportDao.findById(testAltReport.getId());
        assertEquals(testAltReport.reporterRole, updatedReport.reporterRole);
        assertEquals(testAltReport.reporterAge, updatedReport.reporterAge);
        assertEquals(testAltReport.reporterLocation, updatedReport.reporterLocation);
        assertEquals(testAltReport.incidentDate, updatedReport.incidentDate);
    }

    @Test
    public void deleteById_deletesCorrectReport() {
        AnonymousReport testReport = newAnonymousReport();
        IdentifiableReport testAltReport = newIdentifiableReport();
        reportDao.deleteById(testAltReport.getId());
        assertEquals(1, reportDao.getAll().size());
    }

    @Test
    public void deleteById_deletesAllAssociations() {
        AnonymousReport testReport = newAnonymousReport();
        int reportId = testReport.getId();

        Community testCommunity = newCommunity();
        reportDao.addCommunityToReport(testReport, testCommunity);

        reportDao.deleteById(reportId);
        assertEquals(0, communityDao.getAll().size());
    }

    @Test
    public void deleteByConfirmationCode_deletesCorrectReport() {
        AnonymousReport testReport = newAnonymousReport();
        IdentifiableReport testAltReport = newIdentifiableReport();
        reportDao.deleteByConfirmationCode(testAltReport.getConfirmationCode());
        assertEquals(1, reportDao.getAll().size());
    }

    @Test
    public void deleteByConfirmationCode_deletesAllAssociations() {
        AnonymousReport testReport = newAnonymousReport();
        String reportConfirmationCode = testReport.getConfirmationCode();

        Community testCommunity = newCommunity();
        reportDao.addCommunityToReport(testReport, testCommunity);

        reportDao.deleteByConfirmationCode(reportConfirmationCode);
        assertEquals(0, communityDao.getAll().size());
    }

    @Test
    public void clearAll_deletesAllExistingReports() {
    }

    @Test
    public void timeStampIsReturnedCorrectly() throws Exception {
        AnonymousReport testReport= newAnonymousReport();

        long creationTime = testReport.getDateTimeFiled();
        long savedTime = reportDao.getAll().get(0).getDateTimeFiled();
        assertEquals(creationTime, reportDao.getAll().get(0).getDateTimeFiled());
    }

    // Helpers
    public AnonymousReport newAnonymousReport() {
        AnonymousReport newReport = new AnonymousReport(
                "Witness",
                30,
                "Inner SW",
                "2018-07-02",
                "18:00",
                "SW Washington Ave & 5th Ave",
                "School",
                "Vandalism",
                "",
                "Race/ethnicity",
                "",
                false,
                "",
                true,
                "",
                true,
                "",
                ""
        );
        reportDao.add(newReport);
        return newReport;
    }

    public IdentifiableReport newIdentifiableReport() {
        IdentifiableReport newReport = new IdentifiableReport(
                "Jose",
                "Chavez",
                "503-345-8564",
                "jose@netscape.com",
                "Witness",
                30,
                "Inner SW",
                "2018-07-02",
                "18:00",
                "SW Washington Ave & 5th Ave",
                "School",
                "Vandalism",
                "",
                "Race/ethnicity",
                "",
                false,
                "",
                true,
                "",
                true,
                "",
                ""
        );
        reportDao.add(newReport);
        return newReport;
    }

    public Community newCommunity() {
        return new Community("non-binary", "gender");
    }

    public Community newAltCommunity() {
        return new Community("Latino/a/x", "race/ethnicity");
    }
}