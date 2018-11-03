import org.junit.Assert;
import org.junit.Test;

public class MainClassTest
{
    private MainClass mainClass= new MainClass();

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
        int expected = 45;
        int actual = this.mainClass.getClassNumber();
        Assert.assertTrue(
                "getClassNumber() returned " + actual + " instead of a number larger than " + expected,
                actual > expected
        );

    }

    @Test
    public void testGetClassString()
    {
        String upperCaseFirstLetter = "Hello";
        String lowerCaseFirstLetter = "hello";
        String actual = this.mainClass.getClassString();
        Assert.assertTrue(
                "getClassString() expected to return a string containing \""
                        + upperCaseFirstLetter + "\" or \"" + lowerCaseFirstLetter
                        + "\" but returned \"" + actual + "\"",
                actual.contains(upperCaseFirstLetter) || actual.contains(lowerCaseFirstLetter)
        );
    }
}
