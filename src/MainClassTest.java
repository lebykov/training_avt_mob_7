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
}
