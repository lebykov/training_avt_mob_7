package lib.ui.iOS_ex;

import io.appium.java_client.AppiumDriver;
import lib.ui.MainPageObject;

public class iOSNavigationUIEx extends MainPageObject {

    private static final String
        SAVED_BUTTON = "id:Saved";

    public iOSNavigationUIEx(AppiumDriver driver)
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
