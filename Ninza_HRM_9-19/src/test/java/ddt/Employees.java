package ddt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFWorkbookFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Genericutility.ExcelUtility;
import Genericutility.Ninza_Hrm_businessUtils;
import Genericutility.PropertyUtility;
import Genericutility.SeleniumUtils;

public class Employees {
	public static void main(String[] args) throws Exception {
		WebDriver driver = Ninza_Hrm_businessUtils.login();
		Thread.sleep(3000);
		FileInputStream fis=new FileInputStream("./src/test/resources/emp_data60.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		ExcelUtility exutil=new ExcelUtility();
		String cdname=exutil.getCellData(wb,"Sheet1", 7, 0);
		System.out.println(cdname);
		String csname=exutil.getCellData(wb,"Sheet1", 7, 0);
		String cfname=exutil.getCellData(wb,"Sheet1", 7, 1);
		String cdEmail=exutil.getCellData(wb,"Sheet1", 7, 1);
		String cMail=exutil.getCellData(wb,"Sheet1", 7, 0);
		String cdPhone=exutil.getCellData(wb,"Sheet1", 7, 2);
		String cdUsername=exutil.getCellData(wb,"Sheet1", 7, 3);
		String cddesig=exutil.getCellData(wb,"Sheet1", 7, 4);
		String cdExp=exutil.getCellData(wb,"Sheet1", 7, 5);
		driver.findElement(By.xpath("//a[text()='Employees']")).click();
		driver.findElement(By.xpath("//span[text()='Add New Employee']")).click();
		driver.findElement(By.xpath("//div[@role='dialog']//div[@class='form-group']/label[contains(text(), 'Name')]/following-sibling::input")).sendKeys(cdname);
		driver.findElement(By.xpath("//div[@role='dialog']//div[@class='form-group']/label[contains(text(), 'Email')]/following-sibling::input")).sendKeys(cdEmail);
		driver.findElement(By.xpath("//div[@role='dialog']//div[@class='form-group']/label[contains(text(), 'Phone')]/following-sibling::input")).sendKeys(cdPhone);
		driver.findElement(By.xpath("//div[@role='dialog']//div[@class='form-group']/label[contains(text(), 'Username')]/following-sibling::input")).sendKeys(cdUsername);
		driver.findElement(By.xpath("//div[@role='dialog']//div[@class='form-group']/label[contains(text(), 'Designation')]/following-sibling::input")).sendKeys(cddesig);
		driver.findElement(By.xpath("//div[@role='dialog']//div[@class='form-group']/label[contains(text(), 'Experience')]/following-sibling::input")).sendKeys(cdExp);
		SeleniumUtils util = new SeleniumUtils();
		WebElement dd=driver.findElement(By.xpath("//div[@role='dialog']//div[@class='form-group']/label[contains(text(), 'Project')]/following-sibling::select"));
		util.selectByVisibleText(dd, "Zee1");
		driver.findElement(By.xpath("//input[@value='Add']")).click();
		exutil.newcellcreate(wb,0, 0, 6, "Emp_id");
		Thread.sleep(3000);
		String Emp_id=driver.findElement(By.xpath("//div[@class='table-wrapper']//following::tbody/descendant::td[text()='"+cdUsername+"']/preceding-sibling::td[2]")).getText();
		exutil.newcellcreate(wb,0, 7, 6, Emp_id);
		FileOutputStream fos=new FileOutputStream("./src/test/resources/emp_data60.xlsx");
		wb.write(fos);
		
	}
}


