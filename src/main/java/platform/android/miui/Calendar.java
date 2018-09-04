package platform.android.miui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import por.core.Driver;

public class Calendar {

    public static void decreaseDate(Driver driver,int numOfDays) {
        driver.switchTo(Driver.CONTEXT.NATIVE);
        WebElement button=driver.get(By.id("android:id/decrement"));
        for(int i=0;i<numOfDays;++i)
            button.click();
        driver.switchTo(Driver.CONTEXT.WEBVIEW);
    }

    public static void accept(Driver driver) {
        driver.switchTo(Driver.CONTEXT.NATIVE);
        driver.get(By.id("android:id/button1")).click();
        driver.switchTo(Driver.CONTEXT.WEBVIEW);
    }
}
