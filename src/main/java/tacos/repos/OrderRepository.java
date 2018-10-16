package tacos.repos;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import tacos.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
	List<Order> findByDeliveryZip(String deliveryZip);
	
	List<Order> readOrdersByDeliveryZipAndPlacedAtBetween( String deliveryZip, Date startDate, Date endDate);
}