package por.layout;

import org.openqa.selenium.By;
import por.core.Driver;

public class Menu {
    //public static By REF = By.xpath("(//android.view.View[@content-desc='por.layout.Menu']/ancestor::android.view.View)[last()-3] | " +
    //        "(//android.view.View[@content-desc='Import']/ancestor::android.view.View[last()-1]/android.view.View/android.widget.Button/ancestor::android.view.View)[last()-3]");
    public static By REF = By.xpath("//ion-menu");

    public static final class MENU_ITEM {
        public final static String EXPORT = "Export";
        public final static String IMPORT = "Import";

        public final static String CHECK_IN = "Check-In";
        public final static String DELIVERIES_N_TRANSFERS = "Deliveries & Transfers";

        public final static String AIRLINE_TRANSFER = "Airline Transfer";
    }

    public static By menuItm(String menuItemName) {
        //return By.xpath("(.//android.view.View[@content-desc='"+menuItemName+"']/ancestor::android.view.View)[last()-2]");
        return By.xpath(".//ion-item[normalize-space()='"+menuItemName+"']");
    }

    public static void select(Driver driver, String... menuItemNames) {
        for(String menuItemName : menuItemNames) {
            driver.get(menuItm(menuItemName),REF).click();
            driver.sleep(Driver.INTERACTION_TIMEOUT);
            driver.cc("Selected menu item -> "+menuItemName.toUpperCase());
        }
    }
}
