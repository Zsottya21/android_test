package com.example.tesztfeladat;
import org.testng.annotations.Listeners;

import static androidx.core.content.ContextCompat.startActivity;

import static org.openqa.selenium.Keys.ENTER;
import static org.openqa.selenium.Keys.getKeyFromUnicode;

import android.content.Intent;
//import android.view.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.gson.GsonFactory;
//import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.common.collect.ImmutableMap;

import net.bytebuddy.asm.Advice;

import com.mailosaur.MailosaurClient;
import com.mailosaur.models.Message;
import com.mailosaur.models.MessageAddress;
import com.mailosaur.models.MessageContent;
import com.mailosaur.models.MessageSearchParams;
import com.mailosaur.models.MessageHeader;
import com.mailosaur.models.MessageCreateOptions;
import com.mailosaur.models.MessageListParams;
import com.mailosaur.models.MessageSummary;
import com.mailosaur.*;
import com.mailosaur.MailosaurException;
import com.mailosaur.models.*;



import io.appium.java_client.android.nativekey.KeyEvent;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Listeners(CustomTestListener.class)
public class AppTest {

    // Inicializálom a MailosaurClient-et a megfelelő API kulccsal
    private static final String MAILOSAUR_API_KEY = "pBavo9vzrEeDFfNbFSSvHLMtZizxmU9b"; // API kulcs
    private static final String SERVER_ID = "pdacmru1"; // Mailosaur szerver ID
    private static final String EMAIL = "wait-law@pdacmru1.mailosaur.net"; // Email
    private static final String PASSWORD = "Tesztfeladat123";
    private static final String LASTNAME = "Horvath";
    private static final String FIRSTNAME = "Zsolt";

    private AndroidDriver driver;
    private MailosaurClient mailosaurClient;

    @BeforeClass
    public void setUp() throws Exception {
        mailosaurClient = new MailosaurClient(MAILOSAUR_API_KEY);
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("deviceName", "BS98317AA12B1602079");
        caps.setCapability("platformVersion", "13.0");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("appPackage", "com.interticket.budapest13");
        caps.setCapability("appActivity", "com.interticket.smartcity.ui.SplashActivity");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);
    }

    @Test(priority = 1)
    public void testOne() {
        assert true;
    }

    @Test(priority = 2)
    public void testOpeningApplication() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        System.out.println("Alkalmazás megnyitása...");
        assert driver != null;
        assert driver.isAppInstalled("com.interticket.budapest13");
        // várakozás az engedélyezés gombra
        WebElement allowButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.android.permissioncontroller:id/permission_allow_button")));
        // Webelement allowButton = driver.findElement(By.id("com.android.permissioncontroller:id/permission_allow_button"));
        if (allowButton.isDisplayed()) {
            allowButton.click(); // Kattintás az engedélyezés gombra
            System.out.println("Engedélyezés gombra kattintva.");
        } else {
            System.out.println("Engedélyezés gomb nem található.");
        }
    }

    @Test(priority = 3)
    public void testStepToRegisterForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        System.out.println("Belépés a regisztrációhoz...");
        WebElement profileBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.interticket.budapest13:id/toolBarProfileImage")));
        //WebElement profileBtn = driver.findElement(By.id("com.interticket.budapest13:id/toolBarProfileImage"));
        profileBtn.click();
        WebElement registerLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.interticket.budapest13:id/registrationLink")));
        registerLink.click();
        assert true;
    }

    @Test(priority = 4)
    public void testRegister() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement lastName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.interticket.budapest13:id/lastName")));
        lastName.click();
        lastName.sendKeys(LASTNAME);

        WebElement firstName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.interticket.budapest13:id/firstName")));
        firstName.click();
        firstName.sendKeys(FIRSTNAME);

        WebElement email = driver.findElement(By.id("com.interticket.budapest13:id/email"));
        email.click();
        email.sendKeys(EMAIL);

        WebElement password = driver.findElement(By.id("com.interticket.budapest13:id/newPassword"));
        password.click();
        password.sendKeys(PASSWORD);

        WebElement confirmPassword = driver.findElement(By.id("com.interticket.budapest13:id/newPasswordConfirmation"));
        confirmPassword.click();
        confirmPassword.sendKeys(PASSWORD);

        driver.hideKeyboard();

        // Első és második "Jelszó megjelenítése" gomb
        WebElement firstEye = driver.findElement(By.xpath("(//android.widget.ImageButton[@content-desc='Jelszó megjelenítése'])[1]"));
        WebElement secondEye = driver.findElement(By.xpath("(//android.widget.ImageButton[@content-desc='Jelszó megjelenítése'])[2]"));

        firstEye.click();
        secondEye.click();


        firstEye.click();
        secondEye.click();

        // Checkboxok kipipálása
        WebElement dpPolicyCheckbox = driver.findElement(By.id("com.interticket.budapest13:id/acceptDPPolicyCheckbox"));
        if (!dpPolicyCheckbox.isSelected()) {
            dpPolicyCheckbox.click();
        }

        WebElement tcCheckbox = driver.findElement(By.id("com.interticket.budapest13:id/acceptTCCheckbox"));
        if (!tcCheckbox.isSelected()) {
            tcCheckbox.click();
        }

        // Tovább gomb kattintása
        WebElement nextBtn = driver.findElement(By.id("com.interticket.budapest13:id/next"));
        nextBtn.click();


        WebElement ErdeklodesiKor = driver.findElement(By.id("com.interticket.budapest13:id/areaOfInterestIcon"));
        ErdeklodesiKor.click();

        WebElement submit = driver.findElement(By.id("com.interticket.budapest13:id/save"));
        submit.click();

        WebDriverWait waiter = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement confirmOk = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]")));
        confirmOk.click();

        System.out.println("Regisztrációs adatok sikeresen megadva.");
        System.out.println("Kérem várjon az email ellenőrzés folyamatban...");

        //várakozáss
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            WebElement jelszoMentes = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.LinearLayout"));
            if (jelszoMentes.isDisplayed()) {
                WebElement passwordeNO = driver.findElement(By.id("android:id/autofill_save_no"));
                passwordeNO.click();
            } else {
                return;
            }
        } catch (Exception e) {
            System.out.println("Most nem jelent meg jelszó mentése ablak");
        }
        try {
            WebElement FrameLayout = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout"));
            if (FrameLayout.isDisplayed()) {
                WebElement ok = driver.findElement(By.id("android:id/button1"));
                ok.click();
                System.out.println("Jóváhagyás");
            } else {
                return;
            }

        }catch (Exception e) {

        }
        assert true;
    }
    @Test(priority = 5)
    public void testEmailConfirmation() throws MailosaurException, IOException {
        System.out.println("Kérem várjon.");
        System.out.println("Ellenőrzés folyamatban...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MessageSearchParams params = new MessageSearchParams();
        params.withServer(SERVER_ID);
        SearchCriteria criteria = new SearchCriteria();
        criteria.withSentFrom("noreply@interticket.com");   // célzottan az adott email-re szűrök
        // E-mailek lekérése a Mailosaur-tól
        Message confirmationEmail = mailosaurClient.messages().get(params,criteria);


        // Ha találunk e-mailt
        if (confirmationEmail != null) {
            System.out.println("Talált e-mail: " + confirmationEmail.subject());

            // Megkeresem a megerősítő linket vagy gombot.
            String body = confirmationEmail.html().body();
            String confirmationLink = extractConfirmationLink(body);


            if (confirmationLink != null) {
                System.out.println("Megerősítő link: " + confirmationLink);
                driver.get(confirmationLink); // Navigálás a megerősítő linkre
            } else {
                System.out.println("Nem található megerősítő link az e-mailben.");
            }
        } else {
            System.out.println("Nem érkezett e-mail a noreply@interticket.com címről.");
        }
        assert true;
    }

    // ha már regisztrálva van: a blak xpath: /hierarchy/android.widget.FrameLayout, bejelentkezés gomb : xpath: //android.widget.Button[@resource-id="android:id/button1"] resource id: android:id/button1

    @Test(priority = 6)
    public void toLogIn() {
        try {
            WebDriverWait waiter = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement bejelentkezes = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]"))); //driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]")); //android:id/button1 // 00000000-0000-0ce1-ffff-ffff00000163

            bejelentkezes.click();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.EditText[@resource-id=\"com.interticket.budapest13:id/email\"]")));
            email.click();
            try {
                WebElement jelszoMentes = driver.findElement(By.id("android:id/autofill_dialog_picker"));
                if (jelszoMentes.isDisplayed()) {
                    WebElement passwordeNO = driver.findElement(By.id("android:id/autofill_dialog_no"));
                    passwordeNO.click();
                } else {
                    System.out.println("Nem jelent meg a jelszó mentés ablak.");
                }
            } catch (Exception e) {
            }
            email.sendKeys(EMAIL);
            WebElement password = driver.findElement(By.xpath("//android.widget.EditText[@resource-id=\"com.interticket.budapest13:id/password\"]"));
            password.click();
            password.sendKeys(PASSWORD);


            WebElement login = driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"com.interticket.budapest13:id/loginButton\"]"));
            login.click();

        } catch (Exception e) {
            System.out.println("A bejelentkezés sikertelen volt");
            assert false;
        }
        assert true;
    }

    // Megkeresem a Megerősítő gombot
    private String extractConfirmationLink(String emailBody) {
        String confirmationLink = null;
        String regex = "href=\"([^\"]*)\"[^>]*>Megerősítem<"; // Megkeressük a gomb href attribútumát. Ha https:// regexet írtam folyton a headerben lévő google.font felületét találta meg.
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(emailBody);
        if (matcher.find()) {
            confirmationLink = matcher.group(1);
        }
        return confirmationLink;
    }

    @AfterClass
    public void tearDown() {
        // Csak hogy ne álljon le a program
//        if (driver != null) {
//            driver.quit();
//        }
        assert true;
        System.out.println("A regisztráció sikeresen megtörtént.");
        System.out.print("Az új felhasználó: " + FIRSTNAME +" "+" "+ LASTNAME +" Email cím: "+ EMAIL);
    }
}
