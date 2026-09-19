package ddt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.google.common.base.MoreObjects.ToStringHelper;

public class PropertyFile {
	public static void main(String[] args) throws Exception {

		FileInputStream fis = new FileInputStream("./src/test/resources/CommonData.properties");
		Properties p = new Properties();
		p.load(fis);
		WebDriver driver;

		if (p.getProperty("browser").equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions();

			// Disable Chrome's built-in password manager / save-password prompts
			Map<String, Object> prefs = new HashMap<>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			prefs.put("profile.password_manager_leak_detection", false); // this is the key one for the breach-warning
																			// popup
			options.setExperimentalOption("prefs", prefs);

			// Belt-and-braces: also disable via command-line switches
			options.addArguments("--disable-features=PasswordLeakDetection,AutofillServerCommunication");
			options.addArguments("--disable-save-password-bubble");

			driver = new ChromeDriver(options);
		} else if (p.getProperty("browser").equalsIgnoreCase("firefox"))
			driver = new FirefoxDriver();
		else if (p.getProperty("browser").equalsIgnoreCase("edge"))
			driver = new EdgeDriver();
		else
			driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));

		driver.get(p.getProperty("url"));
		driver.findElement(By.id("username")).sendKeys(p.getProperty("username"));
		Thread.sleep(1000);
		driver.findElement(By.id("inputPassword")).sendKeys(p.getProperty("password"));
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[1]")).click();
		Thread.sleep(3000);
//        Robot robot = new Robot();
//        robot.keyPress(KeyEvent.VK_ESCAPE);
//        robot.keyRelease(KeyEvent.VK_ESCAPE);

		// 2.create project
		driver.findElement(By.xpath("//a[contains(text(),'Projects')]")).click();
		driver.findElement(By.xpath("//span[contains(text(),'Create Project')]")).click();

		// 3.Using internal test datas
		fis = new FileInputStream("./src/test/resources/emp_data.xlsx");
		Workbook wb = WorkbookFactory.create(fis);

		// randomnumber for new projectname
		Random r = new Random();
		int num = r.nextInt();

		String pName = wb.getSheetAt(0).getRow(1).getCell(0).toString() + num;
		String pManager = wb.getSheetAt(0).getRow(1).getCell(1).toString();
		String pStatus = wb.getSheetAt(0).getRow(1).getCell(2).toString();
		driver.findElement(By.xpath("//input[@name='projectName']")).sendKeys(pName);
		driver.findElement(By.xpath("//input[@name='createdBy']")).sendKeys(pManager);
		Select sel = new Select(driver.findElement(By.xpath("(//select[@name='status'])[2]")));
		sel.selectByVisibleText(pStatus);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		// get project id
		String projectID = driver.findElement(By.xpath("//td[text()='" + pName + "']/preceding-sibling::td")).getText();
		wb.getSheetAt(0).getRow(1).createCell(3).setCellValue(projectID);
		wb.getSheetAt(0).getRow(1).getCell(0).setCellValue(pName);
		wb.getSheetAt(0).getRow(0).createCell(3).setCellValue("Project_id");

		// writing it in a sheet
		FileOutputStream fos = new FileOutputStream("./src/test/resources/emp_data.xlsx");
		wb.write(fos);
	}
}