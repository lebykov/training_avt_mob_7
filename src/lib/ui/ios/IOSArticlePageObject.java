package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class IOSArticlePageObject extends ArticlePageObject
{
    static {
        TITLE = "id:Java (programming language)";
        FOOTER = "id:View article in browser";
        OPTIONS_ADD_TO_MY_READING_LIST = "id:Save for later";
        CLOSE_ARTICLE_BUTTON = "id:Back";
        NAME_OF_FOLDER_TPL = "xpath://*[@resource-id='org.wikipedia:id/item_container']//*[@text='{FOLDER}']";
        SYNC_SAVED_ARTICLES_POPUP_TITLE = "id:Sync your saved articles?";
        SYNC_SAVED_ARTICLES_POPUP_CLOSE_BUTTON = "id:places auth close";
    }

    public IOSArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }
}
