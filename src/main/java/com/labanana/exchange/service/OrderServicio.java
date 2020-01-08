package com.labanana.exchange.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.labanana.exchange.entity.Order;
import com.labanana.exchange.entity.Transaction;
import com.labanana.exchange.repository.OrderRepositorio;
import com.labanana.exchange.util.Status;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServicio {
	
	private final OrderRepositorio orderRepositorio;
		
	@Transactional
	public List<Order> obtenerOrdenesPorID(List<Long> ids) {
		return orderRepositorio.findAllById(ids);
	}
	
	@Transactional
	public List<Order> obtenerOrdenesBID(Float precio) {
		return orderRepositorio.findByTypeBID(precio);
	}
	
	@Transactional
	public List<Order> obtenerOrdenesASK(Float precio) {
		return orderRepositorio.findByTypeASK(precio);
	}
	
	@Transactional
	public Boolean esOrdenBID(Order order) {
		return order.getType().toUpperCase().equals("BID");
	}
	
	@Transactional
	public Boolean esOrdenASK(Order order) {
		return order.getType().toUpperCase().equals("ASK");
	}
	
	public Boolean verificarOrdenes(List<Order> orders) {
		for (Order order : orders) {
			Optional<Order> verificarOrder = orderRepositorio.findById(order.getId());
			if (verificarOrder.isPresent()) {
				return true;
			}
		}	
		return false;
	}
	
	@Transactional
	public void ejecutarOrdenes(List<Order> orders) {
		for (Order order : orders) {			
			guardarOrden(order);
			ejecutarTransaccion(order);
		}
	}
	
	@Transactional
	public void guardarOrden(Order order) {
		orderRepositorio.save(order);
	}
	
	@Transactional
	public void ejecutarTransaccion(Order order) {
		if (esOrdenBID(order)) {
			ejecutarTransaccionBID(order);
		} 
		
		if (esOrdenASK(order)) {
			ejecutarTransaccionASK(order);
		}	
	}
	
	@Transactional
	public void ejecutarTransaccionBID(Order order) {
		List<Order> ordenesASK = obtenerOrdenesASK(order.getValue());
		System.out.println("OrderServicio:registrarTransaccionBID: " + ordenesASK.toString());
		
		if (ordenesASK.isEmpty()) {
			return;
		}
		
		Transaction transaction = new Transaction();
		for (Order ordenASK : ordenesASK) {			
			if (order.getRemaining().equals(ordenASK.getRemaining())) {
				order.setRemaining(0f);
				order.setStatus(Status.EJECUTADO.toString());
				ordenASK.setRemaining(0f);
				ordenASK.setStatus(Status.EJECUTADO.toString());
			}
			
			if (order.getRemaining() <  ordenASK.getRemaining()) {
				ordenASK.setRemaining(ordenASK.getRemaining() - order.getRemaining());
				order.setRemaining(0f);
				order.setStatus(Status.EJECUTADO.toString());				
			}
			
			if (order.getRemaining() >  ordenASK.getRemaining()) {
				order.setRemaining(order.getRemaining() - ordenASK.getRemaining());
				ordenASK.setRemaining(0f);
				ordenASK.setStatus(Status.EJECUTADO.toString());				
			}
			
			guardarOrden(ordenASK);
			transaction.getOrderID().add(ordenASK.getId());
			
			if (order.getStatus().equals(Status.EJECUTADO.toString())) {				
				break;
			}
			
		}
		
		transaction.setOrder(order);
		order.getTransactions().add(transaction);
		guardarOrden(order);
		
	}
	
	@Transactional
	public void ejecutarTransaccionASK(Order order) {
		List<Order> ordenesBID = obtenerOrdenesBID(order.getValue());
		System.out.println("OrderServicio:registrarTransaccionASK: " + ordenesBID.toString());
		
		if (ordenesBID.isEmpty()) {
			return;
		}
		
		Transaction transaction = new Transaction();
		for (Order ordenBID : ordenesBID) {			
			if (order.getRemaining().equals(ordenBID.getRemaining())) {
				order.setRemaining(0f);
				order.setStatus(Status.EJECUTADO.toString());
				ordenBID.setRemaining(0f);
				ordenBID.setStatus(Status.EJECUTADO.toString());
			}
			
			if (order.getRemaining() <  ordenBID.getRemaining()) {
				ordenBID.setRemaining(ordenBID.getRemaining() - order.getRemaining());
				order.setRemaining(0f);
				order.setStatus(Status.EJECUTADO.toString());				
			}
			
			if (order.getRemaining() >  ordenBID.getRemaining()) {
				order.setRemaining(order.getRemaining() - ordenBID.getRemaining());
				ordenBID.setRemaining(0f);
				ordenBID.setStatus(Status.EJECUTADO.toString());				
			}
			
			guardarOrden(ordenBID);
			transaction.getOrderID().add(ordenBID.getId());
			
			if (order.getStatus().equals(Status.EJECUTADO.toString())) {				
				break;
			}
			
		}
		
		transaction.setOrder(order);
		order.getTransactions().add(transaction);
		guardarOrden(order);
	}

}
