package homepage;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePageStepDefinitions {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration SMALL_TIMEOUT = Duration.ofSeconds(1);

    ChromeDriver webDriver = new ChromeDriver();
    HomePage homePage = new HomePage(webDriver);

    private final WebDriverWait wait = new WebDriverWait(webDriver, DEFAULT_TIMEOUT);

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

        webDriver.close();

        if (!(expected == actual)) {
            fail();
        }
    }

    @When("the same item is added to the wishlist {string} times")
    public void theSameItemIsAddedToTheWishlistNumberOfClicksTimes(String numberOfClicksString) {
        WebElement addToWishListButton = webDriver
            .findElement(By.cssSelector("#content > div.row > div:nth-child(1) > div > div.button-group > button:nth-child(2)"));

        int numberOfClicks = Integer.parseInt(numberOfClicksString);

        for (int i = 0; i < numberOfClicks; i++) {
            addToWishListButton.click();
        }
    }

    @Then("I have {string} items in my wishlist")
    public void iHaveNumberOfItemsItemsInMyWishlist(String numberOfItemsString) {

        webDriver.manage().window().setPosition(new Point(0, 0));
        webDriver.manage().window().maximize();

        String expected = "Wish List (" + Integer.parseInt(numberOfItemsString) + ")";

        wait.withTimeout(SMALL_TIMEOUT).until(ExpectedConditions.textToBePresentInElement(
            webDriver.findElement(
                By.cssSelector("#wishlist-total > span")),
            expected));
        WebElement wishListButton = webDriver.findElement(By.cssSelector("#wishlist-total > span"));

        String actual = wishListButton.getText();

        webDriver.close();

        if (!(expected.equals(actual))) {
            fail();
        }

    }

    @And("the currency dropdown is clicked")
    public void theCurrencyDropdownIsClicked() {
        var currencySelectorButton = webDriver.findElement(By.cssSelector("#form-currency > div > button"));
        currencySelectorButton.click();
    }

    @When("the {string} currency option is selected")
    public void theCurrencyTypeCurrencyOptionIsSelected(String currencyType) {
        var selectionsContainingSymbol = webDriver.findElements(By.cssSelector("#form-currency > div > ul > li > button"))
            .stream()
            .filter(selection -> selection.getText().contains(currencyType))
            .collect(Collectors.toList());

        if (selectionsContainingSymbol.size() > 1) {
            fail();
            throw new RuntimeException("More than one items found in the list containing the character " + currencyType);
        }

        selectionsContainingSymbol.stream()
            .findFirst().orElseThrow(() -> new RuntimeException("No item found in the list containing the character " + currencyType))
            .click();
    }

    @Then("the currency type switches to {string}")
    public void theCurrencyTypeSwitchesToCurrencyType(String currencyType) {
        var currencySelectorButton = webDriver.findElement(By.cssSelector("#form-currency > div > button"));

        if (!currencySelectorButton.getText().contains(currencyType)) {
            fail();
        }
    }

    @And("prices are displayed in {string}")
    public void pricesAreDisplayedInCurrencyType(String currencyType) {
        var itemPrice = webDriver.findElement(By.cssSelector("#content > div.row > div:nth-child(1) > div > div.caption > p.price"));

        if (!itemPrice.getText().contains(currencyType)) {
            fail();
        }
    }

    @And("the cart has its currency set to {string}")
    public void theCartHasItsCurrencySetToCurrencyType(String currencyType) {
        var cartButton = webDriver.findElement(By.cssSelector("#cart-total"));

        if (!cartButton.getText().contains(currencyType)) {
            fail();
        }

        webDriver.close();
    }
}
