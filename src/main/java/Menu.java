import org.openqa.selenium.By;
import por.core.Driver;

public class Menu {
    public static By REF = By.xpath("(//android.view.View[@content-desc='Menu']/ancestor::android.view.View)[last()-3] | " +
            "(//android.view.View[@content-desc='Import']/ancestor::android.view.View[last()-1]/android.view.View/android.widget.Button/ancestor::android.view.View)[last()-3]");

    public static final class MENU_ITEM {
        final static String EXPORT = "Export";
        final static String IMPORT = "Import";

        final static String CHECK_IN = "Check-In";
    }

    public static By menuItm(String menuItemName) {
        return By.xpath("(.//android.view.View[@content-desc='"+menuItemName+"']/ancestor::android.view.View)[last()-2]");
    }

    public static boolean select(Driver driver, String... menuItemNames) {
        try {
            for(String menuItemName : menuItemNames) {
                driver.get(menuItm(menuItemName)).click();
                driver.sleep(Driver.INTERACTION_TIMEOUT);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
