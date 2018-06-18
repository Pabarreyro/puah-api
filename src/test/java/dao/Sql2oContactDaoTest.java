package dao;

import models.Contact;
import models.Organization;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oContactDaoTest {
    private static Connection conn;
    private static Sql2oReportDao reportDao;
    private static Sql2oCommunityDao communityDao;
    private static Sql2oOrganizationDao organizationDao;
    private static Sql2oContactDao contactDao;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/puah_test";
        Sql2o sql2o = new Sql2o(connectionString, null, null);
        reportDao = new Sql2oReportDao(sql2o);
        communityDao = new Sql2oCommunityDao(sql2o);
        organizationDao = new Sql2oOrganizationDao(sql2o);
        contactDao = new Sql2oContactDao(sql2o);
        conn = sql2o.open();
        contactDao.clearAll();
    }

    @After
    public void tearDown() throws Exception {
        contactDao.clearAll();
        System.out.println("clearing database");
    }

    @AfterClass
    public static void shutDown() throws Exception {
        conn.close();
        System.out.println("connection closed");
    }
    @Test
    public void add_setsId() {
        Contact testContact = newContact();
        Contact testAltContact = newAltContact();
        assertNotEquals(null, testContact.getId());
        assertNotEquals(null, testAltContact.getId());
    }

    @Test
    public void getAll_returnsAllExistingContacts() {
        Contact testContact = newContact();
        Contact testAltContact = newAltContact();
        assertEquals(2, contactDao.getAll().size());
    }

    @Test
    public void getAll_returnsEmptyListIfNoContactsExist() {
        assertEquals(0, contactDao.getAll().size());
    }

    @Test
    public void findById_returnsCorrectContact() {
        Contact testContact = newContact();
        Contact testAltContact = newAltContact();
        assertEquals(testContact, contactDao.findById(testContact.getId()));
    }

    @Test
    public void update_correctlyUpdatesContact() {
        Contact testContact = newContact();
        Contact newContact = new Contact("Jose", "Chavez", "jose@gmail.com", "503-323-1425");
        newContact.setId(testContact.getId());
        contactDao.update(newContact);
        Contact updatedContact = contactDao.findById(testContact.getId());
        assertEquals(testContact.getFirstName(), newContact.getFirstName());
        assertEquals(testContact.getLastName(), newContact.getLastName());
        assertNotEquals(testContact.getEmail(), newContact.getEmail());
        assertEquals(testContact.getPhone(), newContact.getPhone());
    }

    @Test
    public void deleteById_deletesCorrectContact() {
        Contact testContact = newContact();
        Contact testAltContact = newAltContact();
        contactDao.deleteById(testAltContact.getId());
        assertEquals(1, contactDao.getAll().size());
    }

    @Test
    public void deleteById_deletesAllAssociatedJoins() {
        Contact testContact = newContact();
        int contactId = testContact.getId();

        Organization testOrganization = setUpOrganization();
        int organizationId = testOrganization.getId();

        organizationDao.addOrganizationToContact(organizationId, contactId);

        contactDao.deleteById(contactId);
        assertEquals(0, organizationDao.getAllContacts(organizationId).size());
    }

    @Test
    public void clearAll() {
        Contact testContact = newContact();
        Contact testAltContact = newAltContact();
        contactDao.clearAll();
        assertEquals(0, contactDao.getAll().size());
    }

    // HELPER
    public Contact newContact() {
        Contact newContact = new Contact("Jose", "Chavez", "jose@netscape.com", "503-323-1425");
        contactDao.add(newContact);
        return newContact;
    }

    public Contact newAltContact() {
        Contact newContact = new Contact("Maria", "Moreno", "maria@netscape.com", "971-242-1425");
        contactDao.add(newContact);
        return newContact;
    }

    public Organization setUpOrganization() {
        Organization newOrganization = new Organization("CCC", "201 NW 2nd Ave", "97201", "503-260-5690", "www.ccc.org", "info@ccc.org");
        organizationDao.add(newOrganization);
        return newOrganization;
    }
}