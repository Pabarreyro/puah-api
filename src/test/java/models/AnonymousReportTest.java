package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AnonymousReportTest {

    @Test
    public void newReport_instantiatesCorrectly() {
        AnonymousReport testReport = newAnonymousReport();
        assertTrue(testReport instanceof Report);
    }

    @Test
    public void setSelfIdentifiers() {
        AnonymousReport testReport = newAnonymousReport();

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
        AnonymousReport testReport = newAnonymousReport();
        long currentTime = System.currentTimeMillis();
        testReport.setDateTimeFiled(currentTime);
        assertEquals(currentTime, testReport.getDateTimeFiled());
    }

    @Test
    public void setFormattedDateTime() {
        AnonymousReport testReport = newAnonymousReport();
        String originalFormattedTime = testReport.getFormattedDateTime();
        long currentTime = System.currentTimeMillis();
        testReport.setDateTimeFiled(currentTime);
        testReport.setFormattedDateTime();
        assertNotEquals(originalFormattedTime, testReport.getFormattedDateTime());
    }

    // Helpers
    public AnonymousReport newAnonymousReport() {
        return new AnonymousReport(
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

    public AnonymousReport newAltAnonymousReport() {
        return new AnonymousReport(
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