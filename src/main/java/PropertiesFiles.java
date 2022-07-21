import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PropertiesFiles {
	
	WebDriver driver;
	
	
	String browser;
	String url;
	String username;
	String password;
	String fullName;

	
	By userNameWebElementField= By.id("username");
	By passwordWebElementField=By.id("password");
	By loginWebElementField=By.name("login");
	By dashboardWebElementField=By.xpath("//h2[text()=' Dashboard ']");
	By customerWebElementField=By.xpath("//span[text()='Customers']");
	By	addCustomerWebElementField=By.xpath("//a[text()='Add Customer']");
	By fullNameWebElementField=By.id("account");
	By companyWebElementField=By.id("cid");
	
	public int generateRandomNumber() {
		
		Random rnd= new Random();
		int RandonNumber=rnd.nextInt(999); 
		
		return RandonNumber;
	}
	
	
	
	
	@BeforeMethod
public void config() throws IOException {
		
		File f= new File("TestData\\testData.properties");
		Properties prop= new Properties();
		
		FileInputStream fi= new FileInputStream(f);
		prop.load(fi);
		
	browser= prop.getProperty("browser");
	url=prop.getProperty("url");
	username=prop.getProperty("username");
	password=prop.getProperty("password");
	fullName=prop.getProperty("fullName");
	
		
		
		
	}
	
	@Test(priority=1)
	public void init() {
		
	
		if(browser.equalsIgnoreCase("chrome")) {
		
		System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
		driver= new ChromeDriver();
		}
		
		else if(browser.equalsIgnoreCase("edge")) {
			
			System.setProperty("webdriver.edge.driver", "drivers\\msedgedriver.exe");
			driver= new EdgeDriver();
			
		}
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	@Test(priority=2)
	public void test() throws InterruptedException {
		int randomNum=generateRandomNumber();
		
		
		driver.findElement(userNameWebElementField).sendKeys(username);
		driver.findElement(passwordWebElementField).sendKeys(password);
		driver.findElement(loginWebElementField).click();
		
		String actualDashboardText=driver.findElement(dashboardWebElementField).getText();
		String expectedDashboardText="Dashboard";
		
		Assert.assertEquals(actualDashboardText, expectedDashboardText, "page not found");
		
		driver.findElement(customerWebElementField).click();
		driver.findElement(addCustomerWebElementField).click();
		
		Thread.sleep(3000);
		
		String actualAddCustomerPageText=driver.findElement(By.xpath("//h5[text()='Add Contact']")).getText();
		String expectedAddCustomerPageText="Add Contact";
		
		Assert.assertEquals(actualAddCustomerPageText, expectedAddCustomerPageText, "page not found");
		
		driver.findElement(fullNameWebElementField).sendKeys(fullName+randomNum);
		
	WebElement companyWebElement=driver.findElement(companyWebElementField);
	Select sel= new Select(companyWebElement);
	sel.selectByVisibleText("Techfios");
	
	
		
		
		
		
	}
	
	
	
	
}
