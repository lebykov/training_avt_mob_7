package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

abstract public class NavigationUI extends MainPageObject
{
    protected static String
            My_LISTS_LINK;

    public NavigationUI(AppiumDriver driver)
    {
        super(driver);
    }


    public void clickMyLists()
    {
        this.waitForElementAndClick(
                My_LISTS_LINK,
                "Cannot find navigation button to My List",
                5
        );
    }


}
