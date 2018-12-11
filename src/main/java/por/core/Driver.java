package por.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.functions.ExpectedCondition;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.qameta.allure.Allure;

public class Driver {
    public WebDriver driver;
    private DesiredCapabilities capabilities;
    WebDriverWait wait;

    private boolean captureScreen;
    private boolean recordScreen;

    String context[];

    public static final int TIMEOUT = 15;
    public static int INTERACTION_TIMEOUT = 500;

    public Driver(DesiredCapabilities capabilities) throws MalformedURLException {
        capabilities.setCapability("deviceName","Pixel_API_25");
        capabilities.setCapability("udid","emulator-5554");
        //capabilities.setCapability("automationName","UIAutomator2");
        capabilities.setCapability("autoGrantPermissions","true");
        //capabilities.setCapability("chromedriverExecutable","C:\\Users\\34150\\Downloads\\chromedriver_win32\\chromedriver.exe");
        //capabilities.setCapability("chromedriverExecutable","C:\\Users\\34150\\Downloads\\chromedriver_2.25\\chromedriver.exe");
        //capabilities.setCapability("adbPort","5555");
        this.capabilities=capabilities;
        int webviewVersion=getWebViewVersion();
        setChromeCapability(webviewVersion);
        capabilities.setCapability("newCommandTimeout",900000);

        //driver=new AndroidDriver(new URL("http://localhost:4723/wd/hub"),this.capabilities);
        driver=new AndroidDriver(new URL("http://localhost:3000/wd/hub"),this.capabilities);
        wait = new WebDriverWait(driver,TIMEOUT);

        //driver.switchTo().

        context=new String[2];
        Set<String> ctxts=((AndroidDriver) driver).getContextHandles();
        Iterator it=ctxts.iterator();
        int counter=0;
        while (it.hasNext()) {
            String ctxt=it.next().toString();
            System.out.println("context : "+ctxt);
            if(counter++==0)
                context[0]=ctxt;
            else if(ctxt.contains("WEBVIEW") && ctxt.contains("cargosystemsapp"))
                context[1]=ctxt;
        }
        currentContext=null;

        if(capabilities.is("screenCapture"))
            captureScreen = (boolean) capabilities.getCapability("screenCapture");
        else
            captureScreen=false;
        /*if(captureScreen)
            INTERACTION_TIMEOUT=100;*/
        if(capabilities.is("screencast"))
            recordScreen = (boolean) capabilities.getCapability("screencast");
        else
            recordScreen=false;
    }

    public static enum CONTEXT {
        NATIVE,
        WEBVIEW
    }

    private String currentContext;

    public void switchTo(CONTEXT context){
        //String currContext=((AndroidDriver)driver).getContext();
        //((AndroidDriver)driver).
        String toContext;
        if(context.equals(CONTEXT.NATIVE)) {
            toContext=this.context[0];
            if(currentContext==null || !currentContext.equalsIgnoreCase(toContext))
                ((AndroidDriver) driver).context(toContext);
        }
        else if(context.equals(CONTEXT.WEBVIEW)) {
            toContext=this.context[1];
            if(currentContext==null || !currentContext.equalsIgnoreCase(toContext)) {
                ((AndroidDriver) driver).context(toContext);
                //String versionText=((JavascriptExecutor) driver).executeScript("return navigator.userAgent").toString();
            }
        }
    }

    private int getWebViewVersion() {
        //if(true) return 68;
        System.out.println("came here 1");
        try {
            Process p=Runtime.getRuntime()
                    .exec("C:\\Users\\34150\\Downloads\\platform-tools_r28.0.0-windows\\platform-tools\\adb.exe -s emulator-5554 shell " +
                            "dumpsys package com.google.android.webview");
            p.waitFor();
            System.out.println("came here 2");
            BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream()));
            String versionText=null;
            while(r.ready()) {
                versionText=r.readLine().trim();
                System.out.println(versionText);
                if (versionText.toLowerCase().startsWith("versionname"))
                    break;
            }
            System.out.println("version text : "+versionText);
            if(versionText==null)
                return -1;
            Pattern $version=Pattern.compile("versionName=(\\d{2})");
            Matcher m=$version.matcher(versionText);
            String version="";
            if(m.find())
                version=m.group(1);
            System.out.println("WEBVIEW version : "+version);
            return Integer.parseInt(version);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void setChromeCapability(int version) {
        URL resource=null;
        switch (version) {
            case 55:
                resource=Driver.class.getResource("/chromedrivers/chromedriver_2_28.exe");
                break;
            case 68:
                resource=Driver.class.getResource("/chromedrivers/chromedriver_2_41.exe");
                break;
        }
        try {
            System.out.println("driver path : "+ Paths.get(resource.toURI()).toFile());
            capabilities.setCapability("chromedriverExecutable",Paths.get(resource.toURI()).toFile().toString());
        } catch (Exception urie){
            urie.printStackTrace();
        }
    }

    public static enum STRATEGY {
        NORMAL,
        QUICK
    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ie) {}
    }

    private void wait(By locator) {

    }

    public WebElement get(By locator) {
        return get(locator,STRATEGY.NORMAL);
    }
    public WebElement get(By locator,STRATEGY strategy) {
        if(strategy==STRATEGY.NORMAL)
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            } catch (Exception e) {}
        return driver.findElement(locator);
    }

    public WebElement get(By locator,By context) {
        return get(locator,context,STRATEGY.NORMAL);
    }
    public WebElement get(By locator,By context,STRATEGY strategy) {
        //wait = new WebDriverWait(driver,TIMEOUT);
        if(strategy==STRATEGY.NORMAL)
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(context));
            } catch (Exception e) {}
        WebElement contextEl=driver.findElement(context);
        //wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        Assert.assertTrue("Assertion condition failed.",true);

        sleep(INTERACTION_TIMEOUT);
        return contextEl.findElement(locator);
    }

    public HashMap<String,String> screenshotsNames=new LinkedHashMap<>();
    public void cc(String description) {

        if(!captureScreen)  return;

        /*switchTo(CONTEXT.NATIVE);
        //File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        byte bytes[] = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
        //Allure.addAttachment(description,Files.newInputStream(scrFile));
        Supplier<byte[]> supplier = ()->bytes;
        CompletableFuture f=Allure.addByteAttachmentAsync(description, "image/png", supplier);
        switchTo(CONTEXT.WEBVIEW);*/

        /*switchTo(CONTEXT.NATIVE);
        //((AppiumDriver)driver).execute("screenshot",ImmutableMap.of("args", Lists.newArrayList("/sdcard/screenshots/test"+(++screenshotsCount)+".png")));
        ((AppiumDriver) driver).executeScript("mobile:shell",ImmutableMap.of("command","screenshot","args", Lists.newArrayList("/sdcard/screenshots/test"+(++screenshotsCount)+".png")));
        *//*String info=(String) ((AppiumDriver) driver).executeScript("mobile:batteryInfo");
        System.out.println("info : "+info);*//*
        //ImmutableMap.of("command","screenshot",
        //                        "args", Lists.newArrayList("/sdcard/screenshots/test"+(++screenshotsCount)+".png"))
        switchTo(CONTEXT.WEBVIEW);*/

        long random=System.currentTimeMillis();
        try {
            Process p = Runtime.getRuntime()
                    .exec("C:\\Users\\34150\\Downloads\\platform-tools_r28.0.0-windows\\platform-tools\\adb.exe shell " +
                            "screencap -p /sdcard/screenshots/scr"+random+".png");
            p.waitFor();
            screenshotsNames.put(description,"scr"+random);
        } catch (Exception e) {
            System.out.println("ERROR during SCREEN_CAPTURE : "+e.getMessage());
        }
    }
}
