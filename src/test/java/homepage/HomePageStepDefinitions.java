package homepage;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;
import java.util.regex.Pattern;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePageStepDefinitions {

    ChromeDriver webDriver = new ChromeDriver();
    HomePage homePage = new HomePage(webDriver);

    static {
        WebDriverManager.chromedriver().setup();
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

        webDriver.close();
    }

    @When("and item is added to the cart {string} times")
    public void andItemIsAddedToTheCartNumberOfClicksTimes(String numberOfClicksString) {
        WebElement addToCartButton = webDriver
            .findElement(By.cssSelector("#content div:nth-of-type(2) div:nth-of-type(1) div.product-thumb.transition div:nth-of-type(3) button:nth-of-type(1)"));

        int numberOfClicks = Integer.parseInt(numberOfClicksString);

        for (int i = 0; i < numberOfClicks; i++) {
            addToCartButton.click();
            Awaitility.waitAtMost(Duration.ofMillis(200)); //For consistency, sometimes the tests would fail if this is not here
        }
    }

    @Then("I have {string} items in my cart")
    public void iHaveNumberOfItemsItemsInMyCart(String numberOfItemsString) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.textMatches(By.cssSelector("#cart-total"), Pattern.compile(".*item.*")));
        WebElement cartButton = webDriver.findElement(By.cssSelector("#cart-total"));

        String actualNumberOfItems =
            cartButton.getText()
                .trim()
                .split(" ")[0];

        int expected = Integer.parseInt(numberOfItemsString);
        int actual = Integer.parseInt(actualNumberOfItems);

        if (!(expected == actual)) {
            fail();
        }

        webDriver.close();
    }
}
