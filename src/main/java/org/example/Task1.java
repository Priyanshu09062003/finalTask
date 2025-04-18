package org.example;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

public class Task1 {

    public static void main(String[] args)  throws  Exception{
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get("https://www.angelone.in/top-gainers-nse");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,350)");

        // Dismiss popup if present
        try {
            WebElement popup = driver.findElement(By.id("wzrk-cancel"));
            new Actions(driver).moveToElement(popup).click().perform();
        } catch (Exception ignored) {}

        // TreeMap to store gain % → StockInfo
        TreeMap<Double, StockInfo> gainMap = new TreeMap<>();

        for (int i = 1; i <= 10; i++) {
            try {
                // Get % gain
                String percentStr = driver.findElement(
                        By.xpath("//table[@data-table-name='market-trends-topgainers-nse']/tbody/tr[" + i + "]//td[2]//p[2]//span[2]")
                ).getText().replace("(", "").replace(")", "").replace("%", "");
                double percent = Double.parseDouble(percentStr);

                if (percent > 12.00) {
                    // Company name
                    String companyName = driver.findElement(
                            By.xpath("//table[@data-table-name='market-trends-topgainers-nse']/tbody/tr[" + i + "]")
                    ).getAttribute("data-complongname");

                    // XPath to <a> tag
                    String xpathToLink = "//table[@data-table-name='market-trends-topgainers-nse']/tbody/tr[" + i + "]//td[1]//a";

                    // Highlight the row
                    WebElement rowElement = driver.findElement(
                            By.xpath("//table[@data-table-name='market-trends-topgainers-nse']/tbody/tr[" + i + "]")
                    );
                    js.executeScript(
                            "arguments[0].style.cssText += 'background: rgba(144, 238, 144, 0.6); border: 2px solid green; border-radius: 4px;'",
                            rowElement
                    );

                    // Store info
                    gainMap.put(percent, new StockInfo(companyName, xpathToLink));

                    System.out.println(companyName + " : " + percent + "%");
                }
            } catch (Exception e) {
                System.out.println("Skipping row " + i + " due to unexpected structure.");
            }
        }

        // Check if any company matched
        if (gainMap.isEmpty()) {
            System.out.println("❌ No company has gain > 12%");
            driver.quit();
            return;
        }

        // Get highest gainer
        double highestGain = gainMap.lastKey();
        StockInfo topStock = gainMap.get(highestGain);

        System.out.println("\nHighest Gainer Stock: " + topStock.companyName + " (" + highestGain + "%)");

        // Scroll to element before clicking
        WebElement toClick = driver.findElement(By.xpath(topStock.xpath));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'})", toClick);
        toClick.click();

        // Wait and verify
        Thread.sleep(3000);
        String clickedCompany = driver.findElement(By.xpath("//li[contains(@class,'active')]/a/span")).getText();
        if (clickedCompany.equals(topStock.companyName)) {
            System.out.println("Redirected to correct company stock page.");
        } else {
            System.out.println("Redirected to wrong company stock page.");
            driver.quit();
            return;
        }

//        System.out.println(LocalDateTime.now());
        // Take screenshot
        TakesScreenshot ts = (TakesScreenshot) driver;
        File srcFile = ts.getScreenshotAs(OutputType.FILE);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timestamp = dateFormat.format(new Date());
        File destFile = new File("TableValidation_" +timestamp + ".png");
        FileUtils.copyFile(srcFile, destFile);

        System.out.println("Screenshot saved: " + destFile.getName());

//        driver.quit();
    }

}
