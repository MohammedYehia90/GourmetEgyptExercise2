package steps;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import junit.framework.Assert;
import pages.HomePage;
import pages.SubsectionPage;

public class StepsDefinition {
	
	WebDriver chrome;
	Random rand = new Random();
	int index = rand.nextInt(10);

	@Given("User in on home page")
	public void user_in_on_home_page() {

		WebDriverManager.chromedriver().setup();	
		chrome = new ChromeDriver();
		chrome.manage().window().maximize();
		chrome.navigate().to("https://www.gourmetegypt.com/");
	}

	@When("User hover on SeaFood category from categories page")
	public void user_hover_on_sea_food_category_from_categories_page() {
		HomePage home = new HomePage(chrome);
		home.clickOnCategories();
	}

	@When("User select specific SeaFood type randomly")
	public void user_select_specific_sea_food_type_randomly() {
		HomePage home = new HomePage(chrome);
		home.selectRandomSeaFood(index);
	}

	@When("User select available all dishes and add them to cart")
	public void user_select_available_all_dishes_and_add_them_to_cart() {
		SubsectionPage page = new SubsectionPage(chrome);
		page.getAllAvailableFoodInCart();
	}

	@Then("User open Cart and check Total price is right")
	public void user_open_cart_and_check_total_price_is_right() {
		SubsectionPage page = new SubsectionPage(chrome);
		String TotalPrice = page.checkTotalPrice();
		Assert.assertEquals(TotalPrice, chrome.findElement(By.xpath("/html/body/div[7]/div[1]/div[1]/header/div[1]/div[3]/div[2]/div/div[1]/div[2]/div[7]/span[3]/span")).getText().toString());
		chrome.quit();
	}
}
