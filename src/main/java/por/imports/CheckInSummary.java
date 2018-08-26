package por.imports;

import org.openqa.selenium.By;
import por.core.Driver;

public class CheckInSummary {
    public static By cargoArrivalDetailsLbl = By.xpath("//android.view.View[@content-desc='Cargo Arrival Details']");

    public static class CargoArrivalDetails {
        public static void open(Driver driver) {
            System.out.println("sub thread : "+Thread.currentThread().getId());
            driver.get(cargoArrivalDetailsLbl).click();
        }
        public static boolean isCompleted(Driver driver) {
            return true;
        }
    }
}
