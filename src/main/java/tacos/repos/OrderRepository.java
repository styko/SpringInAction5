package tacos.repos;

import tacos.model.Order;

public interface OrderRepository {
	Order save(Order order);
}