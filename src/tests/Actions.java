import ActionDrivers.MainDriver;
import com.sun.jna.platform.win32.OaIdl;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;

public class Actions extends MainClasses {
    @Test(testName = "Add", description = "Add", priority = 2)
    public void actionAdd() {
        String[] tables = {"itemProduct"};
        String[][] types = {
                {"TextForm", "AST" + new Date().toString().substring(0, 17)},
                {"DateForm", ""},
                {"CurrencyForm", ""},
                {"DropdownForm", ""},
                {"CheckboxForm", ""},
                {"RelationsForm", "a"},
                {"EditorForm", "Test selenium"},
        };
        for (int j = 0; j < tables.length; j++) {
            MainDriver.getUrl(DRIVER, "/edit/" + tables[j]);
            MainDriver.myWait(DRIVER, 10, ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".ribbon-text .ui.fluid.input")
            ), true);
            String oldUrl = DRIVER.getCurrentUrl();
            for (int i = 0; i < types.length; i++) {
                typeData(types[i][0], types[i][1]);
            }
            MainDriver.myWait(DRIVER, 5, ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".test-selenium-wait")
            ), false);
            DRIVER.findElement(By.cssSelector(".menu .menu-as-button .menu-as-button-button.positive")).click();
            MainDriver.myWait(DRIVER, 10, ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".test-selenium-wait")
            ), false);
            boolean error = MainDriver.myWait(DRIVER, 10, ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".ui.error.message")
            ), false);
            String newUrl = DRIVER.getCurrentUrl();
            if (oldUrl.indexOf(newUrl) >= 0 || error) {
                MainDriver.fail("URL has not changed. Message error: " +
                        DRIVER.findElement(By.cssSelector(".ui.error.message .content")).getText());
            }
        }
    }

    @Test(testName = "Edit", description = "Edit", priority = 2)
    public void actionEdit() {
        String[] tables = {"itemProduct"};
        String[][] types = {
                {"TextForm", "EST " + new Date().toString().substring(0, 16)},
                {"RelationsForm", "a"},
        };
        for (int j = 0; j < tables.length; j++) {
            MainDriver.getUrl(DRIVER, "/edit/" + tables[j] + "/1");
            MainDriver.myWait(DRIVER, 10, ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".ribbon-text .ui.fluid.input")
            ), true);
            for (int i = 0; i < types.length; i++) {
                typeData(types[i][0], types[i][1]);
            }
            MainDriver.myWait(DRIVER, 5, ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".test-selenium-wait")
            ), false);
            DRIVER.findElement(By.cssSelector(".menu .menu-as-button .menu-as-button-button.positive")).click();
            boolean error = MainDriver.myWait(DRIVER, 10, ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".ui.error.message")
            ), false);
            if (error) {
                MainDriver.fail("Message error: " +
                        DRIVER.findElement(By.cssSelector(".ui.error.message .content")).getText());
            }
        }
    }

    public void typeData(String type, String value) {
        try {
            switch (type) {
                case "RelationsForm":
                    List<WebElement> options;
                    options = DRIVER.findElements(By.cssSelector("." + type + " .search input"));
                    for (int i = 0; i < options.size(); i++) {
                        options.get(i).sendKeys(value);
                        MainDriver.myWait(DRIVER, 3, ExpectedConditions.visibilityOfElementLocated(
                                By.cssSelector(".test-selenium-wait")
                        ), false);
                        DRIVER.findElement(By.cssSelector("." + type + " .search input[name='" + options.get(i).getAttribute("name") + "'] ~ .menu div")).click();
                    }
                    break;
                case "DropdownForm":
                    DRIVER.findElement(By.cssSelector("." + type + " .dropdown")).click();
                    MainDriver.myWait(DRIVER, 1, ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(".test-selenium-wait")
                    ), false);
                    DRIVER.findElement(By.cssSelector("." + type + " .dropdown div ~ .menu div + div")).click();
                    break;
                case "TextForm":
                    sentKey(By.cssSelector(".ribbon-text:not(.ribbon-text-my-style) ." + type + " .ui.fluid.input .ui.fluid.input input"), value);
            }
        } catch (Exception eee) {
        }
        
    }

    public void sentKey(By stringParams, String value) {
        List<WebElement> options;
        options = DRIVER.findElements(stringParams);
        System.out.print(options);
        for (int i = 0; i < options.size(); i++) {
            options.get(i).clear();
            options.get(i).sendKeys(value);
        }
    }
}
