import ActionDrivers.MainDriver;
import org.junit.rules.Timeout;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import javax.lang.model.util.Elements;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class Filters extends MainClasses {
    @Test(testName = "Filter Fields itemProduct", description = "Products", priority = 2)
    public void filterFieldsIP() {
        String[][] tables = {
                {"itemProduct", "sName", "test", "input"},
                {"itemProduct", "sCode", "123", "input"},
                {"itemProduct", "iItemCategoryID", "ca", "listbox"},
                {"itemProduct", "bActive", "0", "dropdown"},
        };
        for (int j = 0; j < tables.length; j++) {
            currentUrl(tables[j], true);
        }
    }

    @Test(testName = "Couple Filter Field Quote", description = "Quote", priority = 2)
    public void coupleFilter() {
        String[][] tables = {
                {"quote", "sNo", "test", "input"},
                {"quote", "sName", "test", "input"},
        };
        for (int j = 0; j < tables.length; j++) {
            currentUrl(tables[j], false);
        }
    }

    @Test(testName = "Filter Field Quote", description = "Quote", priority = 2)
    public void filterFieldsQ() {
        String[][] tables = {
                {"quote", "sNo", "150", "input"},
        };
        for (int j = 0; j < tables.length; j++) {
            currentUrl(tables[j], true);
        }
    }

    public void currentUrl(String[] tables, boolean updateUrl) {
        String table = tables[0];
        if (updateUrl || (!updateUrl && DRIVER.getCurrentUrl().indexOf(tables[0])==-1)) {
            MainDriver.getUrl(DRIVER, "/table/" + tables[0]);
        }
        String param = tables[1];
        String filter = tables[2];
        String type = tables[3];
        By stringParams = By.cssSelector(".column-search [name='" + param + "']");
        boolean filterFailed;
        switch (type) {
            case "listbox":
                stringParams = By.cssSelector(".column-search [name='" + param + "-search']");
                MainDriver.myWait(DRIVER, 10, ExpectedConditions.visibilityOfElementLocated(stringParams), true);
                DRIVER.findElement(stringParams).sendKeys(filter);
                stringParams = By.cssSelector(".column-search [name='" + param + "-search'] ~ .menu div");
                MainDriver.myWait(DRIVER, 1, ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".test-selenium-wait")
                ), false);
                MainDriver.myWait(DRIVER, 5, ExpectedConditions.visibilityOfElementLocated(stringParams), true);
                DRIVER.findElement(stringParams).click();
                MainDriver.myWait(DRIVER, 1, ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".test-selenium-wait")
                ), false);
                break;
            case "dropdown":
                stringParams = By.cssSelector("#search-dropdown-" + param + " div");
                MainDriver.myWait(DRIVER, 10, ExpectedConditions.visibilityOfElementLocated(stringParams), true);
                DRIVER.findElement(stringParams).click();
                stringParams = By.cssSelector("#search-dropdown-" + param + " div ~ .menu div + div");
                MainDriver.myWait(DRIVER, 5, ExpectedConditions.visibilityOfElementLocated(stringParams), true);
                DRIVER.findElement(stringParams).click();
                MainDriver.myWait(DRIVER, 10, ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".test-selenium-wait")
                ), false);
                break;
            default:
                MainDriver.myWait(DRIVER, 10, ExpectedConditions.visibilityOfElementLocated(stringParams), true);
                DRIVER.findElement(stringParams).sendKeys(filter);
                DRIVER.findElement(By.tagName("body")).click();
        }
        String url = DRIVER.getCurrentUrl();
        if (url.indexOf("where") < 0 || url.indexOf(param) < 0) {
            MainDriver.fail("The search parameter " + param + " was not found in the URL");
        } else {
            MainDriver.myWait(DRIVER, 5, ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".test-selenium-wait")
            ), false);
            List<WebElement> options = DRIVER.findElements(By.cssSelector(".ag-cell-value [colid='" + param + "'] .ag-react-container span span"));
            for (int i = 0; i < options.size(); i++) {
                if (options.get(i).getText().indexOf(filter) < 0) {
                    MainDriver.fail("Table " + table + ".The value of the parameter " + param + " in line " + i + " does not match the filter " + filter);
                }
            }

        }
    }

}
