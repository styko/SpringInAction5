package tacos.restclient;

import java.util.Collection;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import tacos.model.Ingredient;

@Service
@Slf4j
public class TacoCloudClient {

	private RestTemplate rest;
	private Traverson traverson;

	public TacoCloudClient(RestTemplate rest, Traverson traverson) {
		this.rest = rest;
		this.traverson = traverson;
	}

	public Ingredient getIngredientById(String ingredientId) {
		return rest.getForObject("http://localhost:8080/api/ingredients/{id}", Ingredient.class, ingredientId);
	}

	public List<Ingredient> getAllIngredients() {
		ParameterizedTypeReference<List<Ingredient>> parameterizedTypeReference = new ParameterizedTypeReference<List<Ingredient>>() {};
		return rest.exchange("http://localhost:8080/api/ingredients", HttpMethod.GET, null, parameterizedTypeReference).getBody();
	}
	
	public Iterable<Ingredient> getAllIngredientsWithTraverson() {
	    ParameterizedTypeReference<Resources<Ingredient>> ingredientType =
	        new ParameterizedTypeReference<Resources<Ingredient>>() {};

	    Resources<Ingredient> ingredientRes = traverson.follow("ingredients").toObject(ingredientType);
	    
	    Collection<Ingredient> ingredients = ingredientRes.getContent();
	          
	    return ingredients;
	  }

}
