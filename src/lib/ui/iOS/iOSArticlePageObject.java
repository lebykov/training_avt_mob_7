package lib.ui.iOS;

import io.appium.java_client.AppiumDriver;
import lib.ui.MainPageObject;
import org.openqa.selenium.WebElement;

public class iOSArticlePageObject extends MainPageObject
{
    private static final String
            TITLE_TPL = "predicate:type=='XCUIElementTypeOther' and name=='{TITLE}'",
            SYNC_SAVED_ARTICLES_POPUP_TITLE = "id:Sync your saved articles?",
            SYNC_SAVED_ARTICLES_POPUP_CLOSE_BUTTON = "id:places auth close",
            SAVE_FOR_LATER_BUTTON = "predicate:type=='XCUIElementTypeButton' AND name=='Save for later'",
            REMOVE_FROM_SAVED_ARTICLES = "predicate:type=='XCUIElementTypeButton' AND name=='Saved. Activate to unsave.'",
            ADD_TO_READING_LIST_BUTTON_TPL = "id:Add “{TITLE}” to a reading list?",
            ADD_ARTICLE_TO_READING_LIST_BUTTON = "id:Add",
            CREATE_NEW_LIST_TITLE = "id:Create a new list",
            READING_LIST_NAME_INPUT = "xpath://XCUIElementTypeStaticText[@name='Reading list name']//following-sibling::XCUIElementTypeTextField",
            CREATE_READING_LIST_BUTTON = "id:Create reading list",
            BACK_BUTTON = "id:Back",
            GO_HOME_TOOLTIP = "predicate:type=='XCUIElementTypeStaticText' AND name=='Tap to go home'",
            READING_LIST_TPL = "id:{LIST_NAME}";

    public iOSArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    /* TEMPLATE METHOD */
    private static String getTitleXpathByTitle(String title)
    {
        return TITLE_TPL.replace("{TITLE}", title);
    }
    /* TEMPLATE METHOD */

    /* TEMPLATE METHOD */
    private static String getAddToReadingListButtonXpathByTitle(String title)
    {
        return ADD_TO_READING_LIST_BUTTON_TPL.replace("{TITLE}", title);
    }
    /* TEMPLATE METHOD */

    /* TEMPLATE METHOD */
    private static String getListIdByListName(String list_name)
    {
        return READING_LIST_TPL.replace("{LIST_NAME}", list_name);
    }
    /* TEMPLATE METHOD */

    public WebElement waitForTitleElement(String title)
    {
        String title_xpath = getTitleXpathByTitle(title);
        return this.waitForElementPresent(title_xpath, "Cannot find article title", 15);
    }

    public String getArticleTitle(String title)
    {
        WebElement titleElement = waitForTitleElement(title);
        return titleElement.getAttribute("text");
    }

    public void closeSyncSavedArticlesPopup()
    {
        this.waitForElementPresent(
                SYNC_SAVED_ARTICLES_POPUP_TITLE,
                "Cannot find Sync Saved Articles title text",
                10
        );
        this.waitForElementAndClick(
                SYNC_SAVED_ARTICLES_POPUP_CLOSE_BUTTON,
                "Cannot find Sync Saved Articles Close Button",
                10
        );
    }

    public void waitForGoHomeTooltipDissappear()
    {
        this.waitForElementPresent(
                GO_HOME_TOOLTIP,
                "Cannot find Tap to go home tooltip",
                10
        );
        this.waitForElementNotPresent(
                GO_HOME_TOOLTIP,
                "Tap to go home tooltip is still visible",
                10
        );
    }

    public void addArticleToMyList(String name_of_folder, String title)
    {
        this.waitForElementAndClick(
                SAVE_FOR_LATER_BUTTON,
                "Cannot find and click Save For Later button",
                10
        );

        this.closeSyncSavedArticlesPopup();

        this.waitForElementAndClick(
                REMOVE_FROM_SAVED_ARTICLES,
                "Cannot find and click Save For Later button",
                10
        );

        this.waitForElementAndClick(
                SAVE_FOR_LATER_BUTTON,
                "Cannot find and click Save For Later button",
                10
        );

        String addToReadingListButtonXpath = getAddToReadingListButtonXpathByTitle(title);

        this.waitForElementAndClick(
                addToReadingListButtonXpath,
                "Cannot find button to add article to reading list",
                10
        );

        this.waitForElementAndClick(
                ADD_ARTICLE_TO_READING_LIST_BUTTON,
                "Cannot find + button to add article to reading list",
                10
        );

        this.waitForElementPresent(
                CREATE_NEW_LIST_TITLE,
                "Cannot find Create New List title",
                10
        );

        this.waitForElementAndSendKeys(
                READING_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot find input to set name of article folder",
                5
        );

        this.waitForElementAndClick(
                CREATE_READING_LIST_BUTTON,
                "Cannot press OK button",
                5
        );
    }

    public void closeArticle()
    {
        this.waitForElementAndClick(
                BACK_BUTTON,
                "Cannot close article, cannot find X link",
                5
        );
    }

    public void addArticleToExistingList(String name_of_folder, String title)
    {
        this.waitForElementAndClick(
                SAVE_FOR_LATER_BUTTON,
                "Cannot find and click Save For Later button",
                10
        );

        String addToReadingListButtonXpath = getAddToReadingListButtonXpathByTitle(title);

        this.waitForElementAndClick(
                addToReadingListButtonXpath,
                "Cannot find button to add article to reading list",
                10
        );

        String folderId = getListIdByListName(name_of_folder);
        this.waitForElementAndClick(
                folderId,
                "Cannot find reading list " + name_of_folder,
                10
        );
    }

    public void assertArticleIsInSavedLists()
    {
        int amount_of_elements = getAmountOfElements(REMOVE_FROM_SAVED_ARTICLES);

        if (amount_of_elements == 0) {
            String default_message = "An element '" + REMOVE_FROM_SAVED_ARTICLES + "' supposed to be present";
            throw new AssertionError(default_message + " " + REMOVE_FROM_SAVED_ARTICLES);
        }
    }

}
