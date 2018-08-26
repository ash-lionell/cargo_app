package por.imports;

import org.openqa.selenium.By;
import por.core.Driver;

public class CargoArrivalDetails {

    public static class DocumentArrival {
        private static String $REF = "//android.view.View[@content-desc='Document Arrival']/following-sibling::android.view.View[1]";
        public static String getDate(Driver driver) {
            return null;
        }
        public static void openCalendar(Driver driver) {
            driver.get(By.xpath($REF+"//android.widget.Image[@content-desc='calendar']")).click();
        }
    }

    public static void save(Driver driver) {
        driver.get(By.xpath("//android.widget.Button[normalize-space(@content-desc)='SAVE']")).click();
    }

}
