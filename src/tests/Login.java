import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ActionDrivers.MainDriver;
import org.testng.annotations.Test;

public class Login extends MainClasses{
    @Test(testName = "Login",description = "login form", priority = 1)
    public void login() {
        MainDriver.myWait(DRIVER, 10, ExpectedConditions.visibilityOfElementLocated(By.name("sUsername")), true);
        DRIVER.findElement(By.name("sUsername")).sendKeys("admin");
        DRIVER.findElement(By.name("password")).sendKeys("SKFs78AKLDnoa-");
        DRIVER.findElement(By.name("password")).submit();
        boolean wait = MainDriver.myWait(DRIVER, 5, ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".warningmessage")), false);
        if (wait) {
            MainDriver.fail("Error login/password");
        }
    }
}
