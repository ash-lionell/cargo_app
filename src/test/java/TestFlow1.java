import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import platform.android.miui.Calendar;
import por.core.Driver;
import por.imports.CargoArrivalDetails;
import por.imports.CheckIn;
import por.imports.CheckInSummary;

import java.net.MalformedURLException;

//imports static Menu.MENU_ITEM;


public class TestFlow1 {

    Driver driver;

    @BeforeSuite
    public void setup(){
        try {
            DesiredCapabilities caps=DesiredCapabilities.android();
            caps.setCapability("platformName","Android");
            caps.setCapability("appPackage","com.champ.cargosystemsapp");
            caps.setCapability("appActivity","MainActivity");
            driver = new Driver(caps);
            Assert.assertTrue(Header.isHeaderLoaded(driver),"App did not load within "+Driver.TIMEOUT+" seconds.");
        } catch (MalformedURLException mue) {
            String errMsg="ERROR : Unable to start the application on the target device. Aborting.";
            System.out.println(errMsg);
            Assert.fail(errMsg);
        }
    }

    @Test
    public void test(){
        /*Set<String> ctxts=((AndroidDriver) driver.driver).getContextHandles();
        Iterator it=ctxts.iterator();
        while (it.hasNext()) {
            String ctxt=it.next().toString();
            System.out.println("context : "+ctxt);
            if(ctxt.contains("WEBVIEW"))
                ((AndroidDriver) driver.driver).context(ctxt);}*/

        System.out.println("main thread : "+Thread.currentThread().getId());

        Header.openMenu(driver);
        Menu.select(driver, Menu.MENU_ITEM.IMPORT,Menu.MENU_ITEM.CHECK_IN);
        //Assert.assertEquals(Header.getScreenName(driver),Menu.MENU_ITEM.CHECK_IN,"Check-In screen was not displayed.");

        Header.openOptions(driver);
        Options.select(driver,Options.OPTIONS.USING_API);

        CheckIn.enterFlightNo(driver,"XS800");
        driver.get(CheckIn.proceedBtn).click();

        CheckInSummary.CargoArrivalDetails.open(driver);
        CargoArrivalDetails.DocumentArrival.openCalendar(driver);

        Calendar.decreaseDate(driver,2);
        Calendar.accept(driver);

        CargoArrivalDetails.save(driver);
    }
}
