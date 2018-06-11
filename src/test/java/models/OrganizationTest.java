package models;

import org.junit.*;

import static org.junit.Assert.*;

public class OrganizationTest {
    @Test
    public void newOrganization_instantiatesCorrectly() {
        Organization testOrganization = newOrganizationFull();
        assertEquals("www.ccc.org", testOrganization.getWebsite());
        assertEquals("info@ccc.org", testOrganization.getEmail());
    }

    @Test
    public void newOrganization_instantiatesCorrectlyWithoutEmailOrWebsite() {
        Organization testOrganization = newOrganizationPartial();
        assertEquals("website unavailable", testOrganization.getWebsite());
        assertEquals("email unavailable", testOrganization.getEmail());
    }

    @Test
    public void setName_setsNameCorrectly() {
        Organization testOrganization = newOrganizationFull();
        testOrganization.setName("Coalition of Communities of Color");
        assertEquals("Coalition of Communities of Color", testOrganization.getName());
    }

    @Test
    public void setAddress_setsAddressCorrectly() {
        Organization testOrganization = newOrganizationFull();
        testOrganization.setAddress("211 Nw 3rd Ave");
        assertEquals("211 Nw 3rd Ave", testOrganization.getAddress());
    }

    @Test
    public void setZip_setsZipCorrectly() {
        Organization testOrganization = newOrganizationFull();
        testOrganization.setZip("97202");
        assertEquals("97202", testOrganization.getZip());
    }

    @Test
    public void setPhone_setsPhoneCorrectly() {
        Organization testOrganization = newOrganizationFull();
        testOrganization.setPhone("505-223-5840");
        assertEquals("505-223-5840", testOrganization.getPhone());
    }

    @Test
    public void setWebsite_setsWebsiteCorrectly() {
        Organization testOrganization = newOrganizationFull();
        testOrganization.setWebsite("ccc.com");
        assertEquals("ccc.com", testOrganization.getWebsite());
    }

    @Test
    public void setEmail_setsEmailCorrectly() {
        Organization testOrganization = newOrganizationFull();
        testOrganization.setEmail("contact@ccc.org");
        assertEquals("contact@ccc.org", testOrganization.getEmail());
    }

    // Helpers
    public Organization newOrganizationFull() {
        return new Organization("CCC", "201 NW 2nd Ave", "97201", "503-260-5690", "www.ccc.org", "info@ccc.org");
    }

    public Organization newOrganizationPartial() {
        return new Organization("CCC", "201 NW 2nd Ave", "97201", "503-260-5690");
    }
}