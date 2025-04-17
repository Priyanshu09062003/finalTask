//package org.example;
//
//
//
//import org.openqa.selenium.*;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.edge.EdgeDriver;
//import org.openqa.selenium.interactions.Actions;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Final {
//    public static void main(String[] args) throws Exception {
//        WebDriver driver = new EdgeDriver();
//        driver.get("https://amazon.in");
//        driver.manage().window().maximize();
//
//        // Search for 'toys'
//        WebElement input = driver.findElement(By.id("twotabsearchtextbox"));
//        input.sendKeys("toys");
//        input.sendKeys(Keys.ENTER);
//
//        // Scroll down a bit
//        JavascriptExecutor jsx = (JavascriptExecutor) driver;
//        jsx.executeScript("window.scroll(0, 250)");
//
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
//
//        // Click on several items in the search results
//        WebElement item1 = wait.until(ExpectedConditions.elementToBeClickable(By.id("a-autoid-3-announce")));
//        item1.click();
//        Thread.sleep(2000); // Consider replacing with WebDriverWait
//        WebElement item2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("a-autoid-4-announce")));
//        item2.click();
//        Thread.sleep(2000); // Consider replacing with WebDriverWait
//        WebElement item4 = wait.until(ExpectedConditions.elementToBeClickable(By.id("a-autoid-6-announce")));
//        item4.click();
//        Thread.sleep(2000); // Consider replacing with WebDriverWait
//
//        // Go to the cart
//        WebElement cart = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/gp/cart/view.html?ref_=nav_cart']")));
//        cart.click();
//
//        // Store the items in a TreeMap with prices
//        TreeMap<Integer, String> itemsMap = new TreeMap<>();
//        for (int posx = 1; posx < 4; posx++) {
//            try {
//                // Get price for each position
//                String price = driver.findElement(By.xpath("//div[@data-csa-c-posx='" + posx + "']")).getAttribute("data-price");
//                Integer priceInt = Integer.parseInt(price);
//
//                // Store the item's XPath instead of WebElement
//                String itemXPath = "//div[@data-csa-c-posx='" + posx + "']//button[@data-a-selector='decrement']";
//                itemsMap.put(priceInt, itemXPath);
//
//                System.out.println("Price for posx=" + posx + ": " + price);
//                System.out.println("XPath for posx=" + posx + ": " + itemXPath);
//
//            } catch (Exception e) {
//                System.out.println("Error retrieving data for posx=" + posx + ": " + e.getMessage());
//            }
//        }
//
//        JavascriptExecutor js = (JavascriptExecutor)driver;
//        js.executeScript("window.scroll(0,200);");
//        TakesScreenshot ts = (TakesScreenshot) driver;
//
//// Capture the screenshot and store it as a file
//        File srcFile = ts.getScreenshotAs(OutputType.FILE);
//
//// Define the destination file
//        File destFile = new File("before_removal.png");
//
//// Copy the screenshot to the desired location
//        FileUtils.copyFile(srcFile, destFile);
//
//        System.out.println("Screenshot saved: before_removal.png");
//// Get the minimum-priced item
//        Integer minPrice = itemsMap.firstKey();
//        Thread.sleep(5000);
//
//        for (Map.Entry<Integer, String> entry : itemsMap.entrySet()) {
//            if (entry.getKey().equals(minPrice)) continue;
//
//            // Re-locate the element before clicking
//            WebElement itemElement = driver.findElement(By.xpath(entry.getValue()));
//            itemElement.click();
//        }
//
//
//        TakesScreenshot ts1 = (TakesScreenshot) driver;
//
//// Capture the screenshot and store it as a file
//        File srcFile1 = ts1.getScreenshotAs(OutputType.FILE);
//
//// Define the destination file
//        File destFile1 = new File("after_removal.png");
//
//// Copy the screenshot to the desired location
//        FileUtils.copyFile(srcFile1, destFile1);
//
//        System.out.println("Screenshot saved: after_removal.png");
//
//        WebElement inStockMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//span[contains(text(),'In stock')]")
//        ));
//        String stock_Status =inStockMessage.getText();
//        if(stock_Status.equals("In stock"))
//        {
//            System.out.println("The Product is in Stock");
//        }
//        else{
//            System.out.println("The Product is out of Stock");
//            driver.close();
//        }
////        System.out.println(driver.findElement(By.xpath("//span[@class='a-size-small a-color-success sc-product-availability'][text(),' In stock ']")).getText());
//        // Close the driver
//        driver.quit();
//    }
//}
