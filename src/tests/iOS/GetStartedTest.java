package tests.iOS;

import lib.iOSTestCase;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends iOSTestCase
{
    @Test
    public void testPastThroughWelcome()
    {
        WelcomePageObject welcomePageObject = new WelcomePageObject(driver);

        welcomePageObject.waitForLearnMoreLink();
        welcomePageObject.clickOnNextButton();

        welcomePageObject.waitForNewWaysToExploreText();
        welcomePageObject.clickOnNextButton();

        welcomePageObject.waitForAddOrEditPreferredLangText();
        welcomePageObject.clickOnNextButton();

        welcomePageObject.waitForLearnMoreAboutDataCollectedText();
        welcomePageObject.clickGetStartedButton();
    }
}
