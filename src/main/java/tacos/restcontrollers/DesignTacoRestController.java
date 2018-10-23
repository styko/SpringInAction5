package tacos.restcontrollers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.DummyInvocationUtils;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tacos.controllers.DesignTacoController;
import tacos.model.Taco;
import tacos.repos.TacoRepository;

@RestController
@RequestMapping(path = "/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class DesignTacoRestController {
	private TacoRepository tacoRepo;

	@Autowired
	private EntityLinks entityLinks;

	public DesignTacoRestController(TacoRepository tacoRepo) {
		this.tacoRepo = tacoRepo;
	}

	@GetMapping("/recent")
	public Resources<Resource<Taco>> recentTacos() {
		PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
		List<Taco> tacos = tacoRepo.findAll(page).getContent();
		Resources<Resource<Taco>> recentResources = Resources.wrap(tacos); recentResources.add(
				ControllerLinkBuilder.linkTo(DummyInvocationUtils.methodOn(DesignTacoRestController.class).recentTacos()) .withRel("recents"));
		return recentResources;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
		Optional<Taco> optTaco = tacoRepo.findById(id);
		if (optTaco.isPresent()) {
			return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
}
