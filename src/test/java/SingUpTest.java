import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SingUpTest {

    WebDriver driver;

    @BeforeMethod
    public void setup(){
        driver = new ChromeDriver();
        driver.get("https://sharelane.com/cgi-bin/register.py");
    }

    @Test
    public void checkPositiveSignUp() {
        driver.findElement(By.name("zip_code")).sendKeys("12345");
        driver.findElement(By.cssSelector("[value='Continue']")).click();
        driver.findElement(By.name("first_name")).sendKeys("Darya");
        driver.findElement(By.name("last_name")).sendKeys("Shishkina");
        driver.findElement(By.name("email")).sendKeys("ShiDa@mail.ru");
        driver.findElement(By.name("password1")).sendKeys("123456789");
        driver.findElement(By.name("password2")).sendKeys("123456789");
        driver.findElement(By.cssSelector("[value='Register']")).click();
        boolean elementSignUpMessage = driver.findElement(By.cssSelector(".confirmation_message")).isDisplayed();
        Assert.assertTrue(elementSignUpMessage);
    }

    @Test
    public void checkNegativeSignUpNoName() {
        driver.findElement(By.name("zip_code")).sendKeys("12345");
        driver.findElement(By.cssSelector("[value='Continue']")).click();
        driver.findElement(By.name("first_name")).sendKeys("");
        driver.findElement(By.name("last_name")).sendKeys("Shishkina");
        driver.findElement(By.name("email")).sendKeys("ShiDa@mail.ru");
        driver.findElement(By.name("password1")).sendKeys("123456789");
        driver.findElement(By.name("password2")).sendKeys("123456789");
        driver.findElement(By.cssSelector("[value='Register']")).click();
        boolean elementErrorMessage = driver.findElement(By.cssSelector(".error_message")).isDisplayed();
        Assert.assertTrue(elementErrorMessage);
    }

    @AfterMethod(alwaysRun = true)
    public void quit(){
        driver.quit();
    }
}
