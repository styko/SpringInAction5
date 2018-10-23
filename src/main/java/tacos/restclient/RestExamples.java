package tacos.restclient;

import java.net.URI;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import tacos.model.Ingredient;

@SpringBootConfiguration
@ComponentScan
@Slf4j
public class RestExamples {

	public static void main(String[] args) {
		SpringApplication.run(RestExamples.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public Traverson traverson() {
		Traverson traverson = new Traverson(URI.create("http://localhost:8080/api"), MediaTypes.HAL_JSON);
		return traverson;
	}

	@Bean
	public CommandLineRunner fetchIngredients(TacoCloudClient tacoCloudClient) {
		return args -> {
			log.info("----------------------- GET -------------------------");
			log.info("GETTING INGREDIENT BY IDE");
			log.info("Ingredient:  " + tacoCloudClient.getIngredientById("CHED"));
			log.info("GETTING ALL INGREDIENTS");

			Iterable<Ingredient> allIngredientsWithTraverson = tacoCloudClient.getAllIngredientsWithTraverson();

			for (Ingredient ingredient : allIngredientsWithTraverson) {
				log.info("   - " + ingredient);
			}
			
			//List<Ingredient> ingredients = tacoCloudClient.getAllIngredients();

			/*
			 * log.info("All ingredients:"); for (Ingredient ingredient : ingredients) {
			 * log.info("   - " + ingredient); }
			 */
		};
	}

}
