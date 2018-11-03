import org.junit.Assert;
import org.junit.Test;

public class MainClassTest
{
    @Test
    public void testGetLocalNumber()
    {
        int expected = 14;
        int actual = MainClass.getLocalNumber();
        Assert.assertTrue(
                "MainClass.getLocalNumber() returned " + actual + " instead of " + expected,
                actual == expected
        );
    }

    @Test
    public void testGetClassNumber()
    {
        MainClass mainClass = new MainClass();
        int expected = 45;
        int actual = mainClass.getClassNumber();
        Assert.assertTrue(
                "getClassNumber() returned " + actual + " instead of a number larger than " + expected,
                actual > expected
        );

    }
}
