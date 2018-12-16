package lib;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class Platform {
    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";
    private static final String APPIUM_URL = "http://127.0.0.1:4723/wd/hub";

    private static Platform instance;

    private Platform()
    {

    }

    public static Platform getInstance()
    {
        if (instance == null) {
            instance = new Platform();
        }
        return instance;
    }

    public boolean isAndroid()
    {
        return this.isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS()
    {
        return this.isPlatform(PLATFORM_IOS);
    }

    public AppiumDriver getDriver() throws Exception
    {
        if (this.isAndroid()) {
            return new AppiumDriver(new URL(APPIUM_URL), this.getAndroidDesiredCapabilities());
        } else if (this.isIOS()) {
            return new AppiumDriver(new URL(APPIUM_URL), this.getIOSDesiredCapabilities());
        } else {
            throw new Exception("Cannot detect type of the driver. Platform value: " + this.getPlatformVar());
        }
    }

    private DesiredCapabilities getAndroidDesiredCapabilities()
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platform","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","8.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","/Users/abykov/side_projects/training_avt_mob_7/apks/org.wikipedia.apk");

        // Ex7. Set screen orientation to PORTRAIT orientation before each test
        capabilities.setCapability("orientation", "PORTRAIT");

        return capabilities;
    }

    private DesiredCapabilities getIOSDesiredCapabilities()
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platform","iOS");
        capabilities.setCapability("deviceName","iPhone SE");
        capabilities.setCapability("platformVersion","12.1");
        capabilities.setCapability("app","/Users/abykov/side_projects/training_avt_mob_7/apks/Wikipedia.app");

        // Ex7. Set screen orientation to PORTRAIT orientation before each test
        capabilities.setCapability("orientation", "PORTRAIT");

        return capabilities;

    }

    private boolean isPlatform(String my_platform)
    {
        String platform = this.getPlatformVar();
        return my_platform.equals(platform);
    }

    private String getPlatformVar()
    {
        return System.getenv("PLATFORM");
    }
}
