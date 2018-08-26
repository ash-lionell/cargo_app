package por.core;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.functions.ExpectedCondition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

public class Driver {
    public WebDriver driver;
    WebDriverWait wait;

    public static final int TIMEOUT = 15;
    public static final int INTERACTION_TIMEOUT = 500;

    public Driver(DesiredCapabilities capabilities) throws MalformedURLException {
        capabilities.setCapability("deviceName","mydevice");
        //capabilities.setCapability("automationName","UIAutomator2");
        capabilities.setCapability("autoGrantPermissions","true");
        capabilities.setCapability("chromedriverExecutable","C:\\Users\\34150\\Downloads\\chromedriver_win32\\chromedriver.exe");
        //capabilities.setCapability("adbPort","5555");
        capabilities.setCapability("newCommandTimeout",900000);
        driver=new AndroidDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
        wait = new WebDriverWait(driver,TIMEOUT);
    }

    public static enum STRATEGY {
        NORMAL,
        QUICK
    }


    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ie) {}
    }

    private void wait(By locator) {

    }

    public WebElement get(By locator) {
        return get(locator,STRATEGY.NORMAL);
    }
    public WebElement get(By locator,STRATEGY strategy) {
        if(strategy==STRATEGY.NORMAL)
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            } catch (Exception e) {}
        return driver.findElement(locator);
    }

    public WebElement get(By locator,By context) {
        return get(locator,context,STRATEGY.NORMAL);
    }
    public WebElement get(By locator,By context,STRATEGY strategy) {
        //wait = new WebDriverWait(driver,TIMEOUT);
        if(strategy==STRATEGY.NORMAL)
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(context));
            } catch (Exception e) {}
        WebElement contextEl=driver.findElement(context);
        //wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        sleep(INTERACTION_TIMEOUT);
        return driver.findElement(locator);
    }
}
