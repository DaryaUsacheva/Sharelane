import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest {

    WebDriver driver;
    String LogEmail;
    String LogPass;

    @BeforeMethod
    public void setup(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://sharelane.com/cgi-bin/register.py");
        driver.findElement(By.name("zip_code")).sendKeys("12345");
        driver.findElement(By.cssSelector("[value='Continue']")).click();
        driver.findElement(By.name("first_name")).sendKeys("Darya");
        driver.findElement(By.name("last_name")).sendKeys("Shishkina");
        driver.findElement(By.name("email")).sendKeys("ShiDa@mail.ru");
        driver.findElement(By.name("password1")).sendKeys("123456789");
        driver.findElement(By.name("password2")).sendKeys("123456789");
        driver.findElement(By.cssSelector("[value='Register']")).click();

        LogEmail = driver.findElement(By.xpath("//*[contains(text(),'Email')]/../td[2]/b")).getText();
        LogPass = driver.findElement(By.xpath("//*[contains(text(),'Password')]/../td[2]")).getText();
        driver.get("https://sharelane.com/cgi-bin/main.py");
    }

    @Test
    public void checkPositiveLogin() {
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys(LogEmail);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(LogPass);
        driver.findElement(By.xpath("//input[@value='Login']")).click();
        boolean HelloText = driver.findElement(By.xpath("//span[@class='user']")).isDisplayed();
        Assert.assertTrue(HelloText);
    }

    @Test
    public void checkNegativeLoginNoPass() {
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys(LogEmail);
        driver.findElement(By.xpath("//input[@value='Login']")).click();
        String errorMessage = driver.findElement(By.cssSelector(".error_message")).getText();
        Assert.assertEquals(errorMessage,"Oops, error. Email and/or password don't match our records");
    }

    @Test
    public void checkNegativeLoginPassNotCorrect() {
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys(LogEmail);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(LogPass+'1');
        driver.findElement(By.xpath("//input[@value='Login']")).click();
        String errorMessage = driver.findElement(By.cssSelector(".error_message")).getText();
        Assert.assertEquals(errorMessage,"Oops, error. Email and/or password don't match our records");
    }

    @Test
    public void checkNegativeLoginNoEmail() {
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(LogPass);
        driver.findElement(By.xpath("//input[@value='Login']")).click();
        String errorMessage = driver.findElement(By.cssSelector(".error_message")).getText();
        Assert.assertEquals(errorMessage,"Oops, error. Email and/or password don't match our records");
    }

    @Test
    public void checkNegativeLoginEmailNotCorrect() {
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys(LogEmail+'1');
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(LogPass);
        driver.findElement(By.xpath("//input[@value='Login']")).click();
        String errorMessage = driver.findElement(By.cssSelector(".error_message")).getText();
        Assert.assertEquals(errorMessage,"Oops, error. Email and/or password don't match our records");
    }

    @AfterMethod
    public void quit(){
        driver.quit();
    }

}
