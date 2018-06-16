package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class IdentifiableReportTest {

    @Test
    public void newReport_instantiatesCorrectly() {
        IdentifiableReport testReport = newIdentifiableReport();
        assertEquals("Jose", testReport.getFirstName());
    }

    @Test
    public void setFirstName() {
        IdentifiableReport testReport = newIdentifiableReport();
        testReport.setFirstName("Jorge");
        assertEquals("Jorge", testReport.getFirstName());
    }

    @Test
    public void setLastName() {
        IdentifiableReport testReport = newIdentifiableReport();
        testReport.setLastName("Ramos");
        assertEquals("Ramos", testReport.getLastName());
    }

    @Test
    public void setPhone() {
        IdentifiableReport testReport = newIdentifiableReport();
        testReport.setPhone("345-356-5765");
        assertEquals("345-356-5765", testReport.getPhone());
    }

    @Test
    public void setEmail() {
        IdentifiableReport testReport = newIdentifiableReport();
        testReport.setEmail("Chavez@skyscape.net");
        assertEquals("Chavez@skyscape.net", testReport.getEmail());
    }

    @Test
    public void setSelfIdentifiers() {
        IdentifiableReport testReport = newIdentifiableReport();

        Community testCommunity = newCommunity();
        Community testAltCommunity = newAltCommunity();

        ArrayList<String> selfIdentifiers = new ArrayList<String>(){{
            add(testCommunity.getName());
            add(testAltCommunity.getName());
        }};
        testReport.setSelfIdentifiers(selfIdentifiers);
        assertEquals(2, testReport.getSelfIdentifiers().size());
    }

    @Test
    public void setDateTimeField() {
        IdentifiableReport testReport = newIdentifiableReport();
        long currentTime = System.currentTimeMillis();
        testReport.setDateTimeFiled(currentTime);
        assertEquals(currentTime, testReport.getDateTimeFiled());
    }

    @Test
    public void setFormattedDateTime() {
        IdentifiableReport testReport = newIdentifiableReport();
        String originalFormattedTime = testReport.getFormattedDateTime();
        long currentTime = System.currentTimeMillis();
        testReport.setDateTimeFiled(currentTime);
        testReport.setFormattedDateTime();
        assertNotEquals(originalFormattedTime, testReport.getFormattedDateTime());
    }

    // Helpers
    public IdentifiableReport newIdentifiableReport() {
        return new IdentifiableReport(
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
    }

    public IdentifiableReport newAltIdentifiableReport() {
        return new IdentifiableReport(
                "Linda",
                "Rosario",
                "971-592-9386",
                "L.Rosario@aol.com",
                "Target",
                15,"Outer NE",
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
    }

    public Community newCommunity() {
        return new Community("non-binary", "gender");
    }

    public Community newAltCommunity() {
        return new Community("Latino/a/x", "race/ethnicity");
    }
}