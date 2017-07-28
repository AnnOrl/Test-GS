import ActionDrivers.MainDriver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class MainClasses
{
    public static WebDriver DRIVER;

    @BeforeSuite
    public static void runWebDriver() {
        DRIVER = MainDriver.start("firefox", "/");
    }

    @AfterSuite
    public static void closeWebDriver() {
        MainDriver.closeWebDriver(DRIVER);
    }
}
