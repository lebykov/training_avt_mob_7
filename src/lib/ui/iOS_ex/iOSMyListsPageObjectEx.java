package lib.ui.iOS_ex;

import io.appium.java_client.AppiumDriver;
import lib.ui.MainPageObject;

public class iOSMyListsPageObjectEx extends MainPageObject {
    private static final String
            READING_LISTS_BUTTON = "id:Reading lists",
            FOLDER_BY_NAME_TPL = "id:{FOLDER_NAME}",
            EDIT_BUTTON = "id:Edit",
            REMOVE_BUTTON = "id:Remove",
            ARTICLE_BY_TITLE_TPL = "predicate:type=='XCUIElementTypeLink' AND name CONTAINS '{TITLE}'",
            FILTERED_ARTICLE = "xpath:*//XCUIElementTypeCollectionView/XCUIElementTypeCell",
            SEARCH_READING_LIST_INPUT = "id:Search reading list"
    ;

    /* TEMPLATE METHOD */
    private static String getFolderIdByName(String name_of_folder)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }
    /* TEMPLATE METHOD */

    /* TEMPLATE METHOD */
    private static String getArticleIdByTitle(String title)
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", title);
    }
    /* TEMPLATE METHOD */

    public iOSMyListsPageObjectEx(AppiumDriver driver)
    {
        super(driver);
    }

    public void clickReadingListsButton()
    {
        this.waitForElementAndClick(
                READING_LISTS_BUTTON,
                "Cannot find and click Reading Lists Button",
                10
        );
    }

    public void openFolderByName(String name_of_folder)
    {
        String folder_name_xpath = getFolderIdByName(name_of_folder);
        this.waitForElementAndClick(
                folder_name_xpath,
                "Cannot find folder by name " + name_of_folder,
                5
        );
    }

    public void removeArticleByTitle(String title)
    {
        this.waitForElementToBeClickable(
                EDIT_BUTTON,
                "Edit button is not clickable",
                10
        );

        this.waitForElementAndClick(
                EDIT_BUTTON,
                "Cannot find and click Edit button",
                10
        );

        String articleId = getArticleIdByTitle(title);
        this.waitForElementAndClick(
                articleId,
                "Cannot find and click article with title " + title,
                10
        );

        this.waitForElementToBeClickable(
                REMOVE_BUTTON,
                "Remove button is not clickable",
                10
        );

        this.waitForElementAndClick(
                REMOVE_BUTTON,
                "Cannot find and click Remove button",
                10
        );
    }

    public void searchSavedArticlesByTitle(String title)
    {
        this.waitForElementToBeClickable(
                SEARCH_READING_LIST_INPUT,
                "Search reading list is not clickable",
                10
        );

        this.waitForElementAndSendKeys(
                SEARCH_READING_LIST_INPUT,
                title,
                "Cannot find and send keys to Search reading list input",
                10
        );
    }

    public int getNumberOfFilteredArticles()
    {
        return this.getAmountOfElements(FILTERED_ARTICLE);
    }

    public void openArticleByTitle(String title)
    {
        String articleId = getArticleIdByTitle(title);
        this.waitForElementToBeClickable(
                articleId,
                "Article with title " + title + "is not clickable",
                10
        );

        this.waitForElementAndClick(
                articleId,
                "Cannot find and click article with title " + title,
                10
        );
    }
}
