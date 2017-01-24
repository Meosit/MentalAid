package by.mksn.epam.mentalaid.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class NullUtilTest {

    @Test
    public void nullableEqualsForTwoNullObjectsTest() throws Exception {
        assertTrue(NullUtil.nullableEquals(null, null));
    }

    @Test
    public void nullableEqualsForOneNonNullAndAnotherNullObjectsTest() throws Exception {
        assertFalse(NullUtil.nullableEquals(new Object(), null));
    }

    @Test
    public void nullableEqualsForEqualNonNullObjectsTest() throws Exception {
        assertTrue(NullUtil.nullableEquals("abc", "abc"));
    }


    @Test
    public void nullableEqualsForNotEqualNonNullObjectsTest() throws Exception {
        assertFalse(NullUtil.nullableEquals("abc", "asgfiaejngfa;j"));
    }

    @Test
    public void nullableHashCodeForNullObjectTest() throws Exception {
        assertEquals(0, NullUtil.nullableHashCode(null));
    }

    @Test
    public void nullableHashCodeForNonNullObjectTest() throws Exception {
        String string = "12345";
        assertEquals(string.hashCode(), NullUtil.nullableHashCode(string));
    }

    @Test
    public void isNullForNullObjectTest() throws Exception {
        assertTrue(NullUtil.isNull(null));
    }

    @Test
    public void isNullForNonNullObjectTest() throws Exception {
        assertFalse(NullUtil.isNull(new Object()));
    }

    @Test
    public void ifNullDefaultForNullObjectTest() throws Exception {
        String defaultValue = "123";
        assertEquals(defaultValue, NullUtil.ifNullDefault(null, defaultValue));
    }

    @Test
    public void ifNullDefaultForNonNullObjectTest() throws Exception {
        String testString = "321";
        String defaultValue = "123";
        assertEquals(testString, NullUtil.ifNullDefault(testString, defaultValue));
    }

}