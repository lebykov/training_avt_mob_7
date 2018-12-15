package lib.ui.iOS;

import io.appium.java_client.AppiumDriver;
import lib.ui.MainPageObject;

public class iOSSearchPageObject extends MainPageObject {
    private static final String
            SEARCH_INIT_ELEMENT = "predicate:type=='XCUIElementTypeSearchField' and name=='Search Wikipedia'",
            SEARCH_INPUT = "xpath://XCUIElementTypeImage[@name='search']//preceding-sibling::XCUIElementTypeSearchField",
            SEARCH_RESULT_BY_TITLE_TPL = "predicate:type=='XCUIElementTypeLink' AND name CONTAINS '{TITLE}'",
            ADD_LANGUAGES_TOOLTIP = "predicate:type=='XCUIElementTypeStaticText' AND name=='Add languages'",
            CLEAR_SEARCH_FIELD_BUTTON = "id:clear mini";


    /* TEMPLATE METHOD */
    private static String getResultSearchElement(String description)
    {
        return SEARCH_RESULT_BY_TITLE_TPL
                .replace("{TITLE}", description);
    }
    /* TEMPLATE METHOD */

    public iOSSearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    public void initSearchInput()
    {
        this.waitForElementAndClick(
                SEARCH_INIT_ELEMENT,
                "Cannot find and click search init element",
                5
        );
        this.waitForElementPresent(SEARCH_INPUT,
                "Cannot find search input after clicking search init element"
        );
    }

    public void waitForAddLanguagesTooltipToDisappear()
    {
        this.waitForElementPresent(
                ADD_LANGUAGES_TOOLTIP,
                "Cannot find add languages tooltip",
                5
        );
        this.waitForElementNotPresent(
                ADD_LANGUAGES_TOOLTIP,
                "Add languages tooltip is still visible",
                10
        );
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(
                SEARCH_INPUT,
                search_line,
                "Cannot find and type into search input",
                5
        );
    }

    public void clickByArticleWithTitle(String title)
    {
        String searchResultPredicate = getResultSearchElement(title);
        this.waitForElementAndClick(
                searchResultPredicate,
                "Cannot find and click search result with title " + title,
                10);
    }

    public void clearSearchField()
    {
        this.waitForElementAndClick(
                CLEAR_SEARCH_FIELD_BUTTON,
                "Cannot find and click on clear search field button",
                10
        );
    }

    public void waitForSearchResultToBeClickableByTitle(String title)
    {
        String searchResultPredicate = getResultSearchElement(title);
        this.waitForElementToBeClickable(
                searchResultPredicate,
                "Search result with title " + title + " is not clickable",
                10
        );
    }
}
