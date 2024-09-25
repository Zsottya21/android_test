package demo;

import org.junit.Test;
import static org.junit.Assert.*;
import io.github.cdimascio.dotenv.Dotenv;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.mailosaur.MailosaurClient;
import com.mailosaur.MailosaurException;
import com.mailosaur.models.*;
import java.io.IOException;
import java.util.UUID;

public class OtpEmailTest {
  @Test public void retrieveOneTimePasscode() throws IOException, MailosaurException {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless=new");
    WebDriver browser = new ChromeDriver(options);

    Dotenv dotenv = Dotenv.load();

    String apiKey = dotenv.get("mailosaurApiKey");
    String serverId = dotenv.get("mailosaurServerId");    

    // Instantiate Mailosaur client with api key
    MailosaurClient mailosaur = new MailosaurClient(apiKey);

    // Random test email address (this uses a catch-all pattern)
    String randomString = UUID.randomUUID().toString().replaceAll("-", "").substring(7, 13);
    String emailAddress = randomString + "@" + serverId + ".mailosaur.net";

    // 1 - Request a one time passcode
    browser.get("https://example.mailosaur.com/otp");
    browser.findElement(By.cssSelector("input#email")).sendKeys(emailAddress);
    browser.findElement(By.cssSelector("button[type='submit']")).click();

    // 2 - Create the search criteria for the email
    // https://mailosaur.com/docs/api/messages#4-search-for-messages
    MessageSearchParams params = new MessageSearchParams();
    params.withServer(serverId);

    SearchCriteria searchCriteria = new SearchCriteria();
    searchCriteria.withSentTo(emailAddress);

    // 3 - Get the email from Mailosaur using the search criteria
    Message message = mailosaur.messages().get(params, searchCriteria);

    assertNotNull(message);
    assertEquals("Here is your access code for ACME Product", message.subject());

    // 4 - Extract the link from the email
    // https://mailosaur.com/docs/test-cases/codes
    Code passcode = message.html().codes().get(0);

    System.out.printf("\nEmail otp code -" + passcode.value()); // "564214"

    browser.quit();
  }
}