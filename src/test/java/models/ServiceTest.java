package models;

import org.junit.*;

import static org.junit.Assert.*;

public class ServiceTest {

    @Test
    public void newService_instantiatesCorrectly() throws Exception {
        Service testService = newService();
        assertEquals("Advocacy", testService.getName());
    }

    @Test
    public void setName_setsNameCorrectly() throws Exception {
        Service testService = newService();
        testService.setName("Health Services");
        assertEquals("Health Services", testService.getName());
    }

    @Test
    public void setId_setsIdCorrectly() throws Exception {
        Service testService = newService();
        testService.setId(1);
        assertEquals(1, testService.getId());
    }

    // Helper
    public Service newService() {
        return new Service ("Advocacy");
    }
}