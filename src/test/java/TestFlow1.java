import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;
import platform.android.miui.Calendar;
import por.core.Driver;
import por.imports.CargoArrivalDetails;
import por.imports.CheckIn;
import por.imports.CheckInSummary;
import por.layout.Header;
import por.layout.Menu;
import por.layout.Options;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiConsumer;

//imports static por.layout.Menu.MENU_ITEM;


public class TestFlow1 {

    Driver driver;

    @BeforeSuite
    public void initializeSuite() throws IOException {
        try {
            System.out.println("started");
            System.out.println("started 2");
            DesiredCapabilities caps=DesiredCapabilities.android();
            caps.setCapability("platformName","Android");
            caps.setCapability("appPackage","com.champ.cargosystemsapp");
            caps.setCapability("appActivity","MainActivity");
            //caps.setCapability("app","E:\\champcargosystems.apk");
            //caps.setCapability("app","C:\\Users\\34150\\champcargosystems\\dist\\champcargosystems.apk");
            caps.setCapability("screenCapture",true);
            caps.setCapability("screencast",true);
            driver = new Driver(caps);
            driver.switchTo(Driver.CONTEXT.WEBVIEW);
            Assert.assertTrue(Header.isHeaderLoaded(driver),"App did not load within "+Driver.TIMEOUT+" seconds.");

            String root=System.getProperty("user.dir");
            String videoLogsPath=root+File.separator+"video-logs";
            File dir=new File(videoLogsPath);
            if(!dir.exists())   dir.mkdir();
            else                FileUtils.cleanDirectory(dir);

            String tempScreenshotsPath=root+File.separator+".temp";
            dir=new File(tempScreenshotsPath);
            if(!dir.exists() || !dir.isDirectory())
                dir.mkdir();
        } catch (MalformedURLException mue) {
            String errMsg="ERROR : Unable to start the application on the target device. Aborting.";
            System.out.println(errMsg);
            Assert.fail(errMsg);
        }
    }

    Process p;
    @BeforeMethod
    public void startTest(Method testMethod) throws IOException {
        System.out.println("starting test : "+testMethod.getName());
        p=Runtime.getRuntime().exec("C:\\Users\\34150\\Downloads\\platform-tools_r28.0.0-windows\\platform-tools\\adb.exe shell screenrecord /sdcard/test.mp4");
    }

    @AfterMethod
    public void stopTest(Method testMethod) throws Exception {
        System.out.println("stopping method : "+testMethod.getName());
        p.destroy();
        String root=System.getProperty("user.dir");
        p=Runtime.getRuntime()
                .exec("C:\\Users\\34150\\Downloads\\platform-tools_r28.0.0-windows\\platform-tools\\adb.exe " +
                        "pull /sdcard/test.mp4 "+root+File.separator+"video-logs"+File.separator+WordUtils.capitalize(testMethod.getName())+".mp4");
        p.waitFor();
        driver.sleep(3000);
        p=Runtime.getRuntime()
                .exec("C:\\Users\\34150\\Downloads\\platform-tools_r28.0.0-windows\\platform-tools\\adb.exe " +
                        "pull /sdcard/screenshots "+root+File.separator+".temp");
        p.waitFor();

        p=Runtime.getRuntime()
                .exec("C:\\Users\\34150\\Downloads\\platform-tools_r28.0.0-windows\\platform-tools\\adb.exe shell " +
                        "rm /sdcard/test.mp4");
        p.waitFor();
        p=Runtime.getRuntime()
                .exec("C:\\Users\\34150\\Downloads\\platform-tools_r28.0.0-windows\\platform-tools\\adb.exe shell " +
                        "rm -rf /sdcard/screenshots/*");
        p.waitFor();
        File dir=new File(root+File.separator+".temp"+File.separator+"screenshots");
        String files[]=dir.list();

        driver.screenshotsNames.forEach(new BiConsumer<String, String>() {
            @Override
            public void accept(String screenshotName, String screenshotFileIdentifier) {
                System.out.println("came here 1 "+screenshotName+" , "+screenshotFileIdentifier);
                try {
                    for (String file : files) {
                        if (file.startsWith(screenshotFileIdentifier)) {
                            Allure.addAttachment(screenshotName, new FileInputStream(root+File.separator+".temp"+File.separator+"screenshots"+File.separator+file));
                        }
                    }
                } catch (Exception e) { e.printStackTrace(); }
            }
        });
        //FileUtils.cleanDirectory(dir);
    }

    @Test
    @Description("Checkin for Cargo Arrival")
    public void testFlow1(){
        System.out.println("main thread : "+Thread.currentThread().getId());

        //driver.sleep(5000);
        driver.cc("Initial screen");

        Header.openMenu(driver);
        Menu.select(driver, Menu.MENU_ITEM.IMPORT,Menu.MENU_ITEM.CHECK_IN);
        //Assert.assertEquals(por.layout.Header.getScreenName(driver),por.layout.Menu.MENU_ITEM.CHECK_IN,"Check-In screen was not displayed.");
        //driver.cc("test");

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

    /*@Test (testName = "test two", description = "this is another test")
    public void testFlow2(){
        System.out.println("main thread : "+Thread.currentThread().getId());

        //driver.sleep(5000);
        driver.cc("Initial screen");

        Header.openMenu(driver);
        Menu.select(driver, *//*Menu.MENU_ITEM.IMPORT,*//*Menu.MENU_ITEM.DELIVERIES_N_TRANSFERS,Menu.MENU_ITEM.AIRLINE_TRANSFER);
        //Assert.assertEquals(por.layout.Header.getScreenName(driver),por.layout.Menu.MENU_ITEM.CHECK_IN,"Check-In screen was not displayed.");
        //driver.cc("test");

        Header.openOptions(driver);
        Options.select(driver,Options.OPTIONS.USING_API);

        CheckIn.enterFlightNo(driver,"XS800");
        driver.get(CheckIn.proceedBtn).click();

        CheckInSummary.CargoArrivalDetails.open(driver);
        CargoArrivalDetails.DocumentArrival.openCalendar(driver);

        Calendar.decreaseDate(driver,2);
        Calendar.accept(driver);

        CargoArrivalDetails.save(driver);
    }*/
}
