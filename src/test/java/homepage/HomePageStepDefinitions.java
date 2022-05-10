package homepage;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.fail;

import homepage.HomePage;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

@Slf4j
public class HomePageStepDefinitions {

    ChromeDriver webDriver = new ChromeDriver();
    HomePage homePage = new HomePage(webDriver);

    static {
        WebDriverManager.chromedriver().setup();
    }

    @After
    void teardown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Given("the home page is opened")
    public void openHomePage() {
        homePage.openPage();
    }

    @When("the checkout button is clicked")
    public void theCheckoutButtonIsClicked() {
        var checkoutButton = webDriver.findElement(By.cssSelector("a[title='Checkout']"));
        checkoutButton.click();
    }

    @Then("the checkout page is opened")
    public void theCheckoutPageIsOpened() {
        String currentUrl = webDriver.getCurrentUrl();

        if (!currentUrl.equals("http://tutorialsninja.com/demo/index.php?route=checkout/cart")) {
            fail();
        }

        teardown();
    }
}
