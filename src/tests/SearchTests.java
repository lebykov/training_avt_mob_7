package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

//import static org.hamcrest.CoreMatchers.containsString;
//import static org.hamcrest.CoreMatchers.everyItem;

public class SearchTests extends CoreTestCase
{
    @Test
    public void testSearch()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String searchLine = "Linkin Park discography";
        searchPageObject.typeSearchLine(searchLine);
        int amount_of_search_results = searchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "We found too few results",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String searchLine = "aslkopkbg";
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertThereIsNoResultOfSearch();
    }

    // Ex8. Refactor test from Ex3
    @Test
    public void testCancelSearchSuggestions()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String searchLine = "parrot";
        searchPageObject.typeSearchLine(searchLine);
        int number_of_found_articles = searchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "There are no elements in search results",
                number_of_found_articles > 0
        );

        searchPageObject.clickCancelSearch();
        searchPageObject.waitForSearchResultsListNotPresent();
    }

    // Refactor Ex4
    @Test
    public void testSearchResultsRelevance()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String searchRequest = "parrot";
        searchPageObject.typeSearchLine(searchRequest);
        int number_of_found_articles = searchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "There are no elements in search results",
                number_of_found_articles > 0
        );

        List<String> title_text_list = searchPageObject.getListOfResultsTitles();

//        Assert.assertThat(
//                "Some result titles doesn't contain word '" + searchRequest + "'",
//                title_text_list,
//                everyItem(containsString(searchRequest.toLowerCase())));
    }

    // Ex9
    @Test
    public void testFirstThreeSearchResultsByTitleAndDescription()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String searchRequest = "Kotlin";
        searchPageObject.typeSearchLine(searchRequest);
        searchPageObject.waitForElementByTitleAndDescription(
                "Kotlin (programming language)",
                "Programming language"
        );
        searchPageObject.waitForElementByTitleAndDescription(
                "Kotlin-class destroyer",
                "Class of Soviet cold-war destroyers"
        );
        searchPageObject.waitForElementByTitleAndDescription(
                "Kotlin Island",
                "Island"
        );
    }
}
