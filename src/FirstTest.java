import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.everyItem;

import java.util.List;
import java.util.stream.Collectors;

public class FirstTest extends CoreTestCase {

    // delete after refactor
    private MainPageObject mainPageObject;


    protected void setUp() throws Exception
    {
        super.setUp();

        MainPageObject mainPageObject = new MainPageObject(driver);
    }
    // end delete after refactor

    @Test
    public void testSearchFieldText()
    {
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        WebElement search_field = mainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5
        );

        String search_field_text = search_field.getAttribute("text");

        Assert.assertEquals(
                "Unexpected text in search field",
                "Search…",
                search_field_text
        );
    }


    // Ex4
    @Test
    public void testSearchResultsRelevance()
    {
        String searchRequest = "parrot";

        // move to search screen
        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        // type in search request
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchRequest,
                "Cannot find search input",
                15
        );

        // wait for results list
        WebElement search_results_list = mainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find search results list",
                15
        );

        // get title elements from search result list
        List<WebElement> search_result_titles = search_results_list.findElements(
                By.id("org.wikipedia:id/page_list_item_title")
        );

        // check that there are actually some result elements
        Assert.assertTrue(
                "There are no elements in search results",
                search_result_titles.size() > 0
        );

        // extract title strings from elements
        List<String> title_text_list = search_result_titles.stream()
                .map(WebElement::getText)
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        // assert there is search request substring in every title string
        Assert.assertThat(
                "Some result titles doesn't contain word '" + searchRequest + "'",
                title_text_list,
                everyItem(containsString(searchRequest.toLowerCase())));
    }

    // Ex7. Fail in LANDSCAPE orientation
    @Test
    public void testFailAfterRotation()
    {
        driver.rotate(ScreenOrientation.LANDSCAPE);

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        // intentionally fail this test
        Assert.assertEquals(
                driver.getOrientation().value(),
                ScreenOrientation.PORTRAIT.value()
        );
    }

    // Ex7. Check if orientation after failed test has been restored to PORTRAIT
    @Test
    public void testStartOrientation()
    {
        String currentOrientation = driver.getOrientation().value();
        System.out.println("currentOrientation is " + currentOrientation);
        Assert.assertEquals(
                "Screen orientation supposed to be portrait, but got " + currentOrientation,
                ScreenOrientation.PORTRAIT.value(),
                currentOrientation
        );
    }


}