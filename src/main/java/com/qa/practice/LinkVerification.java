package com.qa.practice;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LinkVerification {

	public WebDriver driver;

	@Test
	public void verification() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://www.uvdesk.com");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, 1000);
		WebElement element = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='fetch-order-detail']//img")));
		List<WebElement> elements = driver.findElements(By.tagName("a"));
		for (WebElement webElement : elements) {
			String url = webElement.getAttribute("href");
			if (url == null || url.isEmpty()) {
				System.out.println(url + "   URL is either not configured for anchor tag or it is empty");
				continue;
			} else {
				verifyURL(url);
			}
		}
		driver.quit();
	}

	public void verifyURL(String url) {

		try {
			URL link = new URL(url);
			HttpURLConnection httpConnect = (HttpURLConnection) link.openConnection();
			httpConnect.setConnectTimeout(3000);
			httpConnect.connect();
			if (httpConnect.getResponseCode() == 200) {
				System.out.println(url + " - " + httpConnect.getResponseMessage());
			}
			if (httpConnect.getResponseCode() == 400) {
				System.out.println(url + " - " + httpConnect.getResponseMessage());
			}
			if (httpConnect.getResponseCode() == 500) {
				System.out.println(url + " - " + httpConnect.getResponseMessage());
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}
}
