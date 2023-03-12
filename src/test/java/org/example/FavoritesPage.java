package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FavoritesPage {

    public WebDriver driver;

    public FavoritesPage(WebDriver driver) {
        PageFactory.initElements(driver, this);

        this.driver = driver;
    }

    @FindBy(className = "removeBookmark")
    private WebElement removeFavoriteBullBtn;

    public void removeFavoriteBull() {
        removeFavoriteBullBtn.click();
    }
}