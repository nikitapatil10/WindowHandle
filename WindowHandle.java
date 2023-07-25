package demo;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;

public class WindowHandle {
    static ChromeDriver driver;
    public void windowHandle()
    {
        this.driver = TestCases.getDriver();
        // Navigate to the url  https://www.w3schools.com/jsref/tryit.asp?filename=tryjsref_win_open
        driver.get("https://www.w3schools.com/jsref/tryit.asp?filename=tryjsref_win_open");
        // Click on the "Try it" button Using Locator "XPath" //button[text()='Try it']
        driver.findElement(By.xpath("//*[text()='Accept all & visit the site']")).click();
        // Click on the "Try it" button Using Locator "XPath" //button[text()='Try it']
        driver.switchTo().frame("iframeResult");
        driver.findElement(By.xpath("//button[text()='Try it']")).click();
        // Printing the url of new window which opened after clicking on "Try it" button  
        Set<String> windows = driver.getWindowHandles();
        Iterator<String> it = windows.iterator();
        String parent = it.next();
        String child = it.next();
        // Printing the title of new window which opened after clicking on "Try it" button  
        driver.switchTo().window(child);
        System.out.println("The title of the newly opened window : "+ driver.getTitle());
     
        // Taking the screen shot of new window which opened after the cliking on "Try it" button  
        takeScreenshot("openWindow", "newly opened window screenshot");
        // Switching back to the original window  
        driver.close();
        driver.switchTo().window(parent);

    }
    public static void takeScreenshot(String screenshotType, String description) {
        try {
            File theDir = new File("/screenshots");
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            String timestamp = String.valueOf(java.time.LocalDateTime.now());
            String fileName = String.format("screenshot_%s_%s_%s.png", timestamp, screenshotType, description);
            TakesScreenshot scrShot = ((TakesScreenshot) driver);
            File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
            File DestFile = new File("screenshots/" + fileName);
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}