package platform.android.miui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import por.core.Driver;

public class Calendar {

    public static void decreaseDate(Driver driver,int numOfDays) {
        WebElement button=driver.get(By.id("android:id/decrement"));
        for(int i=0;i<numOfDays;++i)
            button.click();
    }

    public static void accept(Driver driver) {
        driver.get(By.id("android:id/button1")).click();
    }
}
