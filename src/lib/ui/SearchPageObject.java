package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

abstract public class SearchPageObject extends MainPageObject {

    protected static String
        SEARCH_INIT_ELEMENT,
        SEARCH_INPUT,
        SEARCH_CANCEL_BUTTON,
        SEARCH_RESULT_BY_SUBSTRING_TPL,
        SEARCH_RESULT_ELEMENT,
        SEARCH_EMPTY_RESULT_ELEMENT,
        SEARCH_RESULTS_LIST,
        SEARCH_RESULT_TITLE_ELEMENT,
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL,
        CLEAR_SEARCH_FIELD_BUTTON;

    public SearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    /* TEMPLATE METHOD */
    private static String getResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    /* TEMPLATE METHOD */

    /* TEMPLATE METHOD */
    private static String getResultSearchElementXpathByTitleAndDescription(String title, String description)
    {
        return SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL
                .replace("{TITLE}", title)
                .replace("{DESCRIPTION}", description);
    }
    /* TEMPLATE METHOD */

    public void initSearchInput()
    {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
        this.waitForElementPresent(SEARCH_INPUT, "Cannot find search input after clicking search init element");
    }

    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 5);
    }

    public void waitForCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", 5);
    }

    public void clickCancelSearch()
    {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button", 5);
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find and type into search input", 5);
    }

    public void waitForSearchResult(String substring)
    {
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementPresent(searchResultXpath, "Cannot find search result with substring " + substring);
    }

    public void clickByArticleWithSubstring(String substring)
    {
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementAndClick(searchResultXpath, "Cannot find and click search result with substring " + substring, 10);
    }

    public int getAmountOfFoundArticles()
    {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request ",
                15
        );
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel()
    {
        this.waitForElementPresent(
                SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot find empty result element",
                15);
    }

    public void assertThereIsNoResultOfSearch()
    {
        this.assertElementNotPresent(
                SEARCH_RESULT_ELEMENT,
                "We supposed not to find any results"
        );
    }

    // Ex8
    public void waitForSearchResultsListNotPresent()
    {
        this.waitForElementNotPresent(
                SEARCH_RESULTS_LIST,
                "Search results list is still on the screen",
                5
        );
    }

    public List<String> getListOfResultsTitles()
    {
        // wait for results list
        WebElement search_results_list = this.waitForElementPresent(
                SEARCH_RESULTS_LIST,
                "Cannot find search results list",
                15
        );

        // get title elements from search result list
        By by = this.getLocatorByString(SEARCH_RESULT_TITLE_ELEMENT);
        List<WebElement> search_result_titles = search_results_list.findElements(by);

        // extract title strings from elements
        List<String> title_text_list = search_result_titles.stream()
                .map(WebElement::getText)
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        return title_text_list;
    }

    public void waitForElementByTitleAndDescription(String title, String description)
    {
        String elementXpath = getResultSearchElementXpathByTitleAndDescription(title, description);
        this.waitForElementPresent(
                elementXpath,
                "Cannot find element with title '" + title + "' and description '" + description + "'",
                15
        );
    }

    public void clearSearchField()
    {
        this.waitForElementAndClick(
                CLEAR_SEARCH_FIELD_BUTTON,
                "Cannot find and click on clear search field button",
                10
        );
    }
}
