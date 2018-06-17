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
        String connectionString = "jdbc:postgresql://localhost:5432/puah_test";
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
        Report testReport = newReport();
        Report testAltReport = newAltReport();
        assertNotEquals(null, testReport);
        assertNotEquals(null, testAltReport);
    }

    @Test
    public void getAll_returnsAllExistingReports() throws Exception{
        Report testReport = newReport();
        Report testAltReport = newAltReport();
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

        Report testReport = newReport();
        reportDao.addCommunityToReport(testReport.getId(), testCommunity.getId());
        reportDao.addCommunityToReport(testReport.getId(), altTestCommunity.getId());

        Community[] communities = {testCommunity, altTestCommunity};
        assertEquals(Arrays.asList(communities), reportDao.getAllCommunities(testReport.getId()));
    }

    @Test
    public void findById() throws Exception {
        Report testReport = newReport();
        Report testAltReport = newAltReport();
        assertEquals(testReport, reportDao.findById(testReport.getId()));
    }

    @Test
    public void findByConfirmationNumber() throws Exception {
        Report testReport = newReport();
        Report testAltReport = newAltReport();
        String confirmationCode = testReport.getConfirmationCode();
        assertEquals(testReport, reportDao.findByConfirmationCode(confirmationCode));
    }

    @Test
    public void update_correctlyUpdatesReport() throws Exception {
        Report testReport = newReport();
        Report testAltReport = new Report(
                "Anonymous",
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
        assertEquals(testAltReport.getReporterRole(), updatedReport.getReporterRole());
        assertEquals(testAltReport.getReporterAge(), updatedReport.getReporterAge());
        assertEquals(testAltReport.getReporterLocation(), updatedReport.getReporterLocation());
        assertEquals(testAltReport.getIncidentDate(), updatedReport.getIncidentDate());
    }

    @Test
    public void deleteById_deletesCorrectReport() {
        Report testReport = newReport();
        Report testAltReport = newAltReport();
        reportDao.deleteById(testAltReport.getId());
        assertEquals(1, reportDao.getAll().size());
    }

    @Test
    public void deleteById_deletesAllAssociations() {
        Report testReport = newReport();
        int reportId = testReport.getId();

        Community testCommunity = newCommunity();
        reportDao.addCommunityToReport(testReport.getId(), testCommunity.getId());

        reportDao.deleteById(reportId);
        assertEquals(0, communityDao.getAll().size());
    }

//    @Test
//    public void deleteByConfirmationCode_deletesCorrectReport() {
//        Report testReport = newReport();
//        Report testAltReport = newAltReport();
//        reportDao.deleteByConfirmationCode(testAltReport.getConfirmationCode());
//        assertEquals(1, reportDao.getAll().size());
//    }

//    @Test
//    public void deleteByConfirmationCode_deletesAllAssociations() {
//        Report testReport = newReport();
//        String reportConfirmationCode = testReport.getConfirmationCode();
//
//        Community testCommunity = newCommunity();
//        reportDao.addCommunityToReport(testReport, testCommunity);
//
//        reportDao.deleteByConfirmationCode(reportConfirmationCode);
//        assertEquals(0, communityDao.getAll().size());
//    }

    @Test
    public void clearAll_deletesAllExistingReports() {
    }

//    @Test
//    public void timeStampIsReturnedCorrectly() throws Exception {
//        Report testReport= newReport();
//
//        long creationTime = testReport.getDateTimeFiled();
//        long savedTime = reportDao.getAll().get(0).getDateTimeFiled();
//        assertEquals(creationTime, reportDao.getAll().get(0).getDateTimeFiled());
//    }

    // Helpers
    public Report newReport() {
        Report newReport = new Report(
                "Anonymous",
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

    public Report newAltReport() {
        Report newReport = new Report(
                "NonAnonymous",
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