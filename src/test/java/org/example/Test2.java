package org.example;

import net.jodah.failsafe.internal.util.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class Test2 {

    public static DromPage autodrompage;
    public static FavoritesPage bookmarkpage;
    public static AuthorizationPage loginpage;
    public static WebDriver driver;

    @BeforeClass
    public static void setup() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
        autodrompage = new DromPage(driver);
        bookmarkpage = new FavoritesPage(driver);
        loginpage = new AuthorizationPage(driver);

        driver.get("https://auto.drom.ru/");
        driver.manage().timeouts().getPageLoadTimeout();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void Test2() {
        loginpage.authorize("ЛОГИН", "ПАРОЛЬ.");
        autodrompage.addBullToFavorites();
        checkFavoriteBullHref();
    }

    private void checkFavoriteBullHref() {

        //получаем ссылку на первое объявление, которое добавим в Избранное
        String firstBullHref = driver.findElement(By.cssSelector("[data-ftid='bulls-list_bull']")).getAttribute("href");
        driver.get("https://my.drom.ru/personal/bookmark");
        //Проверяем, что объявление добавилось
        int favoriteBullsCount = driver.findElements(By.cssSelector("[class*='bull-item_inline']")).size();
        Assert.isTrue(favoriteBullsCount == 1, "Объявление не добавлено в избранное");

    }

    @AfterClass
    public static void tearDown() {
        //удаляем объявление из избранного
        bookmarkpage.removeFavoriteBull();
        driver.quit();
    }
}