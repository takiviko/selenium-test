package homepage;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

@Getter
public class HomePage {

    private static final String URL = "http://tutorialsninja.com/demo/";

    private final WebDriver webDriver;

    public HomePage(ChromeDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void openPage() {
        webDriver.get(URL);
        PageFactory.initElements(webDriver, this);
    }

}
