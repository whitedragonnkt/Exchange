package com.labanana.exchange.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.labanana.exchange.entity.Order;

public interface OrderRepositorio extends JpaRepository<Order, Long> {	
	
	@Query("select o from Order o where UPPER(o.type) = 'BID' and UPPER(o.status) = 'PENDIENTE' and value >= ?1 order by value desc")
	public List<Order> findByTypeBID(Float value);
	
	@Query("select o from Order o where UPPER(o.type) = 'ASK' and UPPER(o.status) = 'PENDIENTE' and value <= ?1 order by value asc")
	public List<Order> findByTypeASK(Float value);
	
	@Query("select o from Order o where UPPER(o.status) = 'PENDIENTE' and remaining > 0")
	public List<Order> findByRemaining();
}
