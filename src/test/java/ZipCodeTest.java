import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ZipCodeTest {

    WebDriver driver;

    @BeforeMethod
    public void setup(){
        driver = new ChromeDriver();
        driver.get("https://sharelane.com/cgi-bin/register.py");
    }

    @Test
    public void checkNegativeZipCode4digits() {
        driver.findElement(By.name("zip_code")).sendKeys("1234");
        driver.findElement(By.cssSelector("[value='Continue']")).click();
        String errorMessage = driver.findElement(By.cssSelector(".error_message")).getText();
        Assert.assertEquals(errorMessage,"Oops, error on page. ZIP code should have 5 digits");
    }

    @Test
    public void checkPositiveZipCode5digits() {
        driver.findElement(By.name("zip_code")).sendKeys("12345");
        driver.findElement(By.cssSelector("[value='Continue']")).click();
        String valuePage = driver.findElement(By.name("page")).getAttribute("value");
        Assert.assertEquals(valuePage,"2");
    }

    @AfterMethod
    public void quit(){
        driver.quit();
    }
}
