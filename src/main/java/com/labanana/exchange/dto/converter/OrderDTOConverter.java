package com.labanana.exchange.dto.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.labanana.exchange.dto.OrderDTO;
import com.labanana.exchange.dto.TransactionDTO;
import com.labanana.exchange.entity.Order;
import com.labanana.exchange.entity.Transaction;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderDTOConverter {
	
	private final ModelMapper modelMapper;
	
//	public OrderDTO convertToDto(Order order) {
//		return modelMapper.map(order, OrderDTO.class);
//		
//	}
	
	public static OrderDTO convertOrderToOrderDto(Order order) {
		return OrderDTO.builder()
				.id(order.getId())
				.user(order.getUser())
				.type(order.getType())
				.size(order.getSize())
				.value(order.getValue())
				.remaining(order.getRemaining())
				.build();
	}
	
	public static Order convertOrderDtoToOrder(OrderDTO orderDTO) {
		return new Order().toBuilder()
				.id(orderDTO.getId())
				.user(orderDTO.getUser())
				.type(orderDTO.getType())
				.size(orderDTO.getSize())
				.value(orderDTO.getValue())
				.remaining(orderDTO.getSize())
				.build();
	}
	
	
	public static List<Order> convertOrderDtoToOrder(List<OrderDTO> orderDTO) {
		return orderDTO.stream()
				.map(OrderDTOConverter::convertOrderDtoToOrder)
				.collect(Collectors.toList());
	}
	
	public static List<Long> getIDs(List<Order> ordenes) {
		return ordenes.stream().map(o -> o.getId()).collect(Collectors.toList());
	}
	
	
	public static Set<Long> getOrderIDByTransaction(Transaction t) {
		List<Long> newList = new ArrayList<Long>(t.getOrderID());
		newList.add(t.getOrder().getId());
		return newList.stream().collect(Collectors.toSet());
	}
		
	public static TransactionDTO convertToTransactionDTO(Set<Long> ids) {
		TransactionDTO transactionDTO = new TransactionDTO(ids);
		return transactionDTO;
	}
	
}
