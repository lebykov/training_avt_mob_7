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

    @Test
    public void testCancelSearchSuggestions()
    {
        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "parrot",
                "Cannot find search input",
                15
        );

        WebElement search_results_list = mainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find search results list",
                15
        );

        List<WebElement> search_result_titles = search_results_list.findElements(
                By.id("org.wikipedia:id/page_list_item_title")
        );

        Assert.assertTrue(
                "There are no elements in search results",
                search_result_titles.size() > 0
        );

        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find 'X' to cancel search",
                5
        );

        mainPageObject.waitForElementNotPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Search results list is still on the screen",
                5
        );
    }

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

    // Ex5
    @Test
    public void testSaveTwoArticles()
    {
        // 1. Save two articles
        // 1.1. Saving first article
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String articleToRemoveSearchLine = "Java";

        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                articleToRemoveSearchLine,
                "Cannot find search input",
                15
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String articleToRemoveTitle = mainPageObject.waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find article title",
                15
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                5
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );

        mainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of article folder",
                5
        );

        String nameOfFolder = "Learning programming";

        mainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfFolder,
                "Cannot find input to set name of article folder",
                5
        );

        mainPageObject.waitForElementAndClick(
                By.id("android:id/button1"),
                "Cannot press OK button",
                5
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        // 1.2. Saving second article
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String articleToKeepSearchLine = "Kotlin";

        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                articleToKeepSearchLine,
                "Cannot find search input",
                15
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Programming language']"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String articleToKeepTitle = mainPageObject.waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find article title",
                15
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                5
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_container']//*[@text='" + nameOfFolder + "']"),
                "Cannot folder " + nameOfFolder + " to save second article to",
                5
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        // 2. Navigate to list and delete article
        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My List",
                5
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@class='android.widget.FrameLayout']//*[@text='" + nameOfFolder + "']"),
                "Cannot find created folder",
                5
        );

        mainPageObject.swipeElementToLeft(
                By.xpath("//*[@text='" + articleToRemoveTitle + "']"),
                "Cannot find article to remove"
        );

        int amountOfSavedArticles = mainPageObject.getAmountOfElements(
                By.id("org.wikipedia:id/page_list_item_image_container")
        );

        Assert.assertEquals(
                "There supposed to remain 1 saved article, but got " + amountOfSavedArticles,
                1,
                amountOfSavedArticles
        );

        // 3. Open remaining article
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='"+ articleToKeepTitle +"']"),
                "Cannot find second saved article",
                5
        );

        // 4. Assert that remaining article is the article to keep
        String titleOfRemainingArticle = mainPageObject.waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find article title",
                15
        );

        Assert.assertEquals(
                "Title of remaining article supposed to be '"  + articleToKeepTitle + "', but got '" + titleOfRemainingArticle + "'",
                articleToKeepTitle,
                titleOfRemainingArticle
        );
    }

    // Ex6
    @Test
    public void testArticleHasTitleElement()
    {
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searchLine = "Java";

        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchLine,
                "Cannot find search input",
                15
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        mainPageObject.assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "No page title found"
        );
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