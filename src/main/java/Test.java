import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test{
    public static void main(String args[]) throws Exception {
        //System.out.println("Hello World!");
        test7();
    }

    public static void test1() throws MalformedURLException {
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

    public static void test2() {
        String versionText="Mozilla/5.0 (Linux; Android 6.0.1; Redmi 4 Build/MMB29M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/55.0.2883.91 Mobile Safari/537.36";
        Pattern $version=Pattern.compile("Chrome\\/(\\d{2})");
        Matcher m=$version.matcher(versionText);
        String version="";
        if(m.find())
            version=m.group(1);
        System.out.println("WEBVIEW version : "+version);
    }

    public static void test3() throws IOException, InterruptedException {
        String envp[]={System.getenv("path")};
        Process p=Runtime.getRuntime()
                .exec("C:\\Users\\34150\\Downloads\\platform-tools_r28.0.0-windows\\platform-tools\\adb.exe shell " +
                "dumpsys package com.google.android.webview");
        p.waitFor();
        BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream()));
        //System.out.println("env : "+System.getenv("path"));
        while(r.ready())
            System.out.print(r.readLine());
    }

    public static void test4() throws Exception {
        Process p=Runtime.getRuntime().exec("cmd C:\\Users\\34150\\Desktop\\champ\\detect_webview_version.bat");
        p.waitFor(4000,TimeUnit.MILLISECONDS);
        BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream()));
        /*while(r.ready())
            System.out.print(r.readLine());*/
        System.out.println("print started...");
        String s ;
        while ((s = r.readLine()) != null) {
            System.out.println(s);
        }
        System.out.println("print completed...");
    }

    public static void test5() throws Exception {
        Process p=Runtime.getRuntime()
                .exec("C:\\Users\\34150\\Downloads\\platform-tools_r28.0.0-windows\\platform-tools\\adb.exe shell screenrecord /sdcard/test.mp4");
        Thread.sleep(4000);
        p.destroy();
    }

    public static void test6() {
        System.out.println("path : "+System.getProperty("user.dir"));
    }

    public static void test7() {
        String videoLogsPath=System.getProperty("user.dir")+File.separator+"video-logs";
        File videoLogsDir=new File(videoLogsPath);
        System.out.println("exists : "+videoLogsDir.exists());
        if(!videoLogsDir.exists() || !videoLogsDir.isDirectory())
            videoLogsDir.mkdir();
    }
}