package pages;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SubsectionPage {

	WebDriver driver;
	//static List<WebElement> foodList = new ArrayList<WebElement>();
	static double totalPrice =0;

	public SubsectionPage(WebDriver driver) {
		this.driver = driver;
	}

	public void getAllAvailableFoodInCart() {

		// To insert Location you have to select on "Add" button
		driver.findElement(By.xpath("/html/body/div[7]/div[1]/main/div[4]/div[1]/div[3]/div[2]/ol/li[1]/div[2]/div/div/div/form/div/div[2]/a")).click();
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		driver.findElement(By.xpath("/html/body/div[8]/aside[1]/div[2]/div/div/div/div[2]/div[2]/div[2]/ul/li[29]/a")).click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		// Get products in subsection page
		List<WebElement> foodList = new ArrayList<WebElement>();
		WebElement list = driver.findElement(By.cssSelector("ol.products.list.items.product-items"));


		//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		foodList = list.findElements(By.cssSelector("li.item"));

		//System.out.println("The size of food List is: "+ foodList.size());

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Need to scroll to avoid "Chat with us"
		//(Incase of Add fourth element to cart, the Add button is hidden under "Chat With Us")
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,200)");

		// Add All Element to Cart except Sold out ones
		for (int i = 0; i < foodList.size(); i++) {
			if((foodList.get(i).getText().toString().contains("SOLD OUT")))
			{
				continue;
			}
			else {
				// Click on Add button
				foodList.get(i).findElement(By.cssSelector("div.pull-right")).click();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}	

		// To get Price of Each dish
		for (int j = 0; j < foodList.size(); j++) {
			if((foodList.get(j).getText().toString().contains("SOLD OUT")))
			{
				continue;
			}
			else if ((foodList.get(j).getText().toString().contains("SPECIAL OFFER")))
			{
				// If item price is greater than 999
				if((foodList.get(j).findElement(By.cssSelector("span.special-price")).getText().toString().contains(",")))
				{
					String[] price = foodList.get(j).findElement(By.cssSelector("span.special-price")).getText().toString().split("P");
					String[] price2 = price[1].split(",");
					double first = Double.parseDouble(price2[0]) * 1000;
					double Second = Double.parseDouble(price2[1]);
					totalPrice += first + Second;
					
				}else{
					String[] price = foodList.get(j).findElement(By.cssSelector("span.special-price")).getText().toString().split("P");
					//System.out.println("Price is: " + price[1]);
					totalPrice += Double.parseDouble(price[1]);
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				}

			}
			// to handle the only special case in all subsections
			// This Product is in Lobster & Crab named Mathias Frozen Cooked King Crab Legs
			else if ((foodList.get(j).findElement(By.cssSelector("span.price")).getText().toString().contains(",")))
			{
				String[] price = foodList.get(j).findElement(By.cssSelector("span.price")).getText().toString().split("P");
				String[] price2 = price[1].split(",");
				double first = Double.parseDouble(price2[0]) * 1000;
				double Second = Double.parseDouble(price2[1]);
				totalPrice += first + Second;
			}
			else {
				//System.out.println(food.get(j).findElement(By.cssSelector("span.price")).getText().toString().substring(3));
				String[] price = foodList.get(j).findElement(By.cssSelector("span.price")).getText().toString().split("P");
				//System.out.println("Price is: " + price[1]);
				totalPrice += Double.parseDouble(price[1]);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			}
			//System.out.println(totalPrice);
		}

		//System.out.println("Total Price: " + totalPrice);

	}

	public String checkTotalPrice() {
		
		//Click on Cart
		driver.findElement(By.xpath("/html/body/div[7]/div[1]/div[1]/header/div[1]/div[3]/a")).click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		return (("EGP" + new DecimalFormat("#,##0.00").format(totalPrice)));
	}
}
