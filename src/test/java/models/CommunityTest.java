package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommunityTest {

    @Test
    public void newCommunity_instantiatesCorrectly() {
        Community testCommunity = newCommunity();
        assertEquals("non-binary", testCommunity.getName());
        assertEquals("gender", testCommunity.getType());
    }

    @Test
    public void setName_setsNameCorrectly() {
        Community testCommunity = newCommunity();
        testCommunity.setName("cis-gendered female");
        assertEquals("cis-gendered female", testCommunity.getName());
    }

    @Test
    public void setType_setsNameCorrectly() {
        Community testCommunity = newCommunity();
        testCommunity.setType("gender identity");
        assertEquals("gender identity", testCommunity.getType());
    }

    @Test
    public void setId_setsNameCorrectly() {
        Community testCommunity = newCommunity();
        testCommunity.setId(1);
        assertEquals(1, testCommunity.getId());
    }

    // Helpers
    public Community newCommunity() {
        return new Community("non-binary", "gender");
    }
}