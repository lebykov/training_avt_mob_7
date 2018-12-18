package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class IOSMyListsPageObject extends MyListsPageObject
{
    static {
        FOLDER_BY_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']";
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name, '{TITLE}')]";
        SAVED_ARTICLE_CONTAINER = "xpath://XCUIElementTypeCollectionView/XCUIElementTypeCell";
        SEARCH_SAVED_INPUT = "id:Search";
        FILTERED_ARTICLE = "xpath:*//XCUIElementTypeCollectionView/XCUIElementTypeCell";
    }

    public IOSMyListsPageObject(AppiumDriver driver)
    {
        super(driver);
    }
}
