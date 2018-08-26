import org.openqa.selenium.By;
import por.core.Driver;

public class Header {
    //private static final String $REF="//android.view.View[@resource-id='searchBarHeader']/android.view.View";
    private static final String $REF="//android.view.View[android.widget.Button[following-sibling::android.view.View[android.widget.Button]]]";
    public static By REF = By.xpath($REF);

    //public static By menuIcn = By.xpath("./android.widget.Button");
    public static By menuIcn = By.className("android.widget.Button");
    //public static By menuIcn = By.xpath("//button[contains(@class,'bar-button-menutoggle')]");
    public static By screenNameLbl = By.xpath($REF+"/android.view.View[2]/*[1]");
    public static By optionsIcn = By.xpath($REF+"/android.view.View/android.widget.Button");

    public static boolean openMenu(Driver driver) {
        try {
            driver.get(menuIcn,REF).click();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean isHeaderLoaded(Driver driver) {
        return driver.get(menuIcn,REF).isDisplayed();
    }
    public static boolean openOptions(Driver driver) {
        try {
            driver.get(optionsIcn,REF).click();
            return true;
        } catch (Exception e) {
            System.out.println("ERROR : "+e.getMessage());
            return false;
        }
    }

    public static String getScreenName(Driver driver) {
        return driver.get(screenNameLbl).getText();
    }
}
