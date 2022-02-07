import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Test {
	private WebDriver driver;

	public Test() {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.cwb.gov.tw/V8/C/W/Town/Town.html?TID=6300900");
	}

	public int getAT() {
		driver.navigate().refresh();
		Document doc = Jsoup.parse(driver.getPageSource());
		Element e = doc.select(
				"body > div.wrapper > main > div > div:nth-child(2) > div.row > div.col-sm-6.col-md-6 > div > div > div:nth-child(2) > div > div:nth-child(2) > span > span.tem-C.is-active")
				.first();
		driver.close();
		return Integer.parseInt(e.text());
	}

}
