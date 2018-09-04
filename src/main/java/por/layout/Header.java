package por.layout;

import org.openqa.selenium.By;
import por.core.Driver;

public class Header {
    //private static final String $REF="//android.view.View[@resource-id='searchBarHeader']/android.view.View";
    private static final String $REF="//android.view.View[android.widget.Button[following-sibling::android.view.View[android.widget.Button]]]";
    //public static By REF = By.xpath($REF);
    public static By REF = By.id("searchBarHeader");

    //public static By menuIcn = By.className("android.widget.Button");
    public static By menuIcn = By.xpath("//button[contains(@class,'bar-button-menutoggle')]");

    public static By screenNameLbl = By.xpath($REF+"/android.view.View[2]/*[1]");

    //public static By optionsIcn = By.xpath($REF+"/android.view.View/android.widget.Button");
    public static By optionsIcn = By.xpath(".//ion-buttons[contains(@class,'bar-buttons')]");

    public static void openMenu(Driver driver) {
        driver.get(menuIcn,REF).click();
        driver.sleep(Driver.INTERACTION_TIMEOUT);
        driver.cc("Menu opened");
    }

    public static boolean isHeaderLoaded(Driver driver) {
        return driver.get(menuIcn,REF).isDisplayed();
    }
    public static void openOptions(Driver driver) {
        driver.get(optionsIcn,REF).click();
        driver.cc("Options opened");
    }

    public static String getScreenName(Driver driver) {
        return driver.get(screenNameLbl).getText();
    }
}
