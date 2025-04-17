package org.example;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task1 {
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
        driver.manage().window().maximize();

//        JavascriptExecutor jsx = (JavascriptExecutor) driver;
//        jsx.executeScript("window.scroll(0, 250)");
//
//        WebElement element = driver.findElement(By.xpath("//input[@id='firstName']"));
//        element.sendKeys("priyanshu");
//        List<TestData> testdataSets = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("TestData");

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
        row.createCell(5).setCellValue("10/15/1990");
        row.createCell(6).setCellValue("Maths");
        row.createCell(7).setCellValue("Sports");
        row.createCell(8).setCellValue("123 Main Street, New York");
        row.createCell(9).setCellValue("Maharashtra");
        row.createCell(10).setCellValue("Dhule");

        row = sheet.createRow(2);
        row.createCell(0).setCellValue("Prem");
        row.createCell(1).setCellValue("Chaudhari");
        row.createCell(2).setCellValue("xyz@gmail.com");
        row.createCell(3).setCellValue("Male");
        row.createCell(4).setCellValue("7706841466");
        row.createCell(5).setCellValue("10/15/2003");
        row.createCell(6).setCellValue("Science");
        row.createCell(7).setCellValue("Reading");
        row.createCell(8).setCellValue("123 Main Street, New York");
        row.createCell(9).setCellValue("Maharashtra");
        row.createCell(10).setCellValue("Dhule");


        try {
            FileOutputStream out = new FileOutputStream("output1.xlsx");
            workbook.write(out);
            out.close();
            workbook.close();
        }
        catch (Exception e)
        {
            System.out.println("Exception ");
        }

        List<List<String>> testdata = new ArrayList<>();

        FileInputStream file = new FileInputStream("output1.xlsx");
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

        // Wait for page to load
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName")));

        // 3. Fill out the form dynamically
        // Text fields

        driver.findElement(By.xpath("//input[@id='firstName']")).sendKeys(testdata.get(0).get(0));
        driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys(testdata.get(0).get(1));
        driver.findElement(By.xpath("//input[@placeholder='name@example.com']")).sendKeys(testdata.get(0).get(2));
//        click the radio button
        WebElement element = driver.findElement(By.xpath("label[text()='" + testdata.get(0).get(3) + "']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);

        driver.findElement(By.xpath("//input[@placeholder='Mobile Number']")).sendKeys(testdata.get(0).get(4));

        WebElement date = driver.findElement(By.xpath("//input[@id='dateOfBirthInput']"));
        js.executeScript("arguments[0].value = '"+testdata.get(0).get(5) +"';", date);


//        // Checkboxes for hobbies
//        for (String hobby : testData.getHobbies()) {
//            String hobbyXPath = "//label[text()='" + hobby + "']";
//            WebElement hobbyCheckbox = driver.findElement(By.xpath(hobbyXPath));
//            js.executeScript("arguments[0].click();", hobbyCheckbox);
//        }
//
//        // 4. Select date from date picker using JavaScript Executor
//        WebElement datePickerField = driver.findElement(By.id("dateOfBirthInput"));
//        js.executeScript("arguments[0].click();", datePickerField);
//
//        // Select date using JS (format: MM/dd/yyyy)
//        js.executeScript(
//                "document.getElementById('dateOfBirthInput').value = '" + testData.getDateOfBirth() + "';"
//        );
//        // Click elsewhere to close the date picker
//        driver.findElement(By.id("firstName")).click();
//
//        // Dropdown selections
//        WebElement subjectsInput = driver.findElement(By.id("subjectsInput"));
//        for (String subject : testData.getSubjects()) {
//            subjectsInput.sendKeys(subject);
//            wait.until(ExpectedConditions.visibilityOfElementLocated(
//                    By.xpath("//div[contains(@class, 'subjects-auto-complete__menu')]")));
//            driver.findElement(By.xpath("//div[contains(@class, 'subjects-auto-complete__option')]")).click();
//        }
//
//        // Select state and city dropdowns
//        WebElement stateDropdown = driver.findElement(By.id("state"));
//        js.executeScript("arguments[0].scrollIntoView(true);", stateDropdown);
//        js.executeScript("arguments[0].click();", stateDropdown);
//
//        WebElement stateOption = driver.findElement(By.xpath("//div[text()='" + testData.getState() + "']"));
//        js.executeScript("arguments[0].click();", stateOption);
//
//        WebElement cityDropdown = driver.findElement(By.id("city"));
//        js.executeScript("arguments[0].click();", cityDropdown);
//
//        WebElement cityOption = driver.findElement(By.xpath("//div[text()='" + testData.getCity() + "']"));
//        js.executeScript("arguments[0].click();", cityOption);
//
//        // 5. Submit the form
//        WebElement submitButton = driver.findElement(By.id("submit"));
//        js.executeScript("arguments[0].scrollIntoView(true);", submitButton);
//        js.executeScript("arguments[0].click();", submitButton);
//
//        // Validate the confirmation message
//        WebElement confirmationHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//div[contains(@class, 'modal-title') and contains(text(), 'Thanks')]")));
//
//        boolean isConfirmationDisplayed = confirmationHeader.isDisplayed();
//        System.out.println("Form submitted successfully: " + isConfirmationDisplayed);
//
//        if (isConfirmationDisplayed) {
//            // 6. Capture a screenshot of the confirmation message
//            takeScreenshot(driver, "confirmation_" + testData.getFirstName() + "_" + testData.getLastName() + ".png");
//
//            // Close the confirmation modal to proceed to the next test data
//            WebElement closeButton = driver.findElement(By.id("closeLargeModal"));
//            js.executeScript("arguments[0].click();", closeButton)
//        Task1 task1 = new Task1();
//        task1.populate(testdataSets);

//        for(TestData testDataSet : testdataSets)
//        {
//            driver.findElement(By.xpath("//input[@id='firstName']")).sendKeys(testDataSet.getFirstName());
//            driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys(testDataSet.getLastName());
//            driver.findElement(By.xpath("//input[@placeholder='name@example.com']")).sendKeys(testDataSet.getEmail());
//            driver.findElement(By.xpath("//input[@placeholder='Mobile Number']")).sendKeys(testDataSet.getMobileNumber());
//
//
//        }

    }



}
