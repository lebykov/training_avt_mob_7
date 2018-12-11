package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

public class CoreTestCase extends TestCase {
    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";


    protected AppiumDriver driver;
    private static String AppiumURL = "http://127.0.0.1:4723/wd/hub";

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();

        DesiredCapabilities capabilities = this.getCapabilitiesByPlatformEnv();

        driver = new AndroidDriver(new URL(AppiumURL), capabilities);
        this.rotateScreenPortrait();
    }

    @Override
    protected void tearDown() throws Exception
    {
        driver.quit();

        super.tearDown();
    }

    protected void rotateScreenPortrait()
    {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void rotateScreenLandscape()
    {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void backgroundApp(long seconds)
    {
        driver.runAppInBackground(Duration.ofSeconds(seconds));
    }

    private DesiredCapabilities getCapabilitiesByPlatformEnv() throws Exception
    {
        String platform = System.getenv("PLATFORM");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (platform.equals(PLATFORM_ANDROID)) {
            capabilities.setCapability("platform","Android");
            capabilities.setCapability("deviceName","AndroidTestDevice");
            capabilities.setCapability("platformVersion","8.0");
            capabilities.setCapability("automationName","Appium");
            capabilities.setCapability("appPackage","org.wikipedia");
            capabilities.setCapability("appActivity",".main.MainActivity");
            capabilities.setCapability("app","/Users/abykov/side_projects/training_avt_mob_7/apks/org.wikipedia.apk");

            // Ex7. Set screen orientation to PORTRAIT orientation before each test
            capabilities.setCapability("orientation", "PORTRAIT");
        } else if (platform.equals(PLATFORM_IOS)) {
            capabilities.setCapability("platform","iOS");
            capabilities.setCapability("deviceName","iPhone SE");
            capabilities.setCapability("platformVersion","12.1");
            capabilities.setCapability("app","/Users/abykov/side_projects/training_avt_mob_7/apks/Wikipedia.app");

            // Ex7. Set screen orientation to PORTRAIT orientation before each test
            capabilities.setCapability("orientation", "PORTRAIT");
        } else {
            throw new Exception("Cannot get run platform from env variable. Platform value: " + platform);
        }

        return capabilities;
    }
}
