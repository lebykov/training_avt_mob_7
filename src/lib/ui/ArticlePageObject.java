package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import lib.Platform;

abstract public class ArticlePageObject extends MainPageObject
{
    protected static String
        TITLE,
        FOOTER,
        OPTIONS_BUTTON,
        OPTIONS_ADD_TO_MY_READING_LIST,
        ADD_TO_MY_LIST_OVERLAY,
        MY_LIST_NAME_INPUT,
        MY_LIST_OK_BUTTON,
        CLOSE_ARTICLE_BUTTON,
        NAME_OF_FOLDER_TPL,
        SYNC_SAVED_ARTICLES_POPUP_TITLE,
        SYNC_SAVED_ARTICLES_POPUP_CLOSE_BUTTON;

    public ArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    public WebElement waitForTitleElement()
    {
        return this.waitForElementPresent(TITLE, "Cannot find article title", 15);
    }

    public String getArticleTitle()
    {
        WebElement titleElement = waitForTitleElement();

        if (Platform.getInstance().isAndroid()) {
            return titleElement.getAttribute("text");
        } else {
            return titleElement.getAttribute("name");
        }


    }

    public void swipeToFooter()
    {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER,
                    "Cannot find the end of article",
                    20
            );
        } else {
            this.swipeUpTillElementAppear(
                    FOOTER,
                    "Cannot find the end of article",
                    40
            );
        }
    }

    public void addArticleToMyList(String name_of_folder)
    {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_READING_LIST,
                "Cannot find option to add article to reading list",
                5
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' tip overlay",
                5
        );

        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find input to set name of article folder",
                5
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot find input to set name of article folder",
                5
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press OK button",
                5
        );
    }

    public void closeArticle()
    {
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find X link",
                5
        );
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

    public void addArticlesToMySaved()
    {
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_READING_LIST,
                "Cannot find option to add article to reading list",
                10
        );
    }

    // Ex8
    private static String getFolderXpathByFolderName(String name_of_folder)
    {
        return NAME_OF_FOLDER_TPL.replace("{FOLDER}", name_of_folder);
    }

    public void addArticleToExistingList(String name_of_folder)
    {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_READING_LIST,
                "Cannot find option to add article to reading list",
                5
        );

        String folderXpath = getFolderXpathByFolderName(name_of_folder);
        this.waitForElementAndClick(
                folderXpath,
                "Cannot find folder " + name_of_folder + " to save second article to",
                5
        );
    }

    public void assertArticleHasTitleElement()
    {
        this.assertElementPresent(
            TITLE,
            "No page title found"
    );
    }
}
