package ActionDrivers;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import org.junit.Assert;

import java.util.function.Function;


public class MainDriver {
    public static WebDriver start(String driverName, String path) {
        if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
            System.setProperty("webdriver.gecko.driver", "libs/geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", "libs/geckodriver");
        }
        WebDriver driver;
        switch (driverName) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "ie":
                driver = new InternetExplorerDriver();
                break;
            case "safari":
                driver = new SafariDriver();
                break;
            case "firefox":
            default:
                driver = new FirefoxDriver();
                break;

        }
        getUrl(driver, path);
        myWait(driver, 10, ExpectedConditions.visibilityOfElementLocated(By.id("content")), true);
        return driver;
    }

    public static void getUrl(WebDriver driver, String path) {
        driver.get("http://52.32.154.225:8080" + path);
    }

    public static boolean myWait(WebDriver driver, int time, Function params, boolean fail) {
        WebDriverWait myWait = new WebDriverWait(driver, time);
        try {
            myWait.until(params);
            return true;
        } catch (TimeoutException nsee) {
            if (fail)
                fail(nsee.getMessage());
            return false;
        }
    }

    public static void fail(String message) {
        Assert.fail(message);
    }

    public static void closeWebDriver(WebDriver driver) {
        driver.quit();
        System.exit(0);
    }

}
