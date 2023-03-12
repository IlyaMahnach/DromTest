package org.example;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import net.jodah.failsafe.internal.util.Assert;
import org.openqa.selenium.chrome.ChromeOptions;

public class Test1 {

    public static DromPage autodrompage;
    public static WebDriver driver;

    @BeforeClass
    public static void setup() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(chromeOptions);


        autodrompage = new DromPage(driver);

        driver.get("https://auto.drom.ru/");
        driver.manage().timeouts().getPageLoadTimeout();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void Test1() throws InterruptedException {
        autodrompage.setBrand("Toyota");
        Thread.sleep(2000);
        autodrompage.setModel("Harrier");
        autodrompage.setFuelType("Гибрид");
        autodrompage.setUnsold();
        autodrompage.openAdvancedFilter();
        autodrompage.setMileageFrom("1");
        autodrompage.setYearFrom("2007");
        autodrompage.applyFilters();

        //Проверяем результаты на 1-2 страницах
        for (int page = 0; page < 2; page++) {
            //Не отображаются проданные машины
            checkCrossedBullsTitleAreNotDisplayed();

            //Отображаются машины >=2007 года
            checkCarsYearMoreOrEqual(2007);

            //Каждое объявление содержит информацию о пробеге
            checkEveryBullHasMilleageInfo();

            autodrompage.clickNextPage();
        }
    }

    private void checkCrossedBullsTitleAreNotDisplayed() {
        java.util.List<WebElement> crossedBullsTitle = driver.findElements(By.cssSelector("[data-crossed-bull='true']"));
        Assert.isTrue(crossedBullsTitle.size() == 0, "В списке объявлений не должно быть проданных авто");
    }

    private void checkCarsYearMoreOrEqual(int year) {
        java.util.List<WebElement> bullsTitle = driver.findElements(By.cssSelector("[data-ftid='bull_title']"));
        for (WebElement bullTitle : bullsTitle) {
            String bullTitleText = bullTitle.getText().trim();
            int carYear = Integer.parseInt(bullTitleText.substring(bullTitleText.length() - 4));
            Assert.isTrue(carYear >= year, "В списке объявлений не должно быть авто старше" + year + "года");
        }
    }

    private void checkEveryBullHasMilleageInfo() {
        String bullsDescriptionLocator = "//*[@data-ftid='bulls-list_bull']//*[@data-ftid='component_inline-bull-description']";
        java.util.List<WebElement> bullsDescription = driver.findElements(By.xpath(bullsDescriptionLocator));
        for (WebElement bull : bullsDescription) {
            java.util.List<WebElement> milleageInfo = bull.findElements(By.xpath("./span[contains(text(),'тыс. км')]"));
            Assert.isTrue(milleageInfo.size() == 1, "В объявлении нет информации о пробеге");
        }
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}
