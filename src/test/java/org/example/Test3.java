package org.example;

import net.jodah.failsafe.internal.util.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class Test3 {
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
    public void Test3() {
        autodrompage.setOtherCity("Приморский край");
        Assert.isTrue(driver.getCurrentUrl().contains("https://auto.drom.ru/region25/"), "Приморский край не выбран");


    }

    @AfterClass
    public static void tearDown() {

        driver.quit();
    }

}

