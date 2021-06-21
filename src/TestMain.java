import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

import static org.junit.Assert.assertTrue;

public class TestMain {

    private AppiumDriver driver;

    //запуск апиума с настройками
    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Emulator");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("appPackage", "ru.tele2.mytele2");
        capabilities.setCapability("appActivity", ".ui.splash.SplashActivity");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("app", "C:\\Users\\arthu\\Desktop\\tele2-automation-app\\apks\\ru.tele2.mytele2.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void mainTest() {
        // 1. Клик на кнопку "Стать абонентом теле2"
        elementClick(
                By.xpath("//*[@resource-id='ru.tele2.mytele2:id/item']"),
                "Кнопка 'Стать абонентом теле2' не найдена",
            15
        );

        // 2. Клик на кнопку из боттом-шита "Активировать SIM-карту"
        elementClick(
                By.xpath("//*[@resource-id='ru.tele2.mytele2:id/functionItem']"),
                "Кнопка 'Активировать SIM-карту' не найдена",
                15
        );

        // 3. Клик на кнопку "Сканировать SIM-карту"
        elementClick(
                By.xpath("//*[@resource-id='ru.tele2.mytele2:id/scanSim']"),
                "Кнопка 'Сканировать SIM-карту' не найдена",
                15
        );

        // 4. Клик на пермишн для доступа к камере
        elementClick(
                By.xpath("//*[@resource-id='com.android.packageinstaller:id/permission_allow_button']"),
                "Доступа к кнопке нет",
                10
        );

        // 5. Клик на кнопку "Ввести данные вручную"
        elementClick(
                By.xpath("//*[@resource-id='ru.tele2.mytele2:id/manualInput']"),
                "Не найдена кнопка 'Ввести данные вручную'",
                10
        );

        // 6. Ввод символов в поле ICC-кода
        elementSendKeys(
                By.xpath("//*[@resource-id='ru.tele2.mytele2:id/editText']"),
                "11111111111111111111",
                "поле для ввода ICC-кода не найдено",
                10
        );

        // 7. Клик на кнопку "Подтвердить"
        elementClick(
                By.xpath("//*[@resource-id='ru.tele2.mytele2:id/confirmButton']"),
                "Кнопка 'Подтвердить' не найдена",
                10
        );


        //отображение текста ошибки на экране
        WebElement article = elementPresent(
                By.xpath("//*[@resource-id='ru.tele2.mytele2:id/text']"),
                "Не найдено описание ошибки",
                15
        );

        //присвоение описанию текста
        String article_title = article.getText();

        //сравнение текста на экране
        Assert.assertEquals(
                "Описание не соответствует действительности",
                "Штрихкод введен неверно. Устраните ошибку и повторите попытку",
                article_title
        );

        //вывод текста в консоль
        System.out.println(article_title);
    }




    private WebElement elementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement elementClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = elementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement elementSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = elementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }



}
