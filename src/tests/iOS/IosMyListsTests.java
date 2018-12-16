package tests.iOS;

import lib.CoreTestCase;
import lib.ui.*;
import lib.ui.iOS_ex.iOSArticlePageObjectEx;
import lib.ui.iOS_ex.iOSMyListsPageObjectEx;
import lib.ui.iOS_ex.iOSNavigationUIEx;
import lib.ui.iOS_ex.iOSSearchPageObjectEx;
import org.junit.Test;

public class IosMyListsTests extends CoreTestCase
{

    // Ex 10
    @Test
    public void testSaveTwoArticlesToMyList() {
        WelcomePageObject welcomePageObject = new WelcomePageObject(driver);
        welcomePageObject.passThroughWelcomePage();

        // 1. Save two articles
        // 1.1. Saving first article
        String articleToRemoveSearchLine = "Asd RNA motif";

        iOSSearchPageObjectEx searchPageObject = new iOSSearchPageObjectEx(driver);
        searchPageObject.initSearchInput();
        searchPageObject.waitForAddLanguagesTooltipToDisappear();
        searchPageObject.typeSearchLine(articleToRemoveSearchLine);
        String articleToRemoveTitle = "Asd RNA motif";
        searchPageObject.waitForSearchResultToBeClickableByTitle(articleToRemoveTitle);
        searchPageObject.clickByArticleWithTitle(articleToRemoveTitle);

        iOSArticlePageObjectEx articlePageObject = new iOSArticlePageObjectEx(driver);
        articlePageObject.waitForGoHomeTooltipDissappear();
        articlePageObject.waitForTitleElement(articleToRemoveTitle);
        String name_of_folder = "Short articles";
        articlePageObject.addArticleToMyList(name_of_folder, articleToRemoveTitle);
        articlePageObject.closeArticle();

        // 1.2. Saving second article
        String articleToKeepSearchLine = "Aar small RNA";
        String articleToKeepTitle = "Aar small RNA";
        searchPageObject.initSearchInput();
        searchPageObject.clearSearchField();
        searchPageObject.typeSearchLine(articleToKeepSearchLine);
        searchPageObject.waitForSearchResultToBeClickableByTitle(articleToKeepTitle);
        searchPageObject.clickByArticleWithTitle(articleToKeepTitle);

        articlePageObject.waitForTitleElement(articleToKeepTitle);
        articlePageObject.addArticleToExistingList(name_of_folder, articleToKeepTitle);
        articlePageObject.closeArticle();

        // 2. Navigate to list and delete article
        iOSNavigationUIEx navigationUI = new iOSNavigationUIEx(driver);
        navigationUI.clickSavedButton();

        iOSMyListsPageObjectEx myListsPageObject = new iOSMyListsPageObjectEx(driver);
        myListsPageObject.clickReadingListsButton();
        myListsPageObject.openFolderByName(name_of_folder);
        myListsPageObject.removeArticleByTitle(articleToRemoveTitle);
        // Search saved articles with title of article to keep and
        // assert that there is exactly one article in the results
        myListsPageObject.searchSavedArticlesByTitle(articleToKeepTitle);
        int amountOfSavedArticles = myListsPageObject.getNumberOfFilteredArticles();

        assertEquals(
                "There supposed to remain 1 saved article, but got " + amountOfSavedArticles,
                1,
                amountOfSavedArticles
        );

        // 3. Open remaining article
        myListsPageObject.openArticleByTitle(articleToKeepTitle);

        // 4. Assert that remaining article is the article to keep by
        // checking the state of Saved button in the toolbar
        articlePageObject.waitForTitleElement(articleToKeepTitle);
        articlePageObject.assertArticleIsInSavedLists();
    }
}
