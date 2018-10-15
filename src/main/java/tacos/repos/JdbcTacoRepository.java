package tacos.repos;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import tacos.model.Ingredient;
import tacos.model.Taco;

@Repository
public class JdbcTacoRepository implements TacoRepository {

	private JdbcTemplate jdbc;
	private SimpleJdbcInsert tacoInserter;

	@Autowired
	public JdbcTacoRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
		this.tacoInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco").usingGeneratedKeyColumns("id");
	}

	@Override
	public Taco save(Taco taco) {
		long tacoId = saveTacoInfo(taco);
		taco.setId(tacoId);
		for (Ingredient ingredient : taco.getIngredients()) {
			saveIngredientToTaco(ingredient, tacoId);
		}
		return taco;
	}

	private long saveTacoInfo(Taco taco) {
		taco.setCreatedAt(new Date());
		
		Map<String, Object> values = new HashMap<>();
		values.put("name", taco.getName());
		values.put("createdAt", taco.getCreatedAt());
		
		return tacoInserter.executeAndReturnKey(values).longValue();
	}

	private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
		jdbc.update("insert into Taco_Ingredients (taco, ingredient) " + "values (?, ?)", tacoId, ingredient.getId());
	}
}
