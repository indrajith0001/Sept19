package Genericutility;

import java.util.Properties;

import org.openqa.selenium.WebDriver;

public class PropertyUtility {
	WebDriver driver;
	Properties properties;
	public String getproperty(String key) {
		return properties.getProperty(key);
	}
}
