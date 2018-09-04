package por.layout;

import org.openqa.selenium.By;
import por.core.Driver;

public class Options {
    public static By REF = By.xpath("//ion-popover");

    public static By optionItm(String optionName) {
        //return By.xpath("//android.view.View[contains(@content-desc,'"+optionName+"')]");
        return By.xpath(".//ion-item[starts-with(normalize-space(),'"+optionName+"')]");
    }

    public static final class OPTIONS {
        public static final String USING_API = "Using API";
        public static final String USING_MOCK = "Using Mock";
        public static final String NOTIFICATIONS = "Notifications";
    }

    public static void select(Driver driver, String optionName) {
        driver.get(optionItm(optionName),REF).click();
        driver.sleep(Driver.INTERACTION_TIMEOUT);
    }
}
