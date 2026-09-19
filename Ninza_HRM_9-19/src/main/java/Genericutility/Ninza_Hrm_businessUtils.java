package Genericutility;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Ninza_Hrm_businessUtils {
	public static WebDriver login() throws Exception {
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
		driver.get(p.getProperty("url"));
		driver.findElement(By.id("username")).sendKeys(p.getProperty("username"));
		Thread.sleep(1000);
		driver.findElement(By.id("inputPassword")).sendKeys(p.getProperty("password"));
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[1]")).click();
		return driver;
	}
}
