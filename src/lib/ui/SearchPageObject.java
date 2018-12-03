package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class SearchPageObject extends MainPageObject {

    private static final String
        SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
        SEARCH_INPUT = "//*[contains(@text, 'Search…')]",
        SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
        SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
        SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
        SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']",
        SEARCH_RESULTS_LIST = "org.wikipedia:id/search_results_list",
        SEARCH_RESULT_TITLE_ELEMENT = "org.wikipedia:id/page_list_item_title",
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL =
                "//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='{TITLE}']//following-sibling::*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='{DESCRIPTION}']";

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
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find and click search init element", 5);
        this.waitForElementPresent(By.xpath(SEARCH_INPUT), "Cannot find search input after clicking search init element");
    }

    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "Cannot find search cancel button", 5);
    }

    public void waitForCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "Search cancel button is still present", 5);
    }

    public void clickCancelSearch()
    {
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot find and click search cancel button", 5);
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), search_line, "Cannot find and type into search input", 5);
    }

    public void waitForSearchResult(String substring)
    {
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(searchResultXpath), "Cannot find search result with substring " + substring);
    }

    public void clickByArticleWithSubstring(String substring)
    {
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(searchResultXpath), "Cannot find and click search result with substring " + substring, 10);
    }

    public int getAmountOfFoundArticles()
    {
        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "Cannot find anything by the request ",
                15
        );
        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }

    public void waitForEmptyResultsLabel()
    {
        this.waitForElementPresent(
                By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),
                "Cannot find empty result element",
                15);
    }

    public void assertThereIsNoResultOfSearch()
    {
        this.assertElementNotPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "We supposed not to find any results"
        );
    }

    // Ex8
    public void waitForSearchResultsListNotPresent()
    {
        this.waitForElementNotPresent(
                By.id(SEARCH_RESULTS_LIST),
                "Search results list is still on the screen",
                5
        );
    }

    public List<String> getListOfResultsTitles()
    {
        // wait for results list
        WebElement search_results_list = this.waitForElementPresent(
                By.id(SEARCH_RESULTS_LIST),
                "Cannot find search results list",
                15
        );

        // get title elements from search result list
        List<WebElement> search_result_titles = search_results_list.findElements(
                By.id(SEARCH_RESULT_TITLE_ELEMENT)
        );

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
                By.xpath(elementXpath),
                "Cannot find element with title '" + title + "' and description '" + description + "'",
                15
        );
    }
}
