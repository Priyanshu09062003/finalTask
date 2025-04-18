package org.example;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Task4 {
    public void populate(List<TestData> testDataSets)
    {
        TestData data1 = new TestData();
        data1.setFirstName("John");
        data1.setLastName("Doe");
        data1.setEmail("john.doe@example.com");
        data1.setGender("Male");
        data1.setMobileNumber("1234567890");
        data1.setDateOfBirth("05/20/1995");
        data1.setSubjects("Computer Science and Math");
        data1.setHobbies("Sports");
        data1.setAddress("123 Main Street, New York");
        data1.setState("NCR");
        data1.setCity("Delhi");
        testDataSets.add(data1);

        TestData data2 = new TestData();
        data2.setFirstName("Jane");
        data2.setLastName("Smith");
        data2.setEmail("jane.smith@example.com");
        data2.setGender("Female");
        data2.setMobileNumber("9876543210");
        data2.setDateOfBirth("10/15/1990");
        data2.setSubjects("English and Arts");
        data2.setHobbies("Music");
        data2.setAddress("456 Park Avenue, Boston");
        data2.setState("Haryana");
        data2.setCity("Karnal");
        testDataSets.add(data2);
    }

    public static void main(String[] args) throws Exception{
        WebDriver driver = new ChromeDriver();
//        driver.get("https://demoqa.com/automation-practice-form");


//        JavascriptExecutor jsx = (JavascriptExecutor) driver;
//        jsx.executeScript("window.scroll(0, 250)");
//
//        WebElement element = driver.findElement(By.xpath("//input[@id='firstName']"));
//        element.sendKeys("priyanshu");
//        List<TestData> testdataSets = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("TestData");
//
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Firstname");
        row.createCell(1).setCellValue("Lastname");
        row.createCell(2).setCellValue("Email");
        row.createCell(3).setCellValue("Gender");
        row.createCell(4).setCellValue("Mobile_no");
        row.createCell(5).setCellValue("DOB");
        row.createCell(6).setCellValue("Subject");
        row.createCell(7).setCellValue("Hobby");
        row.createCell(8).setCellValue("Address");
        row.createCell(9).setCellValue("State");
        row.createCell(10).setCellValue("City");

        row = sheet.createRow(1);
        row.createCell(0).setCellValue("Priyanshu");
        row.createCell(1).setCellValue("Chaudhari");
        row.createCell(2).setCellValue("xyz@gmail.com");
        row.createCell(3).setCellValue("Male");
        row.createCell(4).setCellValue("7709841466");
        row.createCell(5).setCellValue("1990-10-15");
        row.createCell(6).setCellValue("Maths");
        row.createCell(7).setCellValue("Sports");
        row.createCell(8).setCellValue("123 Main Street, New York");
        row.createCell(9).setCellValue("NCR");
        row.createCell(10).setCellValue("Delhi");

        row = sheet.createRow(2);
        row.createCell(0).setCellValue("Prem");
        row.createCell(1).setCellValue("Chaudhari");
        row.createCell(2).setCellValue("xyz@gmail.com");
        row.createCell(3).setCellValue("Male");
        row.createCell(4).setCellValue("7706841466");
        row.createCell(5).setCellValue("2003-06-09");
        row.createCell(6).setCellValue("Science");
        row.createCell(7).setCellValue("Reading");
        row.createCell(8).setCellValue("123 Main Street, New York");
        row.createCell(9).setCellValue("Maharashtra");
        row.createCell(10).setCellValue("Dhule");


        try {
            FileOutputStream out = new FileOutputStream("final.xlsx");
            workbook.write(out);
            out.close();
            workbook.close();
        }
        catch (Exception e)
        {
            System.out.println("Exception ");
        }

        List<List<String>> testdata = new ArrayList<>();

        FileInputStream file = new FileInputStream("final.xlsx");
        Workbook workbook1 = new XSSFWorkbook(file);
        Sheet sheet1 = workbook1.getSheet("TestData");

        for (int r = 1; r <= 2; r++) {
            List<String> rowData = new ArrayList<>();
            for (int col = 0; col < 11; col++) {
                String data = sheet1.getRow(r).getCell(col).getStringCellValue();
                rowData.add(data);
            }
            testdata.add(rowData);
        }

        workbook1.close();
        file.close();

        System.out.println(testdata);

        driver.get("https://demoqa.com/automation-practice-form");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Wait for page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName")));

        // 3. Fill out the form dynamically
        // Text fields

        driver.findElement(By.xpath("//input[@id='firstName']")).sendKeys(testdata.get(0).get(0));
        driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys(testdata.get(0).get(1));
        driver.findElement(By.xpath("//input[@placeholder='name@example.com']")).sendKeys(testdata.get(0).get(2));
//        click the radio button
        WebElement element = driver.findElement(By.xpath("//input[@value='" + testdata.get(0).get(3) + "']"));
//        element.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);

        driver.findElement(By.xpath("//input[@placeholder='Mobile Number']")).sendKeys(testdata.get(0).get(4));
//        WebElement date = driver.findElement(By.xpath("//input[@id='dateOfBirthInput']"));
//        js.executeScript("arguments[0].value = '"+testdata.get(0).get(5) +"';", date);
        js.executeScript(
                "document.getElementById('dateOfBirthInput').value = '" + testdata.get(0).get(5) + "';"
        );
        Thread.sleep(3000);
//        js.executeScript("arguments[0].dispatchEvent(new Event('change'))", date);

//        js.executeScript(
//                "arguments[0].value = arguments[1];",
//                date, testdata.get(0).get(5)
//        );

//        driver.findElement(By.xpath("//input[@id='subjectsInput']")).sendKeys(testdata.get(0).get(6));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//div[contains(@class, 'subjects-auto-complete__menu')]")));
//        driver.findElement(By.xpath("//div[contains(@class, 'subjects-auto-complete__option')]")).click();
//label[normalize-space()='Sports']
//        WebElement e =driver.findElement(By.xpath("//label[normalize-space()='"+testdata.get(0).get(7) + "']/parent::input"));
        String hobby = testdata.get(0).get(7);
        WebElement e = null;
        if(hobby.equals("Sports"))
        {
            e =driver.findElement(By.xpath("//input[@id='hobbies-checkbox-1']"));
            js.executeScript("arguments[0].click()",e);
        }
        else if(hobby.equals("Reading"))
        {
            e =driver.findElement(By.xpath("//input[@id='hobbies-checkbox-2']"));
            js.executeScript("arguments[0].click()",e);
        }
        else {
            e =driver.findElement(By.xpath("//input[@id='hobbies-checkbox-3']"));
            js.executeScript("arguments[0].click()",e);
        }

        if(!e.isSelected())
        {
            e.click();
        }
        driver.findElement(By.xpath("//textarea[@id='currentAddress']")).sendKeys(testdata.get(0).get(8));
        Thread.sleep(2000);

        WebElement stateDropdown = driver.findElement(By.xpath("//input[@id='react-select-3-input']"));
        stateDropdown.sendKeys(testdata.get(0).get(9));
        stateDropdown.sendKeys(Keys.ENTER);

        WebElement cityDropDown =driver.findElement(By.xpath("//input[@id='react-select-4-input']"));
        cityDropDown.sendKeys(testdata.get(0).get(10));
        cityDropDown.sendKeys(Keys.ENTER);

        Thread.sleep(2000);
        WebElement submitButton = driver.findElement(By.id("submit"));
        js.executeScript("arguments[0].scrollIntoView(true);", submitButton);
        Thread.sleep(2000);
        js.executeScript("arguments[0].click();", submitButton);

        // Validate the confirmation message
        WebElement confirmationHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'modal-title') and contains(text(), 'Thanks')]")));

        boolean isConfirmationDisplayed = confirmationHeader.isDisplayed();
        System.out.println("Form submitted successfully: " + isConfirmationDisplayed);
        Thread.sleep(2000);
//        js.executeScript("window.scrollTo(0, 250)");
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        TakesScreenshot ts = (TakesScreenshot) driver;
        File srcFile = ts.getScreenshotAs(OutputType.FILE);
        File destFile = new File("FormValidation.png");
        FileUtils.copyFile(srcFile, destFile);

        System.out.println("Screenshot saved: FormValidation.png");

    }



}