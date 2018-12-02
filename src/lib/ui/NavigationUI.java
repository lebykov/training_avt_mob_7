package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class NavigationUI extends MainPageObject
{
    private static final String
            My_LISTS_LINK = "//android.widget.FrameLayout[@content-desc='My lists']";

    public NavigationUI(AppiumDriver driver)
    {
        super(driver);
    }


    public void clickMyLists()
    {
        this.waitForElementAndClick(
                By.xpath(My_LISTS_LINK),
                "Cannot find navigation button to My List",
                5
        );
    }


}
