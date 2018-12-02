package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListsTests extends CoreTestCase
{
    @Test
    public void testSaveFirstArticleToMyList()
    {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();
        String article_title = articlePageObject.getArticleTitle();
        String name_of_folder = "Learning programming";
        articlePageObject.addArticleToMyList(name_of_folder);
        articlePageObject.closeArticle();

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        myListsPageObject.openFolderByName(name_of_folder);
        myListsPageObject.swipeByArticleToDelete(article_title);
    }

    // Ex8. Refactor test from Ex5
    @Test
    public void testSaveTwoArticlesToMyList()
    {
        // 1. Save two articles
        // 1.1. Saving first article
        String articleToRemoveSearchLine = "Java";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(articleToRemoveSearchLine);
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();
        String articleToRemoveTitle = articlePageObject.getArticleTitle();
        String name_of_folder = "Learning programming";
        articlePageObject.addArticleToMyList(name_of_folder);
        articlePageObject.closeArticle();

        // 1.2. Saving second article
        String articleToKeepSearchLine = "Kotlin";
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(articleToKeepSearchLine);
        searchPageObject.clickByArticleWithSubstring("Programming language");

        articlePageObject.waitForTitleElement();
        String articleToKeepTitle = articlePageObject.getArticleTitle();
        articlePageObject.addArticleToExistingList(name_of_folder);
        articlePageObject.closeArticle();

        // 2. Navigate to list and delete article
        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        myListsPageObject.openFolderByName(name_of_folder);
        myListsPageObject.swipeByArticleToDelete(articleToRemoveTitle);
        int amountOfSavedArticles = myListsPageObject.getAmountOfSavedArticles();

        assertEquals(
                "There supposed to remain 1 saved article, but got " + amountOfSavedArticles,
                1,
                amountOfSavedArticles
        );

        // 3. Open remaining article
        myListsPageObject.openArticleByTitle(articleToKeepTitle);

        // 4. Assert that remaining article is the article to keep
        articlePageObject.waitForTitleElement();
        String titleOfRemainingArticle = articlePageObject.getArticleTitle();

        assertEquals(
                "Title of remaining article supposed to be '"  + articleToKeepTitle + "', but got '" + titleOfRemainingArticle + "'",
                articleToKeepTitle,
                titleOfRemainingArticle
        );
    }
}
