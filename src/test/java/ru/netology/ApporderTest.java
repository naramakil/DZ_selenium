package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApporderTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTest() {
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Василий Иванов");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("+79270000000");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id = 'order-success']"));
        assertTrue(result.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", result.getText().trim());
    }

    @Test
    void validationEmptyFieldName() {
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("+79270000000");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Поле обязательно для заполнения", result.getText().trim());
    }

    @Test
    void validationFieldNameWithNotCyrilic() {
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Vasiliy Ivanov");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("+79270000000");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", result.getText().trim());
    }

    @Test
    void validationEmptyPhoneName() {
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Василий Иванов");
        driver.findElement(By.cssSelector("[data-test-id = 'agreement']")).click();
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Поле обязательно для заполнения", result.getText().trim());
    }

    @Test
    void validationEmptyCheckBox() {
        driver.findElement(By.cssSelector("[data-test-id = 'name'] input")).sendKeys("Василий Иванов");
        driver.findElement(By.cssSelector("[data-test-id = 'phone'] input")).sendKeys("+79270000000");
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id = 'agreement'].input_invalid"));
        assertTrue(result.isDisplayed());
    }

}

