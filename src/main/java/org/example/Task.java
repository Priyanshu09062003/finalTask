package org.example;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Task {
    public static void main(String[] args) throws Exception {
        WebDriver driver = new ChromeDriver();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("TestData");
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Firstname");
        row.createCell(1).setCellValue("Lastname");
        row.createCell(2).setCellValue("Job-Title");
        row.createCell(3).setCellValue("Highest level of education");
        row.createCell(4).setCellValue("Gender");
        row.createCell(5).setCellValue("YOE");
        row.createCell(6).setCellValue("Date");

        row = sheet.createRow(1);
        row.createCell(0).setCellValue("Priyanshu");
        row.createCell(1).setCellValue("Chaudhari");
        row.createCell(2).setCellValue("Software Deveolper");
        row.createCell(3).setCellValue("College");
        row.createCell(4).setCellValue("Male");
        row.createCell(5).setCellValue("1990-10-15");
        row.createCell(6).setCellValue("2-4");


        row = sheet.createRow(2);
        row.createCell(0).setCellValue("Prem");
        row.createCell(1).setCellValue("Chaudhari");
        row.createCell(2).setCellValue("Software Deveolper");
        row.createCell(3).setCellValue("College");
        row.createCell(4).setCellValue("Male");
        row.createCell(5).setCellValue("1990-10-15");
        row.createCell(6).setCellValue("2-4");


        try {
            FileOutputStream out = new FileOutputStream("final1.xlsx");
            workbook.write(out);
            out.close();
            workbook.close();
        }
        catch (Exception e)
        {
            System.out.println("Exception ");
        }

        List<List<String>> testdata = new ArrayList<>();

        FileInputStream file = new FileInputStream("final1.xlsx");
        Workbook workbook1 = new XSSFWorkbook(file);
        Sheet sheet1 = workbook1.getSheet("TestData");

        for (int r = 1; r <= 2; r++) {
            List<String> rowData = new ArrayList<>();
            for (int col = 0; col < 7; col++) {
                String data = sheet1.getRow(r).getCell(col).getStringCellValue();
                rowData.add(data);
            }
            testdata.add(rowData);
        }

        workbook1.close();
        file.close();

        System.out.println(testdata);

        driver.get("https://formy-project.herokuapp.com/form");
        driver.manage().window().maximize();

        // Wait for page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.findElement(By.id("first-name")).sendKeys(testdata.get(0).get(0));
        driver.findElement(By.id("last-name")).sendKeys(testdata.get(0).get(1));
        driver.findElement(By.id("job-title")).sendKeys(testdata.get(0).get(2));

        if(testdata.get(0).get(3).equals("High School"))
        {
            driver.findElement(By.id("radio-button-1")).click();
        }
        else if (testdata.get(0).get(3).equals("College"))
        {
            driver.findElement(By.id("radio-button-2")).click();
        }
        else {
            driver.findElement(By.id("radio-button-3")).click();
        }


//        checkbox-1
        if(testdata.get(0).get(4).equals("Male"))
        {
            driver.findElement(By.id("checkbox-1")).click();
        }
        else if (testdata.get(0).get(4).equals("Female"))
        {
            driver.findElement(By.id("checkbox-2")).click();
        }
        else {
            driver.findElement(By.id("checkbox-3")).click();
        }

        WebElement dropdown = driver.findElement(By.id("select-menu"));
        Select s = new Select(dropdown);
        Thread.sleep(2000);
        s.selectByVisibleText(testdata.get(0).get(6).toString());
        WebElement datepicker = driver.findElement(By.id("datepicker"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = '" +testdata.get(0).get(5)+"';", datepicker);

        driver.findElement(By.xpath("//a[normalize-space()='Submit']")).click();

        Thread.sleep(2000);
        String text = driver.findElement(By.xpath("//div[@role='alert']")).getText();
        if(text.equals("The form was successfully submitted!"))
        {
            System.out.println("Form Submitted Successfully");
            TakesScreenshot ts = (TakesScreenshot) driver;
            File srcFile = ts.getScreenshotAs(OutputType.FILE);
            File destFile = new File("FormValidation.png");
            FileUtils.copyFile(srcFile, destFile);

            System.out.println("Screenshot saved: FormValidation.png");
        }
        else {
            System.out.println("Form is not submitted");
            driver.quit();
        }
//        s.selectByVisibleText(testdata.get(0).get(5));
    }
}
