package tacos.repos;

import org.springframework.data.repository.PagingAndSortingRepository;

import tacos.model.Taco;

public interface TacoRepository extends PagingAndSortingRepository<Taco, Long>  {
	
}
