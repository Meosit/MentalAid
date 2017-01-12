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
    public Object testObjectOne;
    @Parameter(1)
    public Object testObjectTwo;
    @Parameter(2)
    public boolean nullableEqualsExpected;
    @Parameter(3)
    public int nullableHashCodeExpected;
    @Parameter(4)
    public boolean isNullExpected;
    @Parameter(5)
    public Object isNullDefaultExpected;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {
                        Timestamp.valueOf("2010-11-21 11:11:11"),
                        Timestamp.valueOf("2010-11-21 11:11:11"),
                        true,
                        Timestamp.valueOf("2010-11-21 11:11:11").hashCode(),
                        false,
                        Timestamp.valueOf("2010-11-21 11:11:11"),
                },
                {
                        null,
                        "123",
                        false,
                        0,
                        true,
                        "123",
                }
        });
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