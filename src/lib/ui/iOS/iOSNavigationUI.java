package lib.ui.iOS;

import io.appium.java_client.AppiumDriver;
import lib.ui.MainPageObject;

public class iOSNavigationUI extends MainPageObject {

    private static final String
        SAVED_BUTTON = "id:Saved";

    public iOSNavigationUI(AppiumDriver driver)
    {
        super(driver);
    }

    public void clickSavedButton()
    {
        this.waitForElementAndClick(
                SAVED_BUTTON,
                "Cannot find and click Saved button",
                10
        );
    }

}
