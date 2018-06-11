package models;

import org.junit.*;

import static org.junit.Assert.*;

public class RegionTest {

    @Test
    public void newRegion_instantiatesCorrectly() {
        Region testRegion = newRegion();
        assertEquals("Inner SE", testRegion.getName());
    }

    @Test
    public void setName_setsNameCorrectly() {
        Region testRegion = newRegion();
        testRegion.setName("Outer SE");
        assertEquals("Outer SE", testRegion.getName());
    }

    @Test
    public void setId_setsIdCorrectly() {
        Region testRegion = newRegion();
        testRegion.setId(1);
        assertEquals(1, testRegion.getId());
    }

    // Helper
    public Region newRegion() {
        return new Region("Inner SE");
    }
}