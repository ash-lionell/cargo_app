package por.imports;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import por.core.Driver;

public class CheckIn {
    //public static By flightNoTxt = By.xpath("//android.widget.EditText[@text='Search Flight']");
    public static By flightNoTxt = By.xpath("//input[@placeholder='Search flight']");

    public static By flightNoSuggestionsLbl(String flightNum) {
        //return By.xpath("//android.view.View[@content-desc='"+flightNum.toUpperCase()+"']");
        return By.xpath("//ion-content[contains(@class,'awb-list')]//ion-item[normalize-space()='"+flightNum.toUpperCase()+"']");
    }
    public static void enterFlightNo(Driver driver, String flightNo) {
        //driver.get(CheckIn.flightNumTxt).sendKeys("XS800");
        //driver.get(CheckIn.flightNoTxt).click();
        //((AndroidDriver) driver.driver).getKeyboard().sendKeys("XS800");
        //((AndroidDriver) driver.driver).pressKeyCode(AndroidKeyCode.KEYCODE_S);
        //((MobileElement) driver.get(CheckIn.flightNoTxt)).setValue(flightNo);
        driver.get(CheckIn.flightNoTxt).sendKeys(flightNo);
        try {
            WebElement suggestion=driver.get(flightNoSuggestionsLbl(flightNo),Driver.STRATEGY.QUICK);
            if(suggestion.isDisplayed())
                driver.get(flightNoSuggestionsLbl(flightNo),Driver.STRATEGY.QUICK).click();
            else
                ((AndroidDriver) driver.driver).hideKeyboard();
        } catch (NoSuchElementException nsee) {
            ((AndroidDriver) driver.driver).hideKeyboard();
        }


    }

    //public static By proceedBtn = By.xpath("//android.widget.Button[normalize-space(@content-desc)='PROCEED']");
    public static By proceedBtn = By.xpath("//button[normalize-space()='Proceed']");
}