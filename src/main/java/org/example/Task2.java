package org.example;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class Task2 {
    public static void main(String[] args) throws Exception {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get("https://vinothqaacademy.com/iframe/");

        WebElement iframe1 = driver.findElement(By.name("employeetable"));
        driver.switchTo().frame(iframe1);

        driver.findElement(By.id("nameInput")).sendKeys("Priyanshu");
        Thread.sleep(2000);
        driver.switchTo().defaultContent();
//        WebElement iframe2 =driver.findElement(By.name("popuppage"));
        Thread.sleep(3000);
        driver.switchTo().frame("popuppage");
        WebElement alertbox = driver.findElement(By.name("alertbox"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", alertbox);
//        alertbox.click();
        Alert alert = driver.switchTo().alert();
        alert.accept();

        driver.switchTo().defaultContent();
        WebElement iframe3 = driver.findElement(By.name("registeruser"));
        driver.switchTo().frame(iframe3);
        Thread.sleep(2000);
        driver.findElement(By.name("vfb-7")).sendKeys("Chaudhari");
        driver.switchTo().defaultContent();

        js = (JavascriptExecutor) driver;
        String mainWindow = driver.getWindowHandle();
        js.executeScript("window.open('https://formy-project.herokuapp.com/form', '_blank');");

// Wait a bit to make sure the tab loads
        Thread.sleep(2000);

        // You might want to switch to the new tab
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Open the form URL

            // Fill out form
            driver.findElement(By.id("first-name")).sendKeys("John");
            driver.findElement(By.id("last-name")).sendKeys("Doe");
            driver.findElement(By.id("job-title")).sendKeys("Software Engineer");

            // Select radio button
            driver.findElement(By.id("radio-button-2")).click();

            // Select checkbox
            driver.findElement(By.id("checkbox-1")).click();

            // Select from dropdown
            Select select = new Select(driver.findElement(By.id("select-menu")));
            select.selectByVisibleText("5-9");

            // Pick a date
            WebElement dateField = driver.findElement(By.id("datepicker"));
            dateField.sendKeys("04/25/2025");

            // Submit the form
            driver.findElement(By.cssSelector(".btn.btn-lg.btn-primary")).click();

            // Wait to see the result
            Thread.sleep(3000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
//            driver.quit();
            String text = driver.findElement(By.xpath("//div[@role='alert']")).getText();
            if (text.equals("The form was successfully submitted!")) {
                System.out.println("Form Submitted Successfully");
                TakesScreenshot ts = (TakesScreenshot) driver;
                File srcFile = ts.getScreenshotAs(OutputType.FILE);
                File destFile = new File("iFrameValidationForm.png");
                FileUtils.copyFile(srcFile, destFile);

                System.out.println("Screenshot saved: FormValidation.png");
            } else {
                System.out.println("Form is not submitted");
                driver.quit();
            }
            driver.switchTo().window(mainWindow);
            driver.quit();
        }
    }

    }
