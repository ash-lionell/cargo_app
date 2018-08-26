import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class Test{
    public static void main(String args[]) throws MalformedURLException {
        //System.out.println("Hello World!");
        DesiredCapabilities caps=DesiredCapabilities.android();
        caps.setCapability("platformName","Android");
        caps.setCapability("deviceName","mydevice");
        caps.setCapability("appPackage","com.miui.calculator");
        caps.setCapability("appActivity","cal.NormalCalculatorActivity");
        WebDriver driver=new RemoteWebDriver(new URL("http://localhost:4723/wd/hub"),caps);

        driver.findElement(By.id("com.miui.calculator:id/btn_1")).click();
        driver.findElement(By.id("com.miui.calculator:id/btn_plus")).click();
        driver.findElement(By.id("com.miui.calculator:id/btn_2")).click();
        driver.findElement(By.id("com.miui.calculator:id/btn_equal")).click();
    }
}