package by.mksn.epam.mentalaid.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;

import static by.mksn.epam.mentalaid.util.NullUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class NullUtilTest {

    @Parameter(0)
    public String nullOrEmptyTestString;
    @Parameter(1)
    public Object testObjectOne;
    @Parameter(2)
    public Object testObjectTwo;
    @Parameter(3)
    public boolean nullOrEmptyExpected;
    @Parameter(4)
    public boolean nullableEqualsExpected;
    @Parameter(5)
    public int nullableHashCodeExpected;
    @Parameter(6)
    public boolean isNullExpected;
    @Parameter(7)
    public Object isNullDefaultExpected;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {
                        "",
                        Timestamp.valueOf("2010-11-21 11:11:11"),
                        Timestamp.valueOf("2010-11-21 11:11:11"),

                        true,
                        true,
                        Timestamp.valueOf("2010-11-21 11:11:11").hashCode(),
                        false,
                        Timestamp.valueOf("2010-11-21 11:11:11"),
                },
                {
                        "test",
                        null,
                        "123",

                        false,
                        false,
                        0,
                        true,
                        "123",
                }
        });
    }

    @Test
    public void isNullOrEmptyTest() throws Exception {
        assertEquals(nullOrEmptyExpected, isNullOrEmpty(nullOrEmptyTestString));
    }

    @Test
    public void nullableEqualsTest() throws Exception {
        assertEquals(nullableEqualsExpected, nullableEquals(testObjectOne, testObjectTwo));
    }

    @Test
    public void nullableHashCodeTest() throws Exception {
        assertEquals(nullableHashCodeExpected, nullableHashCode(testObjectOne));
    }

    @Test
    public void isNullTest() throws Exception {
        assertEquals(isNullExpected, isNull(testObjectOne));
    }

    @Test
    public void isNullDefaultTest() throws Exception {
        assertEquals(isNullDefaultExpected, isNullDefault(testObjectOne, testObjectTwo));
    }

}